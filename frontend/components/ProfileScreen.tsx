import React, { useState } from 'react';
import { UserProfile } from '../types';

interface ProfileScreenProps {
  profile: UserProfile;
  onUpdateProfile?: (updated: UserProfile) => void;
}

export const ProfileScreen: React.FC<ProfileScreenProps> = ({ profile }) => {
  const [organDonor, setOrganDonor] = useState(profile.organDonor);
  const [autoShareVitals, setAutoShareVitals] = useState(true);

  return (
    <div className="flex flex-col w-full p-4 gap-5 bg-black min-h-screen pb-28 max-w-lg mx-auto">
      {/* Profile Header */}
      <div className="bg-black rounded-3xl p-5 border border-white/20 shadow-xl flex items-center gap-4">
        <div className="relative w-16 h-16 rounded-full overflow-hidden border-2 border-white/40 shadow-md shrink-0">
          <img
            src={profile.avatar}
            alt={profile.name}
            className="w-full h-full object-cover grayscale contrast-125"
          />
        </div>
        <div className="flex-1 min-w-0">
          <div className="flex items-center gap-2">
            <h2 className="text-[20px] font-black text-white truncate font-display uppercase">{profile.name}</h2>
            <span className="bg-white/10 text-white text-[9px] font-mono-data font-bold px-2 py-0.5 rounded-full border border-white/25 uppercase">
              VERIFIED
            </span>
          </div>
          <p className="text-[12px] font-mono-data text-white/60 truncate mt-0.5">{profile.phone}</p>
          <p className="text-[11px] font-mono-data text-white/40 truncate uppercase">{profile.email}</p>
        </div>
      </div>

      {/* Emergency Medical ID Card */}
      <div className="bg-black rounded-3xl p-5 border border-white/20 shadow-xl flex flex-col gap-3.5">
        <div className="flex items-center justify-between border-b border-white/10 pb-3">
          <div className="flex items-center gap-2 text-white">
            <span className="material-symbols-outlined text-[20px] text-[#FF3E00]">medical_information</span>
            <h3 className="text-[14px] font-black text-white uppercase tracking-wider font-display">
              EMERGENCY MEDICAL ID
            </h3>
          </div>
          <span className="text-[9px] font-mono-data font-black text-black bg-[#FF3E00] px-2.5 py-0.5 rounded-full uppercase tracking-wider">
            CRITICAL
          </span>
        </div>

        <div className="bg-white/5 p-4 rounded-2xl border border-white/10 space-y-3 font-mono-data">
          <div className="flex justify-between items-center border-b border-white/10 pb-2.5">
            <span className="text-[12px] text-white/60 uppercase tracking-wider">Blood Group</span>
            <span className="text-[16px] font-mono-data font-black text-[#FF3E00]">
              {profile.bloodType}
            </span>
          </div>

          <div className="border-b border-white/10 pb-2.5">
            <span className="text-[10px] text-white/40 block mb-1.5 uppercase tracking-wider">Known Allergies</span>
            <div className="flex flex-wrap gap-1.5">
              {profile.allergies.map((allergy, i) => (
                <span
                  key={i}
                  className="bg-[#FF3E00]/15 text-[#FF3E00] text-[10px] font-bold px-2.5 py-1 rounded-full border border-[#FF3E00]/30 uppercase"
                >
                  {allergy}
                </span>
              ))}
            </div>
          </div>

          <div className="border-b border-white/10 pb-2.5">
            <span className="text-[10px] text-white/40 block mb-1.5 uppercase tracking-wider">Medical Conditions</span>
            <div className="flex flex-wrap gap-1.5">
              {profile.medicalConditions.map((cond, i) => (
                <span
                  key={i}
                  className="bg-white/10 text-white text-[10px] font-bold px-2.5 py-1 rounded-full border border-white/15 uppercase"
                >
                  {cond}
                </span>
              ))}
            </div>
          </div>

          <div>
            <span className="text-[10px] text-white/40 block mb-1.5 uppercase tracking-wider">Prescribed Medications</span>
            <div className="flex flex-wrap gap-1.5">
              {profile.medications.map((med, i) => (
                <span
                  key={i}
                  className="bg-white/10 text-white text-[10px] font-bold px-2.5 py-1 rounded-full border border-white/20 uppercase"
                >
                  {med}
                </span>
              ))}
            </div>
          </div>
        </div>
      </div>

      {/* Emergency Contacts */}
      <div className="bg-black rounded-3xl p-5 border border-white/20 shadow-xl flex flex-col gap-3.5">
        <h3 className="text-[14px] font-black text-white flex items-center gap-2 uppercase font-display border-b border-white/10 pb-3">
          <span className="material-symbols-outlined text-[18px]">
            contact_emergency
          </span>
          EMERGENCY CONTACTS
        </h3>

        <div className="space-y-2.5">
          {profile.emergencyContacts.map((contact) => (
            <div
              key={contact.id}
              className="bg-white/5 p-3.5 rounded-2xl border border-white/10 flex items-center justify-between font-mono-data"
            >
              <div>
                <div className="flex items-center gap-2">
                  <h4 className="text-[14px] font-bold text-white uppercase">{contact.name}</h4>
                  {contact.isPrimary && (
                    <span className="bg-white text-black text-[9px] font-black px-2 py-0.5 rounded-full font-display">
                      PRIMARY
                    </span>
                  )}
                </div>
                <p className="text-[11px] text-white/60 uppercase mt-0.5">{contact.relation} • {contact.phone}</p>
              </div>

              <a
                href={`tel:${contact.phone}`}
                className="w-10 h-10 rounded-full bg-white hover:bg-white/90 text-black flex items-center justify-center shadow-md active:scale-95 transition-all"
              >
                <span className="material-symbols-outlined text-[18px] font-bold">call</span>
              </a>
            </div>
          ))}
        </div>
      </div>

      {/* Insurance & Settings */}
      <div className="bg-black rounded-3xl p-5 border border-white/20 shadow-xl space-y-3.5">
        <h3 className="text-[14px] font-black text-white flex items-center gap-2 uppercase font-display border-b border-white/10 pb-3">
          <span className="material-symbols-outlined text-[18px]">
            health_and_safety
          </span>
          DISPATCH & TELEMETRY SETTINGS
        </h3>

        <div className="bg-white/5 p-4 rounded-2xl border border-white/10 space-y-3.5 text-[12px] font-mono-data">
          <div className="flex justify-between items-center border-b border-white/10 pb-3">
            <div>
              <p className="text-white font-bold uppercase text-[13px]">{profile.insuranceProvider}</p>
              <p className="text-[10px] text-white/50 uppercase tracking-wider mt-0.5">POLICY: {profile.policyNumber}</p>
            </div>
            <span className="text-white bg-white/10 border border-white/20 px-2.5 py-1 rounded-full text-[9px] font-black uppercase">ACTIVE</span>
          </div>

          <div className="flex items-center justify-between">
            <span className="text-white/70 uppercase">Organ Donor Registered</span>
            <button
              onClick={() => setOrganDonor(!organDonor)}
              className={`w-12 h-6 rounded-full transition-colors relative border border-white/20 ${
                organDonor ? 'bg-white' : 'bg-black'
              }`}
            >
              <div
                className={`w-4 h-4 rounded-full absolute top-0.5 transition-transform ${
                  organDonor ? 'right-1 bg-black' : 'left-1 bg-white/40'
                }`}
              />
            </button>
          </div>

          <div className="pt-2 border-t border-white/10 flex items-center justify-between">
            <span className="text-white/70 uppercase">Auto-transmit Medical ID</span>
            <button
              onClick={() => setAutoShareVitals(!autoShareVitals)}
              className={`w-12 h-6 rounded-full transition-colors relative border border-white/20 ${
                autoShareVitals ? 'bg-white' : 'bg-black'
              }`}
            >
              <div
                className={`w-4 h-4 rounded-full absolute top-0.5 transition-transform ${
                  autoShareVitals ? 'right-1 bg-black' : 'left-1 bg-white/40'
                }`}
              />
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};
