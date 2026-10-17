import React, { useState } from 'react';
import { DispatchHistoryItem } from '../types';

interface HistoryScreenProps {
  history: DispatchHistoryItem[];
  onSelectHospitalByName?: (hospitalName: string) => void;
}

export const HistoryScreen: React.FC<HistoryScreenProps> = ({
  history,
  onSelectHospitalByName,
}) => {
  const [selectedFilter, setSelectedFilter] = useState<'all' | 'sos' | 'transport'>('all');

  const filteredHistory = history.filter((item) => {
    if (selectedFilter === 'sos') return item.type === 'emergency_sos';
    if (selectedFilter === 'transport') return item.type === 'hospital_transfer';
    return true;
  });

  return (
    <div className="flex flex-col w-full p-4 gap-5 bg-black min-h-screen pb-28 max-w-lg mx-auto">
      {/* Title / Summary */}
      <div className="flex items-center justify-between border-b border-white/10 pb-4">
        <div>
          <span className="text-[9px] font-mono-data tracking-[0.3em] uppercase text-white/50 block mb-1">
            ENCRYPTED ARCHIVES
          </span>
          <h2 className="text-[28px] font-black text-white font-display uppercase tracking-tight leading-none">
            DISPATCH LOGS
          </h2>
          <p className="text-[11px] font-mono-data text-white/60 uppercase tracking-wider mt-1">
            SECURE HISTORY & INCIDENT TELEMETRY
          </p>
        </div>
        <span className="text-[10px] font-mono-data font-bold bg-white/10 border border-white/20 px-3 py-1.5 rounded-full text-white uppercase tracking-wider">
          {history.length} ENTRIES
        </span>
      </div>

      {/* Filter Chips */}
      <div className="flex items-center gap-2">
        <button
          onClick={() => setSelectedFilter('all')}
          className={`px-4 py-2 rounded-full text-[10px] font-black uppercase tracking-[0.2em] font-mono-data transition-colors border ${
            selectedFilter === 'all'
              ? 'bg-white text-black border-white'
              : 'bg-black text-white/60 hover:text-white border-white/20'
          }`}
        >
          ALL ACTIVITY
        </button>
        <button
          onClick={() => setSelectedFilter('sos')}
          className={`px-4 py-2 rounded-full text-[10px] font-black uppercase tracking-[0.2em] font-mono-data transition-colors border ${
            selectedFilter === 'sos'
              ? 'bg-white text-black border-white'
              : 'bg-black text-white/60 hover:text-white border-white/20'
          }`}
        >
          SOS DISPATCHES
        </button>
        <button
          onClick={() => setSelectedFilter('transport')}
          className={`px-4 py-2 rounded-full text-[10px] font-black uppercase tracking-[0.2em] font-mono-data transition-colors border ${
            selectedFilter === 'transport'
              ? 'bg-white text-black border-white'
              : 'bg-black text-white/60 hover:text-white border-white/20'
          }`}
        >
          FACILITY ROUTES
        </button>
      </div>

      {/* History Items List */}
      <div className="flex flex-col gap-3.5">
        {filteredHistory.map((item) => (
          <div
            key={item.id}
            className="bg-black rounded-3xl p-5 border border-white/20 shadow-xl flex flex-col gap-3.5"
          >
            <div className="flex items-start justify-between border-b border-white/10 pb-3">
              <div className="flex items-center gap-3">
                <div
                  className={`w-10 h-10 rounded-full flex items-center justify-center border ${
                    item.type === 'emergency_sos'
                      ? 'bg-[#FF3E00] text-black border-[#FF3E00]'
                      : 'bg-white text-black border-white'
                  }`}
                >
                  <span className="material-symbols-outlined text-[18px] font-black">
                    {item.type === 'emergency_sos' ? 'emergency' : 'directions_car'}
                  </span>
                </div>
                <div>
                  <h3 className="text-[16px] font-black text-white leading-tight font-display uppercase">
                    {item.title}
                  </h3>
                  <p className="text-[10px] text-white/60 font-mono-data mt-0.5 uppercase tracking-wider">
                    {item.date} • {item.time}
                  </p>
                </div>
              </div>

              <span
                className={`text-[9px] font-black px-3 py-1 rounded-full font-mono-data uppercase tracking-wider border ${
                  item.status === 'Completed'
                    ? 'bg-white/10 text-white border-white/30'
                    : 'bg-[#FF3E00]/20 text-[#FF3E00] border-[#FF3E00]/40'
                }`}
              >
                {item.status}
              </span>
            </div>

            {/* Details Grid */}
            <div className="bg-white/5 p-3.5 rounded-2xl border border-white/10 grid grid-cols-2 gap-3 text-[11px] font-mono-data">
              <div>
                <span className="text-white/40 block text-[9px] uppercase tracking-wider">Origin Location</span>
                <p className="text-white font-bold truncate mt-0.5 uppercase">{item.location}</p>
              </div>
              <div>
                <span className="text-white/40 block text-[9px] uppercase tracking-wider">Target Facility</span>
                <p className="text-white font-bold truncate mt-0.5 uppercase">
                  {item.destinationHospital}
                </p>
              </div>
              <div>
                <span className="text-white/40 block text-[9px] uppercase tracking-wider">Assigned Unit</span>
                <p className="text-white font-bold mt-0.5 uppercase">{item.unitAssigned}</p>
              </div>
              <div>
                <span className="text-white/40 block text-[9px] uppercase tracking-wider">Response Intercept</span>
                <p className="text-white font-bold mt-0.5 uppercase">
                  {item.responseTime}
                </p>
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};
