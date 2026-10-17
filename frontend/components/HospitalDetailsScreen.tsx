import React, { useState } from 'react';
import { Hospital } from '../types';
import { HOSPITAL_MAP_IMAGE } from '../data/mockData';

interface HospitalDetailsScreenProps {
  hospital: Hospital;
  onBack: () => void;
  onStartNavigation: (hospitalId: string) => void;
  onViewOtherHospitals: () => void;
}

export const HospitalDetailsScreen: React.FC<HospitalDetailsScreenProps> = ({
  hospital,
  onBack,
  onStartNavigation,
  onViewOtherHospitals,
}) => {
  const [showMoreInfo, setShowMoreInfo] = useState(false);
  const mapBackground = hospital.mapImage || HOSPITAL_MAP_IMAGE;

  return (
    <div className="relative w-full h-[calc(100vh-4rem)] min-h-[620px] max-w-lg mx-auto bg-black overflow-hidden">
      {/* Map Background Layer */}
      <div className="absolute inset-0 z-0">
        <div
          className="w-full h-full bg-cover bg-center"
          data-location={hospital.name}
          style={{
            backgroundImage: `url('${mapBackground}')`,
            filter: 'grayscale(100%) contrast(1.4) brightness(0.6)',
          }}
        />
        {/* Brutalist Grid */}
        <div className="absolute inset-0 bg-[linear-gradient(rgba(255,255,255,0.03)_1px,transparent_1px),linear-gradient(90deg,rgba(255,255,255,0.03)_1px,transparent_1px)] bg-[size:40px_40px] pointer-events-none" />
        {/* Scrim Gradients to ensure pristine contrast */}
        <div className="absolute inset-0 bg-gradient-to-b from-black/90 via-black/40 to-black/95" />
      </div>

      {/* Content Overlay */}
      <div className="relative z-10 flex flex-col justify-between h-full p-4 gap-4">
        {/* Top Floating Card: ETA & Destination */}
        <div className="bg-black/95 backdrop-blur-xl rounded-2xl p-5 shadow-2xl border border-white/20 transition-all duration-300">
          <div className="flex justify-between items-start mb-3 border-b border-white/10 pb-4">
            <div>
              <span className="text-[9px] font-mono-data tracking-[0.3em] uppercase text-white/50 block mb-1">
                ESTIMATED ARRIVAL TIME
              </span>
              <h2 className="text-[48px] font-black text-white leading-none font-display tracking-tighter uppercase">
                {hospital.etaMins} <span className="text-[20px] font-bold text-white/60 font-mono-data">MINS</span>
              </h2>
              <p className="text-[16px] font-black text-white uppercase tracking-tight font-display mt-2">
                {hospital.name}
              </p>
              <p className="text-[11px] font-mono-data text-white/50 uppercase tracking-wider mt-0.5">
                {hospital.address}
              </p>
            </div>
            <div className="w-12 h-12 border border-white/20 rounded-full flex items-center justify-center bg-white/5 shrink-0">
              <span
                className="material-symbols-outlined text-white text-[22px]"
                style={{ fontVariationSettings: "'FILL' 1" }}
              >
                directions_car
              </span>
            </div>
          </div>

          {/* Route Stats */}
          <div className="flex items-center justify-between pt-1">
            <div className="flex items-center gap-2">
              <span className="material-symbols-outlined text-white/60 text-[16px]">
                traffic
              </span>
              <span className="text-[11px] font-bold uppercase tracking-wider font-mono-data text-white/70">
                {hospital.trafficText}
              </span>
            </div>
            <div className="flex items-center gap-2">
              <span className="material-symbols-outlined text-white/60 text-[16px]">
                straighten
              </span>
              <span className="text-[11px] font-bold uppercase tracking-wider font-mono-data text-white/70">
                {hospital.distance}
              </span>
            </div>
            <button
              onClick={() => setShowMoreInfo(!showMoreInfo)}
              className="text-[10px] font-black uppercase tracking-[0.2em] text-white hover:text-[#FF3E00] flex items-center gap-0.5 ml-auto font-mono-data transition-colors"
            >
              {showMoreInfo ? '[- LESS]' : '[+ SPECS]'}
            </button>
          </div>

          {/* Expandable Facility Specs */}
          {showMoreInfo && (
            <div className="mt-4 pt-4 border-t border-white/10 grid grid-cols-2 gap-2 text-[11px] animate-fadeIn font-mono-data">
              <div className="bg-white/5 p-2.5 rounded-xl border border-white/10">
                <span className="text-white/40 uppercase tracking-wider block text-[9px]">Trauma Level</span>
                <p className="font-bold text-white uppercase text-[12px] mt-0.5">{hospital.traumaLevel}</p>
              </div>
              <div className="bg-white/5 p-2.5 rounded-xl border border-white/10">
                <span className="text-white/40 uppercase tracking-wider block text-[9px]">ER Wait Time</span>
                <p className="font-bold text-white uppercase text-[12px] mt-0.5">~{hospital.waitTimeMins} MINS</p>
              </div>
              <div className="bg-white/5 p-2.5 rounded-xl border border-white/10">
                <span className="text-white/40 uppercase tracking-wider block text-[9px]">ICU Capacity</span>
                <p className="font-bold text-white uppercase text-[12px] mt-0.5">
                  {hospital.icuBedsAvailable} BEDS FREE
                </p>
              </div>
              <div className="bg-white/5 p-2.5 rounded-xl border border-white/10">
                <span className="text-white/40 uppercase tracking-wider block text-[9px]">Dispatch Hotline</span>
                <p className="font-bold text-white uppercase text-[12px] mt-0.5">{hospital.phone}</p>
              </div>
            </div>
          )}
        </div>

        {/* Bottom Actions */}
        <div className="flex flex-col gap-3 mt-auto pb-4">
          {/* Primary Action: Start Navigation */}
          <button
            onClick={() => onStartNavigation(hospital.id)}
            id="start-navigation-btn"
            className="w-full bg-white hover:bg-white/90 text-black text-[13px] font-black uppercase tracking-[0.25em] py-4 rounded-full shadow-[0_8px_32px_rgba(0,0,0,0.8)] active:scale-95 transition-all flex items-center justify-center gap-3 cursor-pointer font-display"
          >
            <span
              className="material-symbols-outlined text-[20px] font-bold"
              style={{ fontVariationSettings: "'FILL' 1" }}
            >
              navigation
            </span>
            START NAVIGATION
          </button>

          {/* Secondary Action: View Other Hospitals */}
          <button
            onClick={onViewOtherHospitals}
            id="view-other-hospitals-btn"
            className="w-full py-2 text-center active:scale-95 transition-transform"
          >
            <span className="text-[11px] font-bold uppercase tracking-[0.25em] text-white/50 hover:text-white transition-colors font-mono-data">
              ← VIEW OTHER HOSPITALS
            </span>
          </button>
        </div>
      </div>
    </div>
  );
};
