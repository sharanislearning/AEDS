import { db, PRIMARY_PARAMEDIC } from '../data/initialDatabase';
import { Incident, DispatchHistoryItem } from '../types/backendTypes';

export class IncidentService {
  getActiveIncident(): Incident | null {
    return db.activeIncident;
  }

  createSosIncident(params?: {
    address?: string;
    targetHospitalId?: string;
    incidentType?: string;
  }): Incident {
    const newIncident: Incident = {
      id: `sos-${Date.now()}`,
      status: 'en_route',
      address: params?.address || '1245 Main St',
      targetHospitalId: params?.targetHospitalId || 'mercy-gen',
      unit: { ...PRIMARY_PARAMEDIC },
      progressPercent: 65,
      initialEtaMins: 4,
      currentEtaMins: 4,
      sosActivatedAt: new Date().toLocaleTimeString([], {
        hour: '2-digit',
        minute: '2-digit',
      }),
      incidentType: params?.incidentType || 'Priority Emergency Medical Response',
    };

    db.activeIncident = newIncident;
    return newIncident;
  }

  cancelIncident(id: string): boolean {
    if (db.activeIncident && db.activeIncident.id === id) {
      const now = new Date();
      const historyItem: DispatchHistoryItem = {
        id: `disp-${Date.now().toString().slice(-4)}`,
        date: now.toLocaleDateString('en-US', {
          month: 'short',
          day: 'numeric',
          year: 'numeric',
        }),
        time: `${now.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })} PST`,
        title: 'Emergency SOS Response',
        type: 'emergency_sos',
        location: db.activeIncident.address,
        destinationHospital: 'Mercy General Hospital',
        unitAssigned: db.activeIncident.unit.unitNumber,
        responseTime: 'Active Cancellation',
        status: 'Cancelled',
      };
      db.history.unshift(historyItem);
      db.activeIncident = null;
      return true;
    }
    return false;
  }

  getHistory(): DispatchHistoryItem[] {
    return db.history;
  }
}

export const incidentService = new IncidentService();
