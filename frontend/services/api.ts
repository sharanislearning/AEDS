import {
  Hospital,
  Incident,
  DispatchHistoryItem,
  UserProfile,
} from '../types';
import {
  INITIAL_HOSPITALS,
  INITIAL_HISTORY,
  INITIAL_USER_PROFILE,
  PRIMARY_PARAMEDIC,
} from '../data/mockData';

const BASE_URL = '/api';

export const api = {
  // Hospitals API
  async getHospitals(): Promise<Hospital[]> {
    try {
      const res = await fetch(`${BASE_URL}/hospitals`);
      if (!res.ok) throw new Error(`HTTP error! status: ${res.status}`);
      const json = await res.json();
      return json.data || INITIAL_HOSPITALS;
    } catch (err) {
      console.warn('[API fallback] getHospitals failed, using local data', err);
      return INITIAL_HOSPITALS;
    }
  },

  async getHospitalById(id: string): Promise<Hospital | undefined> {
    try {
      const res = await fetch(`${BASE_URL}/hospitals/${id}`);
      if (!res.ok) throw new Error(`HTTP error! status: ${res.status}`);
      const json = await res.json();
      return json.data;
    } catch (err) {
      console.warn('[API fallback] getHospitalById failed, searching local data', err);
      return INITIAL_HOSPITALS.find((h) => h.id === id);
    }
  },

  // Incident & SOS API
  async getActiveIncident(): Promise<Incident | null> {
    try {
      const res = await fetch(`${BASE_URL}/incidents/active`);
      if (!res.ok) throw new Error(`HTTP error! status: ${res.status}`);
      const json = await res.json();
      return json.data || null;
    } catch (err) {
      console.warn('[API fallback] getActiveIncident failed', err);
      return null;
    }
  },

  async triggerSos(params?: {
    address?: string;
    targetHospitalId?: string;
    incidentType?: string;
  }): Promise<Incident> {
    try {
      const res = await fetch(`${BASE_URL}/incidents/sos`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(params || {}),
      });
      if (!res.ok) throw new Error(`HTTP error! status: ${res.status}`);
      const json = await res.json();
      return json.data;
    } catch (err) {
      console.warn('[API fallback] triggerSos failed, generating client incident', err);
      return {
        id: `sos-${Date.now()}`,
        status: 'en_route',
        address: params?.address || 'Srishti Complex, Mira Road East',
        targetHospitalId: params?.targetHospitalId || 'bhaktivedanta',
        unit: PRIMARY_PARAMEDIC,
        progressPercent: 65,
        initialEtaMins: 4,
        currentEtaMins: 4,
        sosActivatedAt: new Date().toLocaleTimeString([], {
          hour: '2-digit',
          minute: '2-digit',
        }),
        incidentType: 'Priority Emergency Medical Response',
      };
    }
  },

  async cancelIncident(id: string): Promise<boolean> {
    try {
      const res = await fetch(`${BASE_URL}/incidents/${id}/cancel`, {
        method: 'POST',
      });
      if (!res.ok) throw new Error(`HTTP error! status: ${res.status}`);
      const json = await res.json();
      return json.data?.cancelled ?? true;
    } catch (err) {
      console.warn('[API fallback] cancelIncident failed', err);
      return true;
    }
  },

  // History API
  async getHistory(): Promise<DispatchHistoryItem[]> {
    try {
      const res = await fetch(`${BASE_URL}/incidents/history`);
      if (!res.ok) throw new Error(`HTTP error! status: ${res.status}`);
      const json = await res.json();
      return json.data || INITIAL_HISTORY;
    } catch (err) {
      console.warn('[API fallback] getHistory failed, using local history', err);
      return INITIAL_HISTORY;
    }
  },

  // Profile API
  async getProfile(): Promise<UserProfile> {
    try {
      const res = await fetch(`${BASE_URL}/profile`);
      if (!res.ok) throw new Error(`HTTP error! status: ${res.status}`);
      const json = await res.json();
      return json.data || INITIAL_USER_PROFILE;
    } catch (err) {
      console.warn('[API fallback] getProfile failed, using local profile', err);
      return INITIAL_USER_PROFILE;
    }
  },

  async updateProfile(updates: Partial<UserProfile>): Promise<UserProfile> {
    try {
      const res = await fetch(`${BASE_URL}/profile`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(updates),
      });
      if (!res.ok) throw new Error(`HTTP error! status: ${res.status}`);
      const json = await res.json();
      return json.data;
    } catch (err) {
      console.warn('[API fallback] updateProfile failed', err);
      return { ...INITIAL_USER_PROFILE, ...updates };
    }
  },
};
