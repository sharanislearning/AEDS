import React from 'react';
import { AERS_LOGO_URL, USER_AVATAR_URL } from '../data/mockData';

interface HeaderProps {
  title: string;
  showBack?: boolean;
  onBack?: () => void;
  onProfileClick?: () => void;
}

export const Header: React.FC<HeaderProps> = ({
  title,
  showBack = false,
  onBack,
  onProfileClick,
}) => {
  return (
    <header className="fixed top-0 w-full z-50 bg-black/90 backdrop-blur-xl pt-safe border-b border-white/10 shadow-[0_4px_20px_rgba(0,0,0,0.8)]">
      <div className="h-16 px-4 flex items-center justify-between max-w-lg mx-auto">
        {showBack ? (
          <div className="flex items-center justify-between w-full">
            <div className="flex items-center gap-3">
              <button
                onClick={onBack}
                id="header-back-btn"
                className="w-10 h-10 border border-white/20 rounded-full flex items-center justify-center text-white hover:bg-white hover:text-black transition-all duration-200 active:scale-95 shrink-0"
                aria-label="Go Back"
              >
                <span className="material-symbols-outlined text-[20px]">arrow_back</span>
              </button>
              <div className="flex flex-col">
                <span className="text-[9px] font-mono-data tracking-[0.3em] uppercase text-white/50 leading-none mb-1">
                  TACTICAL DISPATCH
                </span>
                <h1 className="text-[18px] font-black tracking-tighter uppercase text-white truncate font-display">
                  {title}
                </h1>
              </div>
            </div>
            <div className="w-8 h-8 border border-white/20 rounded-full flex items-center justify-center">
              <div className="w-1.5 h-1.5 bg-[#FF3E00] rounded-full animate-ping"></div>
            </div>
          </div>
        ) : (
          <>
            <div className="flex items-center gap-3">
              <div className="w-9 h-9 border border-white/20 rounded-full flex items-center justify-center bg-black">
                <img
                  src={AERS_LOGO_URL}
                  alt="AERS Pro Logo"
                  className="h-5 w-auto object-contain"
                />
              </div>
              <div className="flex flex-col">
                <span className="text-[9px] font-mono-data tracking-[0.3em] uppercase text-white/50 leading-none mb-1">
                  AERS.SYSTEMS // 2026
                </span>
                <span className="text-[19px] font-black tracking-tighter uppercase text-white leading-none font-display">
                  {title}
                </span>
              </div>
            </div>
            <div className="flex items-center gap-3">
              <div className="hidden sm:flex items-center gap-1 text-[10px] uppercase font-mono-data tracking-widest text-white/40 border border-white/10 px-2.5 py-1 rounded-full">
                <span className="w-1.5 h-1.5 bg-[#FF3E00] rounded-full"></span>
                LIVE
              </div>
              <button
                onClick={onProfileClick}
                id="header-profile-btn"
                className="w-10 h-10 border border-white/20 rounded-full p-0.5 focus:outline-none hover:border-white transition-all active:scale-95 overflow-hidden"
                title="Open Profile"
              >
                <img
                  src={USER_AVATAR_URL}
                  alt="Elena Rostova Profile"
                  className="w-full h-full rounded-full object-cover grayscale contrast-125 hover:grayscale-0 transition-all"
                />
              </button>
            </div>
          </>
        )}
      </div>
    </header>
  );
};
