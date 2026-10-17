import React, { useState, useEffect } from 'react';
import { Hospital, TurnInstruction } from '../types';
import { TURN_BY_TURN_STEPS, COMMAND_MAP_IMAGE } from '../data/mockData';

interface NavigationScreenProps {
  hospital: Hospital;
  onEndNavigation: () => void;
  onSwitchToHospitalDetails: () => void;
}

export const NavigationScreen: React.FC<NavigationScreenProps> = ({
  hospital,
  onEndNavigation,
  onSwitchToHospitalDetails,
}) => {
  const [currentStepIndex, setCurrentStepIndex] = useState(0);
  const [speedMph, setSpeedMph] = useState(42);
  const [remainingMins, setRemainingMins] = useState(hospital.etaMins || 8);
  const [emergencySirens, setEmergencySirens] = useState(true);

  const steps: TurnInstruction[] = TURN_BY_TURN_STEPS;
  const currentStep = steps[currentStepIndex] || steps[0];

  // Simulate progress through turns
  useEffect(() => {
    const interval = setInterval(() => {
      setSpeedMph(Math.floor(38 + Math.random() * 12));
    }, 2000);
    return () => clearInterval(interval);
  }, []);

  const handleNextStep = () => {
    if (currentStepIndex < steps.length - 1) {
      setCurrentStepIndex((prev) => prev + 1);
      setRemainingMins((prev) => Math.max(1, prev - 2));
    } else {
      onEndNavigation();
    }
  };

  return (
    <div className="relative w-full h-[calc(100vh-4rem)] min-h-[640px] max-w-lg mx-auto bg-black overflow-hidden flex flex-col justify-between">
      {/* Background Tactical Map */}
      <div
        className="absolute inset-0 z-0 bg-cover bg-center"
        style={{
          backgroundImage: `url('${hospital.mapImage || COMMAND_MAP_IMAGE}')`,
          filter: 'grayscale(100%) contrast(1.4) brightness(0.6)',
        }}
      >
        {/* Brutalist Grid */}
        <div className="absolute inset-0 bg-[linear-gradient(rgba(255,255,255,0.03)_1px,transparent_1px),linear-gradient(90deg,rgba(255,255,255,0.03)_1px,transparent_1px)] bg-[size:40px_40px] pointer-events-none" />

        {/* Animated Navigation Arrow in Center */}
        <div className="absolute top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 flex items-center justify-center pointer-events-none">
          <div className="w-14 h-14 rounded-full bg-white/10 border border-white/40 flex items-center justify-center animate-pulse">
            <div className="w-9 h-9 rounded-full bg-white shadow-[0_0_24px_rgba(255,255,255,1)] flex items-center justify-center text-black">
              <span className="material-symbols-outlined text-[20px] font-black">
                navigation
              </span>
            </div>
          </div>
        </div>

        {/* Tactical Dark Gradients */}
        <div className="absolute inset-0 bg-gradient-to-b from-black/90 via-transparent to-black/95 pointer-events-none" />
      </div>

      {/* Top Navigation HUD Card */}
      <div className="relative z-20 p-4 pt-3">
        <div className="bg-black/95 backdrop-blur-2xl rounded-3xl p-5 border border-white/20 shadow-2xl">
          <div className="flex items-center justify-between">
            <div className="flex items-center gap-3.5">
              <div className="w-12 h-12 rounded-2xl bg-white text-black flex items-center justify-center shadow-lg font-bold shrink-0">
                <span className="material-symbols-outlined text-[28px] font-black">
                  {currentStep.action === 'turn-right'
                    ? 'turn_right'
                    : currentStep.action === 'turn-left'
                    ? 'turn_left'
                    : currentStep.action === 'merge'
                    ? 'merge'
                    : currentStep.action === 'arrive'
                    ? 'pin_drop'
                    : 'straight'}
                </span>
              </div>
              <div>
                <p className="text-[11px] font-mono-data text-white/60 font-bold uppercase tracking-widest">
                  IN {currentStep.distance}
                </p>
                <h3 className="text-[17px] font-black text-white leading-tight font-display uppercase tracking-tight">
                  {currentStep.instruction}
                </h3>
              </div>
            </div>
            <button
              onClick={handleNextStep}
              className="text-[10px] font-mono-data bg-white/10 hover:bg-white hover:text-black text-white px-3 py-1.5 rounded-full border border-white/20 uppercase tracking-wider transition-colors shrink-0"
              title="Next Step Preview"
            >
              {currentStepIndex + 1}/{steps.length}
            </button>
          </div>

          {/* Lane Guidance */}
          <div className="mt-3.5 pt-3 border-t border-white/10 flex items-center justify-between text-[11px] font-mono-data">
            <span className="text-white/60 uppercase tracking-wider">
              DEST: <strong className="text-white font-bold">{hospital.name}</strong>
            </span>
            <div className="flex items-center gap-1.5">
              <span className="text-white uppercase tracking-wider font-bold">PRIORITY LANE</span>
              <span className="w-2 h-2 rounded-full bg-[#FF3E00] animate-ping" />
            </div>
          </div>
        </div>
      </div>

      {/* Floating Speedometer & Sirens HUD */}
      <div className="relative z-20 px-4 flex justify-between items-end pointer-events-none">
        {/* Speedometer */}
        <div className="bg-black/90 backdrop-blur-md px-4 py-2.5 rounded-2xl border border-white/20 shadow-lg pointer-events-auto">
          <span className="font-mono-data text-[28px] font-black text-white leading-none">
            {speedMph}
          </span>
          <span className="text-[10px] text-white/60 font-mono-data ml-1.5 uppercase font-bold tracking-wider">
            MPH
          </span>
        </div>

        {/* Emergency Sirens Toggle */}
        <button
          onClick={() => setEmergencySirens(!emergencySirens)}
          className={`pointer-events-auto px-4 py-2.5 rounded-2xl border flex items-center gap-2 text-[10px] font-black uppercase tracking-[0.2em] font-mono-data backdrop-blur-md shadow-lg transition-all ${
            emergencySirens
              ? 'bg-[#FF3E00] border-[#FF3E00] text-black'
              : 'bg-black/90 border-white/20 text-white/70'
          }`}
        >
          <span className="material-symbols-outlined text-[16px] animate-pulse font-black">
            {emergencySirens ? 'e911_emergency' : 'volume_off'}
          </span>
          {emergencySirens ? 'CODE 3 ACTIVE' : 'SIREN MUTED'}
        </button>
      </div>

      {/* Bottom Route Summary & Controls */}
      <div className="relative z-20 p-4 pb-6">
        <div className="bg-black/95 backdrop-blur-2xl rounded-3xl p-5 border border-white/20 shadow-2xl flex flex-col gap-4">
          <div className="flex items-center justify-between border-b border-white/10 pb-3">
            <div>
              <span className="text-[9px] font-mono-data uppercase tracking-[0.3em] text-white/40 block mb-1">
                REMAINING TIME
              </span>
              <div className="flex items-baseline gap-1.5">
                <span className="text-[38px] font-mono-data font-black text-white leading-none">
                  {remainingMins}
                </span>
                <span className="text-[12px] font-black text-white/60 uppercase font-mono-data tracking-widest">MINS</span>
              </div>
              <p className="text-[11px] font-mono-data text-white/60 mt-1 uppercase tracking-wider">
                {hospital.distance} • FASTEST CORRIDOR
              </p>
            </div>
            <button
              onClick={onSwitchToHospitalDetails}
              className="bg-white/10 hover:bg-white hover:text-black text-white px-3.5 py-2 rounded-full text-[10px] font-black uppercase tracking-[0.2em] font-mono-data border border-white/20 transition-colors"
            >
              FACILITY INFO
            </button>
          </div>

          <div className="grid grid-cols-2 gap-3">
            <button
              onClick={handleNextStep}
              className="bg-white hover:bg-white/90 text-black py-3.5 rounded-full font-black text-[11px] uppercase tracking-[0.25em] flex items-center justify-center gap-2 active:scale-95 transition-transform font-display shadow-lg"
            >
              <span className="material-symbols-outlined text-[18px] font-bold">turn_right</span>
              {currentStepIndex === steps.length - 1 ? 'FINISH' : 'NEXT TURN'}
            </button>
            <button
              onClick={onEndNavigation}
              className="bg-black hover:bg-[#FF3E00] hover:text-black hover:border-[#FF3E00] text-white border border-white/25 py-3.5 rounded-full font-black text-[11px] uppercase tracking-[0.25em] flex items-center justify-center gap-1.5 active:scale-95 transition-all font-display"
            >
              <span className="material-symbols-outlined text-[18px]">close</span>
              END NAV
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};
