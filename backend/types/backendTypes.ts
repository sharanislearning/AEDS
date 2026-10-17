export type TabType = 'home' | 'hospitals' | 'history' | 'profile';

export type ActiveScreen = 
  | { type: 'tab'; tab: TabType }
  | { type: 'hospital_details'; hospitalId: string }
  | { type: 'incident_details'; incidentId: string }
  | { type: 'navigation'; hospitalId: string; origin?: string };

export interface Hospital {
  id: string;
  name: string;
  distance: string;
  distanceMiles: number;
  etaMins: number;
  erStatus: 'open' | 'busy' | 'closed';
  icuStatus: 'available' | 'full' | 'limited';
  trafficStatus: 'low' | 'moderate' | 'high';
  trafficText: string;
  isFastest?: boolean;
  address: string;
  city: string;
  traumaLevel: 'Level I' | 'Level II' | 'Level III' | 'Pediatric';
  phone: string;
  waitTimeMins: number;
  availableBeds: number;
  icuBedsAvailable: number;
  specialties: string[];
  mapImage?: string;
  coordinates: { x: number; y: number; lat: number; lng: number };
}

export interface AmbulanceUnit {
  id: string;
  unitNumber: string;
  paramedicName: string;
  paramedicRole: string;
  paramedicRating: number;
  avatar: string;
  status: 'en_route' | 'standing_by' | 'at_scene';
  etaMins: number;
  currentAddress: string;
  destinationAddress: string;
  assignedHospitalId: string;
  phone: string;
  coordinates: { x: number; y: number };
}

export interface Incident {
  id: string;
  status: 'active' | 'en_route' | 'completed' | 'cancelled';
  address: string;
  targetHospitalId: string;
  unit: AmbulanceUnit;
  progressPercent: number;
  initialEtaMins: number;
  currentEtaMins: number;
  sosActivatedAt: string;
  incidentType: string;
}

export interface DispatchHistoryItem {
  id: string;
  date: string;
  time: string;
  title: string;
  type: 'emergency_sos' | 'hospital_transfer' | 'scheduled_transport';
  location: string;
  destinationHospital: string;
  unitAssigned: string;
  responseTime: string;
  status: 'Completed' | 'Cancelled' | 'Resolved On-Site';
}

export interface EmergencyContact {
  id: string;
  name: string;
  relation: string;
  phone: string;
  isPrimary: boolean;
}

export interface UserProfile {
  name: string;
  avatar: string;
  email: string;
  phone: string;
  bloodType: string;
  allergies: string[];
  medications: string[];
  medicalConditions: string[];
  emergencyContacts: EmergencyContact[];
  insuranceProvider: string;
  policyNumber: string;
  organDonor: boolean;
  homeAddress: string;
  workAddress: string;
}

export interface ApiResponse<T> {
  success: boolean;
  data?: T;
  error?: string;
  timestamp: string;
}
