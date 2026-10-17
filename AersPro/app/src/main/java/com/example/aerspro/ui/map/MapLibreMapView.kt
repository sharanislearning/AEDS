package com.example.aerspro.ui.map

import android.content.Context
import android.graphics.Color
import android.location.Location
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import org.maplibre.geojson.Feature
import org.maplibre.geojson.FeatureCollection
import org.maplibre.geojson.LineString
import org.maplibre.geojson.Point
import org.maplibre.android.camera.CameraUpdateFactory
import org.maplibre.android.geometry.LatLng
import org.maplibre.android.geometry.LatLngBounds
import org.maplibre.android.maps.MapView
import org.maplibre.android.maps.MapLibreMap
import org.maplibre.android.maps.Style
import org.maplibre.android.style.layers.LineLayer
import org.maplibre.android.style.layers.Property
import org.maplibre.android.style.layers.PropertyFactory
import org.maplibre.android.style.sources.GeoJsonSource

private const val SOURCE_ID_ROUTE = "route-source"
private const val LAYER_ID_ROUTE = "route-layer"
private const val OSM_TILE_URL =
    "https://tile.openstreetmap.org/{z}/{x}/{y}.png"

/** OpenStreetMap raster style JSON for MapLibre */
private fun osmStyle(tileUrl: String): String = """
{
  "version": 8,
  "name": "OSM",
  "sources": {
    "osm-raster": {
      "type": "raster",
      "tiles": ["$tileUrl"],
      "tileSize": 256,
      "attribution": "© <a href='https://www.openstreetmap.org/copyright'>OpenStreetMap</a> contributors"
    }
  },
  "layers": [
    {
      "id": "osm-raster-layer",
      "type": "raster",
      "source": "osm-raster",
      "minzoom": 0,
      "maxzoom": 22
    }
  ]
}
""".trimIndent()

/**
 * Composable wrapper around MapLibre [MapView].
 * Renders an OpenStreetMap raster basemap, user location dot,
 * destination marker, and an orange OSRM route line.
 */
@Composable
fun MapLibreMapView(
    modifier: Modifier = Modifier,
    userLocation: Location?,
    destinationLat: Double?,
    destinationLng: Double?,
    routeCoordinates: List<Pair<Double, Double>>,
    onMapLongPress: (lat: Double, lng: Double) -> Unit = { _, _ -> }
) {
    val context = LocalContext.current
    val mapView = rememberMapView(context)

    AndroidView(
        factory = { mapView },
        modifier = modifier
    )

    // Set up the MapLibre style with OSM tiles
    LaunchedEffect(Unit) {
        mapView.getMapAsync { map ->
            map.setStyle(Style.Builder().fromJson(osmStyle(OSM_TILE_URL))) { style ->
                setupRouteLayer(style)
                map.uiSettings.isAttributionEnabled = true

                // Long-press to set destination
                map.addOnMapLongClickListener { latLng ->
                    onMapLongPress(latLng.latitude, latLng.longitude)
                    true
                }
            }
        }
    }

    // Pan camera to user location when first acquired
    LaunchedEffect(userLocation) {
        userLocation ?: return@LaunchedEffect
        mapView.getMapAsync { map ->
            if (map.cameraPosition.zoom < 10.0) {
                map.animateCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        LatLng(userLocation.latitude, userLocation.longitude),
                        15.0
                    ), 1200
                )
            }
        }
    }

    // Draw route and fit bounds when coordinates change
    LaunchedEffect(routeCoordinates, destinationLat, destinationLng, userLocation) {
        if (routeCoordinates.isEmpty()) return@LaunchedEffect
        mapView.getMapAsync { map ->
            map.getStyle { style ->
                drawRoute(style, routeCoordinates)
                fitRouteBounds(map, routeCoordinates)
            }
        }
    }
}

private fun setupRouteLayer(style: Style) {
    // GeoJSON source (initially empty)
    style.addSource(GeoJsonSource(SOURCE_ID_ROUTE))

    // Orange route line layer
    val routeLayer = LineLayer(LAYER_ID_ROUTE, SOURCE_ID_ROUTE).apply {
        setProperties(
            PropertyFactory.lineColor(Color.parseColor("#FF8C00")), // deep orange
            PropertyFactory.lineWidth(6f),
            PropertyFactory.lineCap(Property.LINE_CAP_ROUND),
            PropertyFactory.lineJoin(Property.LINE_JOIN_ROUND)
        )
    }
    style.addLayer(routeLayer)
}

private fun drawRoute(style: Style, coords: List<Pair<Double, Double>>) {
    val points = coords.map { (lat, lng) -> Point.fromLngLat(lng, lat) }
    val lineString = LineString.fromLngLats(points)
    val feature = Feature.fromGeometry(lineString)
    val source = style.getSourceAs<GeoJsonSource>(SOURCE_ID_ROUTE) ?: return
    source.setGeoJson(FeatureCollection.fromFeature(feature))
}

private fun fitRouteBounds(map: MapLibreMap, coords: List<Pair<Double, Double>>) {
    if (coords.size < 2) return
    val boundsBuilder = LatLngBounds.Builder()
    coords.forEach { (lat, lng) -> boundsBuilder.include(LatLng(lat, lng)) }
    try {
        val bounds = boundsBuilder.build()
        map.animateCamera(
            CameraUpdateFactory.newLatLngBounds(bounds, 160),
            1500
        )
    } catch (_: Exception) { /* bounds may be invalid for a single point */ }
}

/** Remember a [MapView] across recompositions, lifecycle-aware. */
@Composable
private fun rememberMapView(context: Context): MapView {
    val mapView = remember {
        MapView(context).apply {
            onCreate(null)
        }
    }
    DisposableEffect(mapView) {
        mapView.onStart()
        mapView.onResume()
        onDispose {
            mapView.onPause()
            mapView.onStop()
            mapView.onDestroy()
        }
    }
    return mapView
}
