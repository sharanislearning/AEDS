import React, { useState, useEffect } from 'react';
import {
  ActiveScreen,
  TabType,
  Hospital,
  Incident,
  DispatchHistoryItem,
  UserProfile,
} from './types';
import {
  INITIAL_HOSPITALS,
  PRIMARY_PARAMEDIC,
  INITIAL_USER_PROFILE,
  INITIAL_HISTORY,
} from './data/mockData';
import { api } from './services/api';
import { Header } from './components/Header';
import { BottomNav } from './components/BottomNav';
import { HomeScreen } from './components/HomeScreen';
import { HospitalsScreen } from './components/HospitalsScreen';
import { HospitalDetailsScreen } from './components/HospitalDetailsScreen';
import { IncidentDetailsScreen } from './components/IncidentDetailsScreen';
import { NavigationScreen } from './components/NavigationScreen';
import { HistoryScreen } from './components/HistoryScreen';
import { ProfileScreen } from './components/ProfileScreen';
import { CallModal, TextModal } from './components/CommunicationModals';

export default function App() {
  const [activeScreen, setActiveScreen] = useState<ActiveScreen>({
    type: 'tab',
    tab: 'home',
  });
  const [hospitals, setHospitals] = useState<Hospital[]>(INITIAL_HOSPITALS);
  const [history, setHistory] = useState<DispatchHistoryItem[]>(INITIAL_HISTORY);
  const [userProfile, setUserProfile] = useState<UserProfile>(INITIAL_USER_PROFILE);
  const [activeIncident, setActiveIncident] = useState<Incident | null>(null);

  // Communication Modals
  const [isCallOpen, setIsCallOpen] = useState(false);
  const [isTextOpen, setIsTextOpen] = useState(false);

  // Initial load from backend API (with automatic fallback to mock data)
  useEffect(() => {
    async function loadData() {
      try {
        const [fetchedHospitals, fetchedHistory, fetchedProfile, fetchedIncident] = await Promise.all([
          api.getHospitals(),
          api.getHistory(),
          api.getProfile(),
          api.getActiveIncident(),
        ]);
        if (fetchedHospitals && fetchedHospitals.length > 0) setHospitals(fetchedHospitals);
        if (fetchedHistory && fetchedHistory.length > 0) setHistory(fetchedHistory);
        if (fetchedProfile) setUserProfile(fetchedProfile);
        if (fetchedIncident) setActiveIncident(fetchedIncident);
      } catch (err) {
        console.warn('Initial data load error:', err);
      }
    }
    loadData();
  }, []);

  // Determine current active tab
  const currentTab: TabType =
    activeScreen.type === 'tab'
      ? activeScreen.tab
      : activeScreen.type === 'hospital_details'
      ? 'hospitals'
      : 'home';

  // SOS activation handler
  const handleTriggerSos = async () => {
    try {
      const newIncident = await api.triggerSos({
        address: '1245 Main St',
        targetHospitalId: 'mercy-gen',
        incidentType: 'Life-Threatening Emergency SOS',
      });
      setActiveIncident(newIncident);
      setActiveScreen({ type: 'incident_details', incidentId: newIncident.id });

      // Refresh history from API
      const updatedHistory = await api.getHistory();
      setHistory(updatedHistory);
    } catch (err) {
      console.error('Failed to trigger SOS:', err);
      // Fallback
      const fallbackIncident: Incident = {
        id: `sos-${Date.now()}`,
        status: 'en_route',
        address: '1245 Main St',
        targetHospitalId: 'mercy-gen',
        unit: PRIMARY_PARAMEDIC,
        progressPercent: 65,
        initialEtaMins: 4,
        currentEtaMins: 4,
        sosActivatedAt: new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }),
        incidentType: 'Life-Threatening Emergency SOS',
      };
      setActiveIncident(fallbackIncident);
      setActiveScreen({ type: 'incident_details', incidentId: fallbackIncident.id });
    }
  };

  const handleCancelIncident = async () => {
    if (activeIncident) {
      await api.cancelIncident(activeIncident.id);
    }
    setActiveIncident(null);
    setActiveScreen({ type: 'tab', tab: 'home' });
  };

  // Screen header title resolution
  const getHeaderInfo = () => {
    if (activeScreen.type === 'tab') {
      switch (activeScreen.tab) {
        case 'home':
          return { title: 'Home', showBack: false };
        case 'hospitals':
          return { title: 'Hospitals', showBack: false };
        case 'history':
          return { title: 'History', showBack: false };
        case 'profile':
          return { title: 'Profile', showBack: false };
      }
    } else if (activeScreen.type === 'hospital_details') {
      return { title: 'Hospital Details', showBack: true };
    } else if (activeScreen.type === 'incident_details') {
      return { title: 'Incident Details', showBack: true };
    } else if (activeScreen.type === 'navigation') {
      return { title: 'Tactical Navigation', showBack: true };
    }
    return { title: 'AERS Pro', showBack: false };
  };

  const headerInfo = getHeaderInfo();

  const handleHeaderBack = () => {
    if (activeScreen.type === 'hospital_details') {
      setActiveScreen({ type: 'tab', tab: 'hospitals' });
    } else if (activeScreen.type === 'incident_details') {
      setActiveScreen({ type: 'tab', tab: 'home' });
    } else if (activeScreen.type === 'navigation') {
      setActiveScreen({
        type: 'hospital_details',
        hospitalId: activeScreen.hospitalId,
      });
    } else {
      setActiveScreen({ type: 'tab', tab: 'home' });
    }
  };

  const selectedHospital =
    activeScreen.type === 'hospital_details' || activeScreen.type === 'navigation'
      ? hospitals.find((h) => h.id === activeScreen.hospitalId) || hospitals[0]
      : hospitals[0];

  return (
    <div className="min-h-screen bg-black text-white flex flex-col font-sans selection:bg-[#FF3E00] selection:text-black">
      {/* Dynamic Header */}
      <Header
        title={headerInfo.title}
        showBack={headerInfo.showBack}
        onBack={handleHeaderBack}
        onProfileClick={() => setActiveScreen({ type: 'tab', tab: 'profile' })}
      />

      {/* Main Screen Container with Header Spacing */}
      <main className="flex-1 w-full pt-16 relative">
        {activeScreen.type === 'tab' && activeScreen.tab === 'home' && (
          <HomeScreen
            hospitals={hospitals}
            onFindHospitals={() => setActiveScreen({ type: 'tab', tab: 'hospitals' })}
            onSosTrigger={handleTriggerSos}
            onSelectHospital={(hospitalId) =>
              setActiveScreen({ type: 'hospital_details', hospitalId })
            }
            onSelectIncident={(incidentId) =>
              setActiveScreen({ type: 'incident_details', incidentId })
            }
            activeSosIncidentId={activeIncident ? activeIncident.id : null}
          />
        )}

        {activeScreen.type === 'tab' && activeScreen.tab === 'hospitals' && (
          <HospitalsScreen
            hospitals={hospitals}
            onSelectHospital={(hospitalId) =>
              setActiveScreen({ type: 'hospital_details', hospitalId })
            }
            onStartRoute={(hospitalId) =>
              setActiveScreen({ type: 'hospital_details', hospitalId })
            }
          />
        )}

        {activeScreen.type === 'tab' && activeScreen.tab === 'history' && (
          <HistoryScreen
            history={history}
            onSelectHospitalByName={(name) => {
              const found = hospitals.find((h) =>
                h.name.toLowerCase().includes(name.toLowerCase())
              );
              if (found) {
                setActiveScreen({ type: 'hospital_details', hospitalId: found.id });
              }
            }}
          />
        )}

        {activeScreen.type === 'tab' && activeScreen.tab === 'profile' && (
          <ProfileScreen
            profile={userProfile}
            onUpdateProfile={(updated) => setUserProfile(updated)}
          />
        )}

        {activeScreen.type === 'hospital_details' && (
          <HospitalDetailsScreen
            hospital={selectedHospital}
            onBack={handleHeaderBack}
            onStartNavigation={(hospitalId) =>
              setActiveScreen({ type: 'navigation', hospitalId })
            }
            onViewOtherHospitals={() =>
              setActiveScreen({ type: 'tab', tab: 'hospitals' })
            }
          />
        )}

        {activeScreen.type === 'incident_details' && (
          <IncidentDetailsScreen
            unit={activeIncident?.unit || PRIMARY_PARAMEDIC}
            destinationAddress={activeIncident?.address || '1245 Main St'}
            onBack={handleHeaderBack}
            onCallDriver={() => setIsCallOpen(true)}
            onTextDriver={() => setIsTextOpen(true)}
            onCancelRequest={handleCancelIncident}
            onViewLiveNavigation={() =>
              setActiveScreen({
                type: 'navigation',
                hospitalId: activeIncident?.targetHospitalId || 'mercy-gen',
              })
            }
          />
        )}

        {activeScreen.type === 'navigation' && (
          <NavigationScreen
            hospital={selectedHospital}
            onEndNavigation={() => setActiveScreen({ type: 'tab', tab: 'home' })}
            onSwitchToHospitalDetails={() =>
              setActiveScreen({
                type: 'hospital_details',
                hospitalId: selectedHospital.id,
              })
            }
          />
        )}
      </main>

      {/* Floating Bottom Navigation Bar (Shown on top-level tabs) */}
      {activeScreen.type === 'tab' && (
        <BottomNav
          activeTab={currentTab}
          onTabChange={(tab) => setActiveScreen({ type: 'tab', tab })}
          hasActiveSos={!!activeIncident}
        />
      )}

      {/* Encrypted Voice Call Simulation Modal */}
      <CallModal
        isOpen={isCallOpen}
        onClose={() => setIsCallOpen(false)}
        paramedicName={PRIMARY_PARAMEDIC.paramedicName}
        unitNumber={PRIMARY_PARAMEDIC.unitNumber}
      />

      {/* Instant Tactical Messaging Simulation Modal */}
      <TextModal
        isOpen={isTextOpen}
        onClose={() => setIsTextOpen(false)}
        paramedicName={PRIMARY_PARAMEDIC.paramedicName}
        unitNumber={PRIMARY_PARAMEDIC.unitNumber}
      />
    </div>
  );
}
