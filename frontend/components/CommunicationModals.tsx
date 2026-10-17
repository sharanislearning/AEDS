import React, { useState, useEffect, useRef } from 'react';
import { PARAMEDIC_DAVID_AVATAR } from '../data/mockData';

interface CallModalProps {
  isOpen: boolean;
  onClose: () => void;
  paramedicName?: string;
  unitNumber?: string;
}

export const CallModal: React.FC<CallModalProps> = ({
  isOpen,
  onClose,
  paramedicName = 'David M.',
  unitNumber = 'Unit 42',
}) => {
  const [callDuration, setCallDuration] = useState(0);
  const [isMuted, setIsMuted] = useState(false);
  const [isSpeaker, setIsSpeaker] = useState(true);

  useEffect(() => {
    if (!isOpen) {
      setCallDuration(0);
      return;
    }
    const timer = setInterval(() => {
      setCallDuration((prev) => prev + 1);
    }, 1000);
    return () => clearInterval(timer);
  }, [isOpen]);

  if (!isOpen) return null;

  const formatTime = (secs: number) => {
    const m = Math.floor(secs / 60);
    const s = secs % 60;
    return `${m < 10 ? '0' : ''}${m}:${s < 10 ? '0' : ''}${s}`;
  };

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/95 backdrop-blur-2xl p-4 animate-fadeIn">
      <div className="bg-black rounded-3xl p-6 max-w-sm w-full border border-white/20 shadow-2xl flex flex-col items-center text-center">
        {/* Paramedic Avatar with Live Call Waves */}
        <div className="relative my-4">
          <div className="w-28 h-28 rounded-full overflow-hidden border-2 border-white/30 shadow-[0_0_30px_rgba(255,255,255,0.15)]">
            <img
              src={PARAMEDIC_DAVID_AVATAR}
              alt={paramedicName}
              className="w-full h-full object-cover grayscale contrast-125"
            />
          </div>
          <div className="absolute -bottom-1 right-2 bg-white text-black p-1.5 rounded-full shadow-md">
            <span className="material-symbols-outlined text-[16px] font-bold">call</span>
          </div>
        </div>

        <span className="text-[9px] font-mono-data uppercase tracking-[0.3em] text-white/40 block mb-1">
          ENCRYPTED DISPATCH FREQUENCY
        </span>
        <h2 className="text-[26px] font-black text-white uppercase font-display tracking-tight">{paramedicName}</h2>
        <p className="text-[12px] text-white/70 font-bold uppercase font-mono-data tracking-wider mt-0.5">
          LEAD PARAMEDIC • {unitNumber}
        </p>
        <p className="text-[13px] text-white/90 font-mono-data font-black tracking-widest mt-2">
          {callDuration === 0 ? 'CONNECTING COMMS...' : `LIVE [${formatTime(callDuration)}]`}
        </p>

        {/* Audio Waveform simulation */}
        <div className="flex items-center gap-1.5 my-6 h-8">
          {[40, 75, 100, 60, 85, 30, 95, 50, 70, 90, 45, 80].map((h, i) => (
            <div
              key={i}
              className="w-1 bg-white rounded-full animate-pulse"
              style={{
                height: `${h}%`,
                animationDelay: `${i * 0.1}s`,
                opacity: isMuted ? 0.2 : 0.85,
              }}
            />
          ))}
        </div>

        {/* Call Controls */}
        <div className="grid grid-cols-3 gap-3 w-full mb-6">
          <button
            onClick={() => setIsMuted(!isMuted)}
            className={`p-3 rounded-2xl flex flex-col items-center gap-1 text-[10px] font-black uppercase tracking-[0.2em] font-mono-data transition-colors border ${
              isMuted ? 'bg-[#FF3E00] text-black border-[#FF3E00]' : 'bg-white/5 text-white/80 hover:text-white border-white/15'
            }`}
          >
            <span className="material-symbols-outlined text-[20px]">
              {isMuted ? 'mic_off' : 'mic'}
            </span>
            {isMuted ? 'MUTED' : 'MUTE'}
          </button>

          <button
            onClick={() => setIsSpeaker(!isSpeaker)}
            className={`p-3 rounded-2xl flex flex-col items-center gap-1 text-[10px] font-black uppercase tracking-[0.2em] font-mono-data transition-colors border ${
              isSpeaker ? 'bg-white text-black border-white' : 'bg-white/5 text-white/80 hover:text-white border-white/15'
            }`}
          >
            <span className="material-symbols-outlined text-[20px]">volume_up</span>
            SPEAKER
          </button>

          <button
            className="p-3 rounded-2xl flex flex-col items-center gap-1 text-[10px] font-black uppercase tracking-[0.2em] font-mono-data bg-white/5 text-white/80 hover:text-white border border-white/15"
          >
            <span className="material-symbols-outlined text-[20px]">dialpad</span>
            KEYPAD
          </button>
        </div>

        {/* End Call Button */}
        <button
          onClick={onClose}
          className="w-full bg-[#FF3E00] hover:bg-[#ff551f] text-black py-4 rounded-full font-black text-[12px] uppercase tracking-[0.25em] shadow-lg active:scale-95 transition-all flex items-center justify-center gap-2 font-display cursor-pointer"
        >
          <span className="material-symbols-outlined text-[20px] font-black">call_end</span>
          END EMERGENCY CALL
        </button>
      </div>
    </div>
  );
};

interface TextModalProps {
  isOpen: boolean;
  onClose: () => void;
  paramedicName?: string;
  unitNumber?: string;
}

interface Message {
  id: string;
  sender: 'user' | 'driver';
  text: string;
  time: string;
}

export const TextModal: React.FC<TextModalProps> = ({
  isOpen,
  onClose,
  paramedicName = 'David M.',
  unitNumber = 'Unit 42',
}) => {
  const [messages, setMessages] = useState<Message[]>([
    {
      id: 'm1',
      sender: 'driver',
      text: "Hi Elena, this is David from Unit 42. We're turning onto Main St right now. Siren and lights active. ETA ~3-4 mins.",
      time: '14:23',
    },
    {
      id: 'm2',
      sender: 'driver',
      text: 'Are there any locked gates or security codes at 1245 Main St?',
      time: '14:24',
    },
  ]);
  const [inputVal, setInputVal] = useState('');
  const chatEndRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    if (isOpen) {
      chatEndRef.current?.scrollIntoView({ behavior: 'smooth' });
    }
  }, [messages, isOpen]);

  if (!isOpen) return null;

  const handleSend = () => {
    if (!inputVal.trim()) return;
    const newMsg: Message = {
      id: Date.now().toString(),
      sender: 'user',
      text: inputVal.trim(),
      time: new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }),
    };
    setMessages((prev) => [...prev, newMsg]);
    setInputVal('');

    // Simulate quick intelligent paramedic response
    setTimeout(() => {
      setMessages((prev) => [
        ...prev,
        {
          id: (Date.now() + 1).toString(),
          sender: 'driver',
          text: 'Understood. Dispatch team has updated our navigation terminal. We are right outside.',
          time: new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }),
        },
      ]);
    }, 1500);
  };

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/95 backdrop-blur-2xl p-3 animate-fadeIn">
      <div className="bg-black rounded-3xl w-full max-w-sm h-[580px] flex flex-col border border-white/20 shadow-2xl overflow-hidden">
        {/* Header */}
        <div className="bg-black p-4 flex items-center justify-between border-b border-white/15">
          <div className="flex items-center gap-3">
            <img
              src={PARAMEDIC_DAVID_AVATAR}
              alt={paramedicName}
              className="w-10 h-10 rounded-full object-cover border border-white/30 grayscale contrast-125"
            />
            <div>
              <h3 className="font-black text-white text-[16px] uppercase font-display">{paramedicName}</h3>
              <p className="text-[10px] font-mono-data uppercase tracking-wider text-white/60">{unitNumber} • ACTIVE CHANNEL</p>
            </div>
          </div>
          <button
            onClick={onClose}
            className="w-8 h-8 rounded-full bg-white/10 text-white hover:bg-white hover:text-black flex items-center justify-center transition-colors"
          >
            <span className="material-symbols-outlined text-[18px]">close</span>
          </button>
        </div>

        {/* Messages Body */}
        <div className="flex-1 p-4 overflow-y-auto space-y-3 bg-black">
          {messages.map((m) => {
            const isUser = m.sender === 'user';
            return (
              <div
                key={m.id}
                className={`flex flex-col ${isUser ? 'items-end' : 'items-start'}`}
              >
                <div
                  className={`max-w-[82%] px-4 py-3 rounded-2xl text-[13px] leading-relaxed shadow-sm ${
                    isUser
                      ? 'bg-white text-black rounded-br-none font-bold'
                      : 'bg-white/10 text-white rounded-bl-none border border-white/15'
                  }`}
                >
                  {m.text}
                </div>
                <span className="text-[9px] text-white/40 font-mono-data mt-1 px-1 tracking-wider">
                  {m.time}
                </span>
              </div>
            );
          })}
          <div ref={chatEndRef} />
        </div>

        {/* Quick Quick Replies */}
        <div className="px-3 py-2 bg-black border-t border-white/10 flex gap-1.5 overflow-x-auto no-scrollbar">
          {['Gate code is #4412', 'Front entrance on Main', 'Waiting at lobby'].map((q, i) => (
            <button
              key={i}
              onClick={() => {
                setInputVal(q);
              }}
              className="text-[10px] font-mono-data uppercase tracking-wider bg-white/5 hover:bg-white hover:text-black text-white/80 px-3 py-1.5 rounded-full whitespace-nowrap border border-white/15 transition-colors"
            >
              {q}
            </button>
          ))}
        </div>

        {/* Input Bar */}
        <div className="p-3 bg-black border-t border-white/15 flex items-center gap-2">
          <input
            type="text"
            value={inputVal}
            onChange={(e) => setInputVal(e.target.value)}
            onKeyDown={(e) => e.key === 'Enter' && handleSend()}
            placeholder="TYPE PRIORITY MESSAGE..."
            className="flex-1 bg-white/5 text-white placeholder:text-white/40 uppercase tracking-wider font-mono-data px-4 py-3 rounded-full text-[12px] border border-white/15 focus:outline-none focus:border-white"
          />
          <button
            onClick={handleSend}
            className="w-11 h-11 rounded-full bg-white text-black flex items-center justify-center shrink-0 active:scale-95 transition-transform hover:bg-white/90"
          >
            <span className="material-symbols-outlined text-[18px] font-bold">send</span>
          </button>
        </div>
      </div>
    </div>
  );
};
