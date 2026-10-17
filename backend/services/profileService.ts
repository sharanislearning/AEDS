import { db } from '../data/initialDatabase';
import { UserProfile } from '../types/backendTypes';

export class ProfileService {
  getProfile(): UserProfile {
    return db.profile;
  }

  updateProfile(updates: Partial<UserProfile>): UserProfile {
    db.profile = { ...db.profile, ...updates };
    return db.profile;
  }
}

export const profileService = new ProfileService();
