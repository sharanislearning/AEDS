import React, { useState, useRef, useEffect } from 'react';
import { COMMAND_MAP_IMAGE, INITIAL_HOSPITALS } from '../data/mockData';
import { Hospital } from '../types';

interface HomeScreenProps {
  onFindHospitals: () => void;
  onSosTrigger: () => void;
  onSelectHospital: (hospitalId: string) => void;
  onSelectIncident?: (incidentId: string) => void;
  activeSosIncidentId?: string | null;
  hospitals?: Hospital[];
  selectedHospitalId?: string;
}

export const HomeScreen: React.FC<HomeScreenProps> = ({
  onFindHospitals,
  onSosTrigger,
  onSelectHospital,
  onSelectIncident,
  activeSosIncidentId,
  hospitals = INITIAL_HOSPITALS,
  selectedHospitalId,
}) => {
  const [pressProgress, setPressProgress] = useState(0);
  const [isPressing, setIsPressing] = useState(false);
  const [isDispatched, setIsDispatched] = useState(false);
  const pressTimerRef = useRef<number | null>(null);
  const startTimeRef = useRef<number>(0);
  const HOLD_DURATION = 1600; // 1.6s smooth press

  // SVG circle calculations for radius 46 -> circumference = 2 * PI * 46 ~ 289
  const CIRCUMFERENCE = 289;
  const strokeDashoffset = CIRCUMFERENCE - (pressProgress / 100) * CIRCUMFERENCE;

  const startHold = (e: React.MouseEvent | React.TouchEvent) => {
    e.preventDefault();
    if (isDispatched) return;
    setIsPressing(true);
    startTimeRef.current = Date.now();

    const interval = window.setInterval(() => {
      const elapsed = Date.now() - startTimeRef.current;
      const progress = Math.min(100, (elapsed / HOLD_DURATION) * 100);
      setPressProgress(progress);

      if (progress >= 100) {
        clearInterval(interval);
        setIsDispatched(true);
        // Trigger haptic or audio feedback if available
        if (navigator.vibrate) {
          navigator.vibrate([100, 50, 200]);
        }
        setTimeout(() => {
          onSosTrigger();
          setIsPressing(false);
          setPressProgress(0);
          setIsDispatched(false);
        }, 300);
      }
    }, 20);

    pressTimerRef.current = interval;
  };

  const cancelHold = () => {
    if (isDispatched) return;
    if (pressTimerRef.current) {
      clearInterval(pressTimerRef.current);
      pressTimerRef.current = null;
    }
    setIsPressing(false);
    setPressProgress(0);
  };

  useEffect(() => {
    return () => {
      if (pressTimerRef.current) clearInterval(pressTimerRef.current);
    };
  }, []);

  return (
    <div className="relative w-full h-[calc(100vh-8rem)] min-h-[580px] max-w-lg mx-auto overflow-hidden bg-black">
      {/* Grayscale Tactical Map Layer */}
      <div className="absolute inset-0 z-0">
        <div
          className="w-full h-full bg-cover bg-center transition-all duration-700"
          style={{
            backgroundImage: `url('${COMMAND_MAP_IMAGE}')`,
            filter: 'grayscale(100%) contrast(1.4) brightness(0.65)',
          }}
        />
        {/* Brutalist grid lines overlay */}
        <div className="absolute inset-0 bg-[linear-gradient(rgba(255,255,255,0.04)_1px,transparent_1px),linear-gradient(90deg,rgba(255,255,255,0.04)_1px,transparent_1px)] bg-[size:48px_48px] pointer-events-none" />
        
        {/* Scrim Overlay */}
        <div className="absolute inset-0 bg-gradient-to-b from-black/85 via-black/30 to-black/95 pointer-events-none" />

        {/* User Location Radar Marker (Center - Srishti, Mira Road) */}
        <div className="absolute top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 flex items-center justify-center pointer-events-none z-10">
          <div className="w-4 h-4 bg-white rounded-full relative z-10 shadow-[0_0_20px_rgba(255,255,255,1)]" />
          <div className="w-14 h-14 bg-white/20 rounded-full absolute animate-ping duration-3000" />
          <div className="w-32 h-32 border border-white/30 rounded-full absolute animate-[ping_4s_cubic-bezier(0,0,0.2,1)_infinite]" />
          <span className="absolute top-5 bg-black/90 text-white font-mono-data text-[9px] font-bold px-2 py-0.5 rounded-full border border-white/20 tracking-wider whitespace-nowrap shadow-lg">
            YOU • MIRA ROAD (E)
          </span>
        </div>

        {/* Live Ambulance Marker: ALS Unit 42 */}
        <button
          onClick={() => {
            if (activeSosIncidentId && onSelectIncident) {
              onSelectIncident(activeSosIncidentId);
            } else {
              onSosTrigger();
            }
          }}
          className="absolute top-[46%] left-[32%] flex flex-col items-center gap-1 group z-20 focus:outline-none cursor-pointer"
          title="Unit 42 - En Route (Mira Road)"
        >
          <div className="bg-black text-white p-2 rounded-full shadow-2xl border border-[#FF3E00] flex items-center justify-center group-hover:scale-110 transition-transform">
            <span
              className="material-symbols-outlined text-[18px] text-[#FF3E00]"
              style={{ fontVariationSettings: "'FILL' 1" }}
            >
              ambulance
            </span>
          </div>
          <span className="text-[9px] font-mono-data font-bold uppercase tracking-widest text-[#FF3E00] bg-black/90 px-2 py-0.5 rounded-full border border-[#FF3E00]/40 shadow-md group-hover:border-[#FF3E00] transition-colors">
            ALS UNIT 42
          </span>
        </button>

        {/* Dynamic Nearby Hospital Markers from hospitals list */}
        {hospitals.map((hosp) => {
          const isSelected = selectedHospitalId === hosp.id;
          const isFastest = hosp.isFastest;
          const topPercent = hosp.coordinates?.y ? `${hosp.coordinates.y}%` : '40%';
          const leftPercent = hosp.coordinates?.x ? `${hosp.coordinates.x}%` : '50%';

          return (
            <button
              key={hosp.id}
              onClick={() => onSelectHospital(hosp.id)}
              style={{ top: topPercent, left: leftPercent }}
              className={`absolute -translate-x-1/2 -translate-y-1/2 flex flex-col items-center gap-1 group z-20 focus:outline-none cursor-pointer transition-all duration-300 ${
                isSelected ? 'scale-110 z-30' : 'hover:scale-105'
              }`}
              title={`${hosp.name} • ${hosp.distance} • ${hosp.etaMins}m ETA`}
            >
              <div
                className={`p-2 rounded-full shadow-xl flex items-center justify-center transition-all ${
                  isFastest
                    ? 'bg-white text-black border-2 border-white shadow-[0_0_15px_rgba(255,255,255,0.8)]'
                    : isSelected
                    ? 'bg-[#FF3E00] text-black border-2 border-white'
                    : 'bg-black text-white border border-white/40 hover:border-white'
                }`}
              >
                <span
                  className="material-symbols-outlined text-[16px]"
                  style={{ fontVariationSettings: "'FILL' 1, 'wght' 700" }}
                >
                  local_hospital
                </span>
              </div>
              <div className="flex items-center gap-1 bg-black/95 px-2 py-0.5 rounded-full border border-white/30 shadow-lg group-hover:border-white">
                <span className="text-[8px] font-black uppercase tracking-wider text-white font-display truncate max-w-[84px]">
                  {hosp.name.split(' ')[0]}
                </span>
                <span className="text-[8px] font-mono-data text-[#FF3E00] font-bold">
                  {hosp.etaMins}m
                </span>
              </div>
            </button>
          );
        })}
      </div>

      {/* UI Controls Overlay */}
      <div className="relative z-20 flex flex-col justify-between h-full p-4 pointer-events-none">
        {/* Top Status Banner */}
        <div className="pointer-events-auto">
          {activeSosIncidentId ? (
            <button
              onClick={() => onSelectIncident && onSelectIncident(activeSosIncidentId)}
              className="w-full bg-[#FF3E00] text-black rounded-2xl p-4 flex items-center justify-between shadow-[0_8px_30px_rgba(255,62,0,0.5)] transition-all active:scale-[0.99]"
            >
              <div className="flex items-center gap-3 min-w-0">
                <div className="w-10 h-10 rounded-full bg-black text-white flex items-center justify-center shrink-0 shadow-md">
                  <span className="material-symbols-outlined text-[20px] animate-spin">emergency</span>
                </div>
                <div className="text-left truncate">
                  <span className="text-[9px] font-mono-data uppercase tracking-[0.3em] font-bold text-black/70 block">
                    ALERT LEVEL: ALPHA • MIRA ROAD
                  </span>
                  <p className="text-[15px] font-black tracking-tight text-black truncate uppercase font-display">
                    Active Emergency Incident
                  </p>
                  <p className="text-[11px] font-bold text-black/80 truncate font-mono-data">
                    UNIT 42 EN ROUTE • TAP TO VIEW
                  </p>
                </div>
              </div>
              <span className="material-symbols-outlined text-black font-black">arrow_forward</span>
            </button>
          ) : (
            <div className="bg-black/90 backdrop-blur-xl rounded-2xl p-3.5 flex items-center justify-between border border-white/15 shadow-2xl">
              <div className="flex items-center gap-3">
                <div className="w-9 h-9 rounded-full border border-white/20 bg-white/5 flex items-center justify-center shrink-0">
                  <span className="material-symbols-outlined text-white text-[18px]">
                    radar
                  </span>
                </div>
                <div className="min-w-0">
                  <span className="text-[9px] font-mono-data uppercase tracking-[0.3em] text-white/50 block">
                    MIRA ROAD (THANE) TELEMETRY
                  </span>
                  <p className="text-[13px] font-black tracking-tight text-white uppercase font-display flex items-center gap-1.5">
                    <span>{hospitals.length} HOSPITALS TRACKED</span>
                  </p>
                </div>
              </div>
              <div className="flex items-center gap-2 border border-white/20 px-3 py-1 rounded-full bg-white/5">
                <span className="w-2 h-2 rounded-full bg-[#FF3E00] animate-ping" />
                <span className="text-[10px] font-mono-data font-bold uppercase tracking-widest text-white">
                  LIVE
                </span>
              </div>
            </div>
          )}
        </div>

        {/* Center / Bottom SOS Interaction Area */}
        <div className="flex flex-col items-center pb-2 pointer-events-none mt-auto">
          {/* Secondary Action: Find Hospitals */}
          <button
            onClick={onFindHospitals}
            id="home-find-hospitals-btn"
            className="pointer-events-auto mb-6 bg-white hover:bg-white/90 text-black px-7 py-3 rounded-full flex items-center gap-2.5 shadow-[0_8px_32px_rgba(0,0,0,0.8)] active:scale-95 transition-all duration-200"
          >
            <span className="material-symbols-outlined text-[18px] font-bold">search</span>
            <span className="text-[11px] font-black uppercase tracking-[0.25em] font-display">
              FIND HOSPITALS
            </span>
          </button>

          {/* Large SOS Button with Circular Long-Press Animation */}
          <div className="relative pointer-events-auto flex flex-col items-center">
            <button
              onMouseDown={startHold}
              onTouchStart={startHold}
              onMouseUp={cancelHold}
              onMouseLeave={cancelHold}
              onTouchEnd={cancelHold}
              onClick={() => {
                if (pressProgress < 20) {
                  onSosTrigger();
                }
              }}
              id="sos-button-main"
              className={`relative flex items-center justify-center w-28 h-28 rounded-full select-none transition-all duration-300 active:scale-95 focus:outline-none shadow-[0_12px_48px_rgba(255,62,0,0.6)] ${
                isDispatched
                  ? 'bg-white shadow-[0_0_60px_rgba(255,255,255,0.9)]'
                  : 'bg-[#FF3E00] hover:bg-[#ff551f]'
              }`}
            >
              {/* Outer Pulsing Aura */}
              <div
                className={`absolute inset-0 rounded-full ${
                  isDispatched ? 'bg-white' : 'bg-[#FF3E00]'
                } animate-ping opacity-30 pointer-events-none`}
              />

              {/* Inner Decorative Ring */}
              <div className="absolute inset-2 rounded-full border border-black/20 pointer-events-none" />

              {/* Center Text */}
              <div className="flex flex-col items-center justify-center z-10">
                <span
                  className={`text-[28px] font-black tracking-tighter uppercase font-display leading-none ${
                    isDispatched ? 'text-black' : 'text-black'
                  }`}
                >
                  {isDispatched ? 'SENT' : 'SOS'}
                </span>
                {!isDispatched && (
                  <span className="text-[8px] font-bold font-mono-data tracking-[0.3em] uppercase text-black/75 mt-0.5">
                    HOLD
                  </span>
                )}
              </div>

              {/* SVG Long-Press Progress Ring */}
              <svg
                className={`absolute inset-0 w-full h-full -rotate-90 pointer-events-none transition-opacity duration-200 ${
                  isPressing ? 'opacity-100' : 'opacity-0'
                }`}
                viewBox="0 0 100 100"
              >
                {/* Background Ring Track */}
                <circle
                  cx="50"
                  cy="50"
                  r="46"
                  fill="none"
                  stroke="rgba(0,0,0,0.3)"
                  strokeWidth="6"
                />
                {/* Active Dynamic Progress Stroke */}
                <circle
                  cx="50"
                  cy="50"
                  r="46"
                  fill="none"
                  stroke="#ffffff"
                  strokeWidth="6"
                  strokeDasharray={CIRCUMFERENCE}
                  strokeDashoffset={strokeDashoffset}
                  strokeLinecap="round"
                  className="transition-[stroke-dashoffset] duration-75 ease-linear"
                />
              </svg>
            </button>

            {/* Label below SOS */}
            <div className="mt-3 pointer-events-auto flex items-center gap-2 bg-black/90 px-4 py-1.5 rounded-full border border-white/20 backdrop-blur-md shadow-lg">
              <div className="w-1.5 h-1.5 bg-[#FF3E00] rounded-full"></div>
              <span className="text-[10px] font-mono-data font-bold uppercase tracking-[0.2em] text-white/70">
                {isPressing ? 'HOLDING TO DISPATCH...' : 'PRESS OR HOLD FOR IMMEDIATE SOS'}
              </span>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
