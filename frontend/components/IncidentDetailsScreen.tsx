import React, { useState, useEffect } from 'react';
import { AmbulanceUnit } from '../types';
import { COMMAND_MAP_IMAGE, PARAMEDIC_DAVID_AVATAR } from '../data/mockData';

interface IncidentDetailsScreenProps {
  unit?: AmbulanceUnit;
  destinationAddress?: string;
  onBack: () => void;
  onCallDriver: () => void;
  onTextDriver: () => void;
  onCancelRequest: () => void;
  onViewLiveNavigation?: () => void;
}

export const IncidentDetailsScreen: React.FC<IncidentDetailsScreenProps> = ({
  unit,
  destinationAddress = 'Srishti Complex, Mira Road East',
  onBack,
  onCallDriver,
  onTextDriver,
  onCancelRequest,
  onViewLiveNavigation,
}) => {
  const [isSheetExpanded, setIsSheetExpanded] = useState(true);
  const [etaMinutes, setEtaMinutes] = useState(4);
  const [progress, setProgress] = useState(65);
  const [showCancelModal, setShowCancelModal] = useState(false);

  const paramedicName = unit?.paramedicName || 'Rohan Deshmukh';
  const paramedicRole = unit?.paramedicRole || 'Lead Paramedic • MBMC EMS Unit 42';
  const paramedicAvatar = unit?.avatar || PARAMEDIC_DAVID_AVATAR;
  const rating = unit?.paramedicRating || 4.9;

  // Simulate realistic progress updates
  useEffect(() => {
    const timer = setInterval(() => {
      setProgress((prev) => (prev >= 95 ? 60 : prev + 1));
    }, 4000);
    return () => clearInterval(timer);
  }, []);

  return (
    <div className="relative w-full h-[calc(100vh-4rem)] min-h-[640px] max-w-lg mx-auto bg-black overflow-hidden">
      {/* Top Active SOS Banner */}
      <div className="absolute top-0 left-0 right-0 z-20 px-4 pt-3 pointer-events-none">
        <div className="flex justify-center">
          <div className="bg-[#FF3E00] text-black backdrop-blur-md rounded-full px-5 py-2 flex items-center gap-2 shadow-[0_0_30px_rgba(255,62,0,0.6)] animate-pulse">
            <span
              className="material-symbols-outlined text-black text-[20px] font-black animate-[spin_4s_linear_infinite]"
              style={{ fontVariationSettings: "'FILL' 1" }}
            >
              emergency
            </span>
            <span className="text-black font-black text-[11px] tracking-[0.25em] uppercase font-display">
              SOS DISPATCH ACTIVE
            </span>
          </div>
        </div>
      </div>

      {/* Map Area with Tactical Glowing Route Line */}
      <div
        className="w-full h-full relative z-10 bg-cover bg-center"
        style={{
          backgroundImage: `url('${COMMAND_MAP_IMAGE}')`,
          filter: 'grayscale(100%) contrast(1.4) brightness(0.6)',
        }}
      >
        {/* SVG Route Overlay */}
        <svg
          className="absolute inset-0 w-full h-full pointer-events-none"
          preserveAspectRatio="none"
          viewBox="0 0 100 100"
        >
          <defs>
            <filter id="glow" x="-30%" y="-30%" width="160%" height="160%">
              <feGaussianBlur stdDeviation="1.5" result="blur" />
              <feMerge>
                <feMergeNode in="blur" />
                <feMergeNode in="SourceGraphic" />
              </feMerge>
            </filter>
          </defs>

          {/* Static Background Route Line */}
          <path
            d="M 20,80 C 30,70 40,80 50,50 C 60,20 70,30 80,20"
            fill="none"
            stroke="#ffffff"
            strokeWidth="1.2"
            strokeDasharray="2 1"
            opacity="0.3"
          />

          {/* Glowing Animated Route Line */}
          <path
            d="M 20,80 C 30,70 40,80 50,50 C 60,20 70,30 80,20"
            fill="none"
            stroke="#FF3E00"
            strokeWidth="2.2"
            filter="url(#glow)"
            strokeDasharray="12 4"
            className="animate-pulse"
          />

          {/* User Location Target Marker (End Point) */}
          <g transform="translate(80, 20)">
            <circle r="5" fill="#FF3E00" filter="url(#glow)" opacity="0.8">
              <animate attributeName="r" values="4;7;4" dur="2s" repeatCount="indefinite" />
              <animate attributeName="opacity" values="0.8;0.3;0.8" dur="2s" repeatCount="indefinite" />
            </circle>
            <circle r="3" fill="#FF3E00" />
            <circle r="1.2" fill="#ffffff" />
          </g>

          {/* Ambulance Live Location Marker (Start Point) */}
          <g transform="translate(20, 80)">
            <circle r="5" fill="#ffffff" filter="url(#glow)" opacity="0.6">
              <animate attributeName="r" values="3;6;3" dur="1.5s" repeatCount="indefinite" />
            </circle>
            <circle r="3" fill="#ffffff" />
            <circle r="1.2" fill="#000000" />
          </g>
        </svg>

        {/* Tactical Coordinates & Live Status readout */}
        <div className="absolute top-16 left-4 z-20 bg-black/90 backdrop-blur-md px-3.5 py-2 rounded-xl border border-white/20 text-[10px] font-mono-data text-white/80">
          <p className="text-[#FF3E00] font-black uppercase tracking-widest">UNIT 42 // INTERCEPT ROUTE</p>
          <p className="tracking-wider">LOC: 34.0522° N, 118.2437° W</p>
          <p className="text-white/60 tracking-wider">SPEED: 46 MPH • EMERGENCY CODE 3</p>
        </div>
      </div>

      {/* Floating Bottom Sheet */}
      <div
        className={`absolute bottom-0 left-0 right-0 z-30 px-3 pb-3 transition-transform duration-300 ${
          isSheetExpanded ? 'translate-y-0' : 'translate-y-[62%]'
        }`}
      >
        <div
          id="bottom-sheet"
          className="bg-black/95 backdrop-blur-2xl rounded-3xl border border-white/20 shadow-[0_-12px_48px_rgba(0,0,0,0.95)] overflow-hidden flex flex-col"
        >
          {/* Drag / Toggle Handle */}
          <button
            onClick={() => setIsSheetExpanded(!isSheetExpanded)}
            className="w-full flex justify-center pt-3 pb-2 cursor-pointer focus:outline-none"
            aria-label="Toggle Sheet"
          >
            <div className="w-12 h-1 bg-white/30 rounded-full hover:bg-white transition-colors" />
          </button>

          <div className="px-5 pb-5 flex flex-col gap-4">
            {/* Status & ETA Header */}
            <div className="flex items-start justify-between border-b border-white/10 pb-4">
              <div className="flex flex-col">
                <span className="text-[9px] font-mono-data uppercase tracking-[0.3em] text-white/40 block mb-1">
                  INTERCEPT STATUS
                </span>
                <h2 className="text-[24px] font-black text-white leading-none font-display uppercase tracking-tight">
                  AMBULANCE EN ROUTE
                </h2>
                <p className="text-[12px] font-mono-data text-white/60 flex items-center gap-1 mt-2 uppercase tracking-wider">
                  <span className="material-symbols-outlined text-[15px] text-[#FF3E00]">
                    location_on
                  </span>
                  {destinationAddress}
                </p>
              </div>
              <div className="flex flex-col items-end">
                <span
                  id="eta-timer"
                  className="font-mono-data text-[52px] font-black text-white leading-none tracking-tighter"
                >
                  {etaMinutes < 10 ? `0${etaMinutes}` : etaMinutes}
                </span>
                <span className="text-[9px] font-black text-white/60 uppercase tracking-[0.25em] font-mono-data mt-0.5">
                  MINUTES
                </span>
              </div>
            </div>

            {/* High Contrast Progress Bar */}
            <div className="w-full h-2 bg-white/10 rounded-full overflow-hidden">
              <div
                className="h-full bg-white rounded-full transition-all duration-500 shadow-[0_0_16px_rgba(255,255,255,0.9)]"
                style={{ width: `${progress}%` }}
              />
            </div>

            {/* Paramedic Driver Card */}
            <div className="bg-white/5 rounded-2xl p-3.5 flex items-center gap-3.5 border border-white/15 shadow-sm">
              <div className="relative w-12 h-12 rounded-full overflow-hidden bg-black border border-white/30 shrink-0">
                <img
                  src={paramedicAvatar}
                  alt={paramedicName}
                  className="w-full h-full object-cover grayscale contrast-125"
                />
              </div>
              <div className="flex-1 min-w-0">
                <span className="text-[9px] font-mono-data uppercase tracking-[0.25em] text-white/40 block">
                  RESPONDER
                </span>
                <h3 className="text-[16px] font-black text-white truncate font-display uppercase">
                  {paramedicName}
                </h3>
                <p className="text-[11px] font-mono-data text-white/60 truncate uppercase tracking-wider">
                  {paramedicRole}
                </p>
              </div>
              <div className="flex items-center gap-1 bg-white/10 px-3 py-1 rounded-full border border-white/20 shrink-0 font-mono-data">
                <span
                  className="material-symbols-outlined text-white text-[15px]"
                  style={{ fontVariationSettings: "'FILL' 1" }}
                >
                  star
                </span>
                <span className="text-[12px] font-black text-white">
                  {rating.toFixed(1)}
                </span>
              </div>
            </div>

            {/* Primary Action Buttons */}
            <div className="grid grid-cols-2 gap-3">
              <button
                onClick={onCallDriver}
                id="call-driver-btn"
                className="bg-white hover:bg-white/90 active:scale-95 text-black font-black text-[11px] uppercase tracking-[0.25em] py-3.5 px-4 rounded-full flex items-center justify-center gap-2 transition-all shadow-lg font-display"
              >
                <span className="material-symbols-outlined text-[18px] font-bold">
                  call
                </span>
                CALL DRIVER
              </button>
              <button
                onClick={onTextDriver}
                id="text-driver-btn"
                className="bg-black hover:bg-white hover:text-black active:scale-95 text-white font-black text-[11px] uppercase tracking-[0.25em] py-3.5 px-4 rounded-full flex items-center justify-center gap-2 transition-all border border-white/25 shadow-md font-display"
              >
                <span className="material-symbols-outlined text-[18px]">
                  chat
                </span>
                TEXT DRIVER
              </button>
            </div>

            {/* Optional Simulation Navigation button */}
            {onViewLiveNavigation && (
              <button
                onClick={onViewLiveNavigation}
                className="w-full bg-white/5 hover:bg-white/10 text-white py-3 rounded-full text-[10px] font-black uppercase tracking-[0.25em] border border-white/20 flex items-center justify-center gap-2 transition-colors font-mono-data"
              >
                <span className="material-symbols-outlined text-[16px]">navigation</span>
                VIEW LIVE TACTICAL NAVIGATION ROUTE
              </button>
            )}

            {/* Cancel Request Link */}
            <div className="text-center pt-1">
              <button
                onClick={() => setShowCancelModal(true)}
                id="cancel-request-btn"
                className="text-[10px] font-bold uppercase tracking-[0.25em] text-white/40 hover:text-[#FF3E00] transition-colors py-1 px-4 font-mono-data"
              >
                [CANCEL EMERGENCY REQUEST]
              </button>
            </div>
          </div>
        </div>
      </div>

      {/* Cancel Confirmation Dialog */}
      {showCancelModal && (
        <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/90 backdrop-blur-md p-4 animate-fadeIn">
          <div className="bg-black rounded-3xl p-6 max-w-sm w-full border border-white/20 shadow-2xl flex flex-col gap-4">
            <div className="w-14 h-14 rounded-full bg-white/5 border border-white/20 flex items-center justify-center mx-auto text-[#FF3E00]">
              <span className="material-symbols-outlined text-[32px]">warning</span>
            </div>
            <div className="text-center">
              <span className="text-[9px] font-mono-data uppercase tracking-[0.3em] text-[#FF3E00] block mb-1">
                ABORT DISPATCH
              </span>
              <h3 className="text-[20px] font-black text-white uppercase font-display tracking-tight">
                CANCEL EMERGENCY?
              </h3>
              <p className="text-[12px] font-mono-data text-white/60 mt-2 leading-relaxed uppercase tracking-wider">
                Unit 42 is currently moving toward your location at 1245 Main St. Are you sure the emergency is resolved?
              </p>
            </div>
            <div className="flex gap-2.5 mt-2">
              <button
                onClick={() => setShowCancelModal(false)}
                className="flex-1 bg-black hover:bg-white hover:text-black text-white py-3.5 rounded-full text-[10px] font-black uppercase tracking-[0.2em] border border-white/20 font-display transition-colors"
              >
                KEEP ACTIVE
              </button>
              <button
                onClick={() => {
                  setShowCancelModal(false);
                  onCancelRequest();
                }}
                className="flex-1 bg-[#FF3E00] hover:bg-[#ff551f] text-black py-3.5 rounded-full text-[10px] font-black uppercase tracking-[0.2em] shadow-lg font-display transition-colors"
              >
                CONFIRM CANCEL
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};
