import React from 'react';
import { TabType } from '../types';

interface BottomNavProps {
  activeTab: TabType;
  onTabChange: (tab: TabType) => void;
  hasActiveSos?: boolean;
}

export const BottomNav: React.FC<BottomNavProps> = ({
  activeTab,
  onTabChange,
  hasActiveSos = false,
}) => {
  const tabs: { id: TabType; label: string; icon: string }[] = [
    { id: 'home', label: 'Home', icon: 'map' },
    { id: 'hospitals', label: 'Hospitals', icon: 'medical_services' },
    { id: 'history', label: 'History', icon: 'history' },
    { id: 'profile', label: 'Profile', icon: 'person' },
  ];

  return (
    <nav
      className="fixed bottom-0 left-0 right-0 z-50 pb-safe bg-black/95 backdrop-blur-xl border-t border-white/10 shadow-[0_-8px_32px_rgba(0,0,0,0.9)]"
      id="bottom-navigation-bar"
    >
      <div className="grid grid-cols-4 items-center h-16 px-2 max-w-lg mx-auto w-full gap-1">
        {tabs.map((tab) => {
          const isActive = activeTab === tab.id;
          return (
            <button
              key={tab.id}
              onClick={() => onTabChange(tab.id)}
              id={`nav-tab-${tab.id}`}
              className={`w-full flex flex-col items-center justify-center gap-1 py-1.5 px-1 rounded-2xl transition-all duration-200 cursor-pointer ${
                isActive
                  ? 'bg-white text-black shadow-lg font-black'
                  : 'text-white/40 hover:text-white active:scale-95'
              }`}
            >
              <div className="relative flex items-center justify-center">
                <span
                  className="material-symbols-outlined text-[19px] sm:text-[21px]"
                  style={
                    isActive
                      ? { fontVariationSettings: "'FILL' 1, 'wght' 700" }
                      : undefined
                  }
                >
                  {tab.icon}
                </span>
                {tab.id === 'home' && hasActiveSos && (
                  <span className="absolute -top-0.5 -right-0.5 w-2 h-2 bg-[#FF3E00] rounded-full animate-ping" />
                )}
              </div>
              <span className="text-[9px] sm:text-[10px] font-black uppercase tracking-[0.08em] leading-none font-display truncate max-w-full">
                {tab.label}
              </span>
            </button>
          );
        })}
      </div>
    </nav>
  );
};
