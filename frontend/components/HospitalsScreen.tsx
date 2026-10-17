import React, { useState, useMemo } from 'react';
import { Hospital } from '../types';

interface HospitalsScreenProps {
  hospitals: Hospital[];
  onSelectHospital: (hospitalId: string) => void;
  onStartRoute: (hospitalId: string) => void;
}

export const HospitalsScreen: React.FC<HospitalsScreenProps> = ({
  hospitals,
  onSelectHospital,
  onStartRoute,
}) => {
  const [searchQuery, setSearchQuery] = useState('');
  const [selectedFilter, setSelectedFilter] = useState<'all' | 'er' | 'icu' | 'low_traffic' | 'trauma1'>('all');

  const filteredHospitals = useMemo(() => {
    return hospitals.filter((h) => {
      const matchesSearch =
        h.name.toLowerCase().includes(searchQuery.toLowerCase()) ||
        h.specialties.some((s) => s.toLowerCase().includes(searchQuery.toLowerCase())) ||
        h.city.toLowerCase().includes(searchQuery.toLowerCase());

      if (!matchesSearch) return false;

      if (selectedFilter === 'er') return h.erStatus === 'open';
      if (selectedFilter === 'icu') return h.icuStatus === 'available';
      if (selectedFilter === 'low_traffic') return h.trafficStatus === 'low';
      if (selectedFilter === 'trauma1') return h.traumaLevel === 'Level I';

      return true;
    });
  }, [hospitals, searchQuery, selectedFilter]);

  return (
    <div className="flex flex-col w-full p-4 gap-6 bg-black min-h-screen pb-28 max-w-lg mx-auto">
      {/* Header Eyebrow Info */}
      <div className="flex items-center justify-between border-b border-white/10 pb-3">
        <div>
          <span className="text-[9px] font-mono-data tracking-[0.3em] uppercase text-white/40 block">
            MIRA ROAD & MBMC CORRIDOR
          </span>
          <h2 className="text-[24px] font-black uppercase tracking-tighter text-white font-display">
            HOSPITALS NEARBY
          </h2>
        </div>
        <div className="text-right">
          <span className="text-[18px] font-black text-white font-mono-data block leading-none">
            0{filteredHospitals.length}
          </span>
          <span className="text-[9px] font-mono-data uppercase tracking-widest text-white/40">
            TRACKED FACILITIES
          </span>
        </div>
      </div>

      {/* Search Input */}
      <div className="relative w-full">
        <span className="material-symbols-outlined absolute left-4 top-1/2 -translate-y-1/2 text-white/50 text-[20px] pointer-events-none">
          search
        </span>
        <input
          type="text"
          value={searchQuery}
          onChange={(e) => setSearchQuery(e.target.value)}
          placeholder="SEARCH MEDICAL FACILITIES..."
          id="facility-search-input"
          className="w-full bg-black h-12 pl-12 pr-10 rounded-full text-white placeholder:text-white/30 text-[13px] font-mono-data uppercase tracking-wider focus:outline-none focus:border-white border border-white/20 transition-all"
        />
        {searchQuery && (
          <button
            onClick={() => setSearchQuery('')}
            className="absolute right-3 top-1/2 -translate-y-1/2 text-white/50 hover:text-white p-1"
          >
            <span className="material-symbols-outlined text-[18px]">close</span>
          </button>
        )}
      </div>

      {/* Filter Tabs */}
      <div className="flex items-center gap-2 overflow-x-auto no-scrollbar pb-1 -mt-2">
        <button
          onClick={() => setSelectedFilter('all')}
          className={`px-4 py-2 rounded-full text-[10px] font-black uppercase tracking-widest whitespace-nowrap transition-all ${
            selectedFilter === 'all'
              ? 'bg-white text-black shadow-md'
              : 'bg-black text-white/60 hover:text-white border border-white/20'
          }`}
        >
          ALL ({hospitals.length})
        </button>
        <button
          onClick={() => setSelectedFilter('icu')}
          className={`px-4 py-2 rounded-full text-[10px] font-black uppercase tracking-widest whitespace-nowrap transition-all flex items-center gap-1.5 ${
            selectedFilter === 'icu'
              ? 'bg-white text-black shadow-md'
              : 'bg-black text-white/60 hover:text-white border border-white/20'
          }`}
        >
          <span className="w-1.5 h-1.5 rounded-full bg-[#FF3E00]" />
          ICU AVAIL
        </button>
        <button
          onClick={() => setSelectedFilter('low_traffic')}
          className={`px-4 py-2 rounded-full text-[10px] font-black uppercase tracking-widest whitespace-nowrap transition-all ${
            selectedFilter === 'low_traffic'
              ? 'bg-white text-black shadow-md'
              : 'bg-black text-white/60 hover:text-white border border-white/20'
          }`}
        >
          LOW TRAFFIC
        </button>
        <button
          onClick={() => setSelectedFilter('trauma1')}
          className={`px-4 py-2 rounded-full text-[10px] font-black uppercase tracking-widest whitespace-nowrap transition-all ${
            selectedFilter === 'trauma1'
              ? 'bg-white text-black shadow-md'
              : 'bg-black text-white/60 hover:text-white border border-white/20'
          }`}
        >
          LEVEL I TRAUMA
        </button>
      </div>

      {/* Facilities List */}
      <div className="flex flex-col gap-4">
        {filteredHospitals.map((hospital) => {
          const isFastest = hospital.isFastest;

          if (isFastest) {
            // Featured Fastest Route Card (Mercy General Hospital)
            return (
              <div
                key={hospital.id}
                id={`hospital-card-${hospital.id}`}
                className="w-full bg-black rounded-2xl p-5 flex flex-col gap-4 border-2 border-white relative overflow-hidden shadow-[0_8px_32px_rgba(0,0,0,0.8)]"
              >
                {/* Fastest Route Tag */}
                <div className="flex items-center justify-between">
                  <div className="bg-[#FF3E00] text-black text-[9px] font-black uppercase tracking-[0.25em] px-3 py-1 rounded-full font-mono-data shadow-sm">
                    FASTEST ROUTE
                  </div>
                  <span className="text-[10px] font-mono-data font-bold tracking-widest text-white/60 uppercase">
                    ETA: {hospital.etaMins} MIN
                  </span>
                </div>

                <div className="flex justify-between items-end pt-1 border-b border-white/10 pb-3">
                  <div className="flex flex-col">
                    <span className="text-[9px] font-mono-data uppercase tracking-[0.3em] text-white/50 mb-1">
                      LEVEL I TRAUMA CENTER
                    </span>
                    <h3 className="text-[22px] font-black text-white leading-none font-display uppercase tracking-tight">
                      {hospital.name}
                    </h3>
                  </div>
                  <div className="text-right">
                    <span className="text-[32px] font-black text-white font-mono-data tracking-tighter leading-none block">
                      {hospital.etaMins}
                      <span className="text-[14px] font-bold text-white/60 ml-1">MIN</span>
                    </span>
                    <span className="text-[11px] font-mono-data text-white/50 tracking-wider">
                      {hospital.distance}
                    </span>
                  </div>
                </div>

                {/* Status Badges */}
                <div className="flex flex-wrap gap-2">
                  <div className="flex items-center gap-1.5 bg-white/10 px-3 py-1 rounded-full border border-white/20">
                    <div className="w-1.5 h-1.5 rounded-full bg-[#FF3E00] animate-ping" />
                    <span className="text-[10px] font-bold uppercase tracking-wider text-white font-mono-data">
                      ER OPEN
                    </span>
                  </div>
                  <div className="flex items-center gap-1.5 bg-white/10 px-3 py-1 rounded-full border border-white/20">
                    <div className="w-1.5 h-1.5 rounded-full bg-white" />
                    <span className="text-[10px] font-bold uppercase tracking-wider text-white font-mono-data">
                      ICU AVAIL
                    </span>
                  </div>
                  <div className="flex items-center gap-1.5 bg-white/5 px-3 py-1 rounded-full border border-white/10">
                    <span className="material-symbols-outlined text-[13px] text-white/60">
                      traffic
                    </span>
                    <span className="text-[10px] font-bold uppercase tracking-wider text-white/60 font-mono-data">
                      {hospital.trafficText}
                    </span>
                  </div>
                </div>

                {/* Action Button: Route Now */}
                <button
                  onClick={() => onStartRoute(hospital.id)}
                  id={`route-now-btn-${hospital.id}`}
                  className="w-full mt-1 bg-white hover:bg-white/90 text-black font-black text-[11px] uppercase tracking-[0.25em] py-3.5 rounded-full flex items-center justify-center gap-2 transition-transform active:scale-[0.98] shadow-lg font-display"
                >
                  <span className="material-symbols-outlined text-[18px] font-bold">
                    directions
                  </span>
                  ROUTE NOW
                </button>
              </div>
            );
          }

          // Standard Hospital Cards
          return (
            <div
              key={hospital.id}
              id={`hospital-card-${hospital.id}`}
              className="w-full bg-black rounded-2xl p-5 flex flex-col gap-3.5 border border-white/15 hover:border-white/40 transition-all shadow-md"
            >
              <div className="flex justify-between items-end border-b border-white/10 pb-3">
                <div className="flex flex-col">
                  <span className="text-[9px] font-mono-data uppercase tracking-[0.3em] text-white/40 mb-1">
                    {hospital.traumaLevel} FACILITY
                  </span>
                  <h3 className="text-[20px] font-black text-white leading-none font-display uppercase tracking-tight">
                    {hospital.name}
                  </h3>
                </div>
                <div className="text-right">
                  <span className="text-[24px] font-black text-white font-mono-data tracking-tighter leading-none block">
                    {hospital.etaMins}
                    <span className="text-[12px] font-bold text-white/50 ml-0.5">MIN</span>
                  </span>
                  <span className="text-[10px] font-mono-data text-white/40 tracking-wider">
                    {hospital.distance}
                  </span>
                </div>
              </div>

              {/* Status Badges */}
              <div className="flex flex-wrap gap-2">
                {hospital.erStatus === 'open' ? (
                  <div className="flex items-center gap-1.5 bg-white/10 px-3 py-1 rounded-full border border-white/20">
                    <div className="w-1.5 h-1.5 rounded-full bg-white" />
                    <span className="text-[10px] font-bold uppercase tracking-wider text-white font-mono-data">
                      ER OPEN
                    </span>
                  </div>
                ) : (
                  <div className="flex items-center gap-1.5 bg-white/5 px-3 py-1 rounded-full border border-white/10">
                    <div className="w-1.5 h-1.5 rounded-full bg-[#FF3E00]" />
                    <span className="text-[10px] font-bold uppercase tracking-wider text-[#FF3E00] font-mono-data">
                      ER BUSY
                    </span>
                  </div>
                )}

                {hospital.icuStatus === 'available' ? (
                  <div className="flex items-center gap-1.5 bg-white/10 px-3 py-1 rounded-full border border-white/20">
                    <div className="w-1.5 h-1.5 rounded-full bg-white" />
                    <span className="text-[10px] font-bold uppercase tracking-wider text-white font-mono-data">
                      ICU AVAIL
                    </span>
                  </div>
                ) : (
                  <div className="flex items-center gap-1.5 bg-white/5 px-3 py-1 rounded-full border border-white/10">
                    <div className="w-1.5 h-1.5 rounded-full bg-[#FF3E00]" />
                    <span className="text-[10px] font-bold uppercase tracking-wider text-[#FF3E00] font-mono-data">
                      ICU FULL
                    </span>
                  </div>
                )}

                <div className="flex items-center gap-1.5 bg-white/5 px-3 py-1 rounded-full border border-white/10">
                  <span
                    className={`material-symbols-outlined text-[13px] ${
                      hospital.trafficStatus === 'high'
                        ? 'text-[#FF3E00]'
                        : 'text-white/60'
                    }`}
                  >
                    traffic
                  </span>
                  <span
                    className={`text-[10px] font-bold uppercase tracking-wider font-mono-data ${
                      hospital.trafficStatus === 'high'
                        ? 'text-[#FF3E00]'
                        : 'text-white/60'
                    }`}
                  >
                    {hospital.trafficText}
                  </span>
                </div>
              </div>

              {/* View Details Button */}
              <button
                onClick={() => onSelectHospital(hospital.id)}
                id={`view-details-btn-${hospital.id}`}
                className="w-full mt-1 bg-black hover:bg-white hover:text-black text-white py-3 rounded-full flex items-center justify-center gap-2 font-black text-[11px] uppercase tracking-[0.25em] transition-all active:scale-[0.98] border border-white/25 font-display"
              >
                VIEW DETAILS
              </button>
            </div>
          );
        })}

        {filteredHospitals.length === 0 && (
          <div className="py-16 text-center text-white/40 border border-white/10 rounded-2xl">
            <span className="material-symbols-outlined text-[48px] text-white/20 mb-2">
              search_off
            </span>
            <p className="text-[16px] font-black uppercase tracking-tight text-white font-display">
              NO FACILITIES FOUND
            </p>
            <p className="text-[12px] font-mono-data text-white/50 mt-1 uppercase tracking-wider">
              Try adjusting your search query or filter criteria
            </p>
          </div>
        )}
      </div>
    </div>
  );
};
