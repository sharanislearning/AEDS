import { db } from '../data/initialDatabase';
import { Hospital } from '../types/backendTypes';

export class HospitalService {
  getAllHospitals(): Hospital[] {
    return db.hospitals;
  }

  getHospitalById(id: string): Hospital | undefined {
    return db.hospitals.find((h) => h.id === id);
  }

  updateHospitalStatus(
    id: string,
    updates: Partial<Hospital>
  ): Hospital | null {
    const index = db.hospitals.findIndex((h) => h.id === id);
    if (index === -1) return null;
    db.hospitals[index] = { ...db.hospitals[index], ...updates };
    return db.hospitals[index];
  }
}

export const hospitalService = new HospitalService();
