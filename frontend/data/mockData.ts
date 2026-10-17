import { Hospital, AmbulanceUnit, Incident, DispatchHistoryItem, UserProfile, TurnInstruction } from '../types';

export const AERS_LOGO_URL = 'https://lh3.googleusercontent.com/aida-public/AB6AXuByCnR2qZlpaTJMjsAYnkF-MOpU1ZILnDuRwcntV5l2AQzcpfqRt87vuMuCvnFstO-mXtlm77ph71ew-xqsG7kHQ3OMQGNOKBhDpYqQ6dUMcx7r2PTvoQwMKYZyqhyCqa0EPOoehR4x3Rq53unhyerL7IJSsPw-1TdZbVy04CrcEq1Q6QL20S5MlyHuQbIbVVR0yyNUrrs4yi_uAdBa2Tx9krBMa6vdDWX5QEZwdk5RkdQaz10D1N7P';

export const USER_AVATAR_URL = 'https://lh3.googleusercontent.com/aida-public/AB6AXuC87QX_4HlJvIn_eWsAGZBSEdq6oOIc0_9YmmLm5MZ-Qt9A3ZPixUC7LbNnijq9_SUfjHRY51VSebXWeBCgk9QNE0zWGarHzOg2uFHj_8O-v3gttUMLTeI8a_JufGKMdrz0IbQ-2Kn9Yhkdd3rhWJvJPrJaSloeMie03xXeVJPh4Bz_xXo0yArU6IZ4wzObYOs0w9GAOuoFCvw5XmSBLE77VGNPsdYtsymD3XB-3IQkk1OLAV2_TTlH';

export const PARAMEDIC_DAVID_AVATAR = 'https://lh3.googleusercontent.com/aida-public/AB6AXuBPVfLpg3GfoARJBPAYRjpfFfMEx1MHxvZbDTG7mtJJcFaEdUbMXij01878bfCRfkFebTd1ycpD65TR57VQYfvlk3ItlRyTrw_4hdrGBN2Okpp04y6g7mq-zR5avZIQUYalRgmix1Tmzj95XWpT_EDPtKtgCOX6_d5NZZtS5Aqj0RcQXFYkNjYpWmq6T_L9gwyxwxBI31bDduv5uTjNIj3ENBMIfU-ZdOK7KsJesCMvJyXuje6Sx342';

export const HOSPITAL_MAP_IMAGE = 'https://lh3.googleusercontent.com/aida-public/AB6AXuBj3tRtNXC6XhJcMDFEZ2Jd5v8tpvLwqCQilmkBJsDPSeDyCnolM8QB3pWk2sfM9f6xph0tVsJBtBCP57Xc7tS7VcdvxoviL4ovPl_Sl_Ww0ENoskStLWSRXrWd-rXcuaP_uH2CsWKVRhgMz1pUMFQSIBpsFNvGoGDwtvA608CLyHrGua0lHXG9Qzk28M1YdRFHGytEoS96I6KeBoB_uZpBBJfNBHMuHPSoaaSkYutqfWoWA0SD3Ao7';

export const COMMAND_MAP_IMAGE = 'https://lh3.googleusercontent.com/aida-public/AB6AXuBth7YV_0pyzsjINo5GvYuwm9Odk-LnT8lSonBdr24KBty38fJyAjDr1SYWwSauMYGuPZw7zZdcvPoB0VIY0EEWC5b0FTLcxRuGFK4kFm9QnUkVPhO8EAsP3rGmzojkxGXx8X1yF5wVw9QUR0eulUjqTflRtADkem05IyMPE9dAU2RW273a8rndV0Xo5sOHTW6h87RBP6bsNEH-pw2hqv_nRTXWzK90FQ7hPKfpsNM7Otzpjvg0kF9V';

export const INITIAL_HOSPITALS: Hospital[] = [
  {
    id: 'bhaktivedanta',
    name: 'Bhaktivedanta Hospital & Research Institute',
    distance: '1.1 km',
    distanceMiles: 0.7,
    etaMins: 4,
    erStatus: 'open',
    icuStatus: 'available',
    trafficStatus: 'low',
    trafficText: 'Low Traffic',
    isFastest: true,
    address: 'Srishti Complex, Bhaktivedanta Swami Marg, Sector 1, Mira Road East',
    city: 'Mira Road, Thane',
    traumaLevel: 'Level I',
    phone: '+91 22 2945 2500',
    waitTimeMins: 3,
    availableBeds: 28,
    icuBedsAvailable: 8,
    specialties: ['24/7 Cardiac Emergency', 'Critical Care ICU', 'Accident & Trauma', 'Cardiac Ambulance'],
    mapImage: HOSPITAL_MAP_IMAGE,
    coordinates: { x: 38, y: 30, lat: 19.2882, lng: 72.8687 }
  },
  {
    id: 'wockhardt-mira',
    name: 'Wockhardt Super Speciality Hospital',
    distance: '2.4 km',
    distanceMiles: 1.5,
    etaMins: 7,
    erStatus: 'open',
    icuStatus: 'available',
    trafficStatus: 'moderate',
    trafficText: 'Moderate Traffic',
    isFastest: false,
    address: 'The Umrao IMSR, Near Railway Station, Mira Road East',
    city: 'Mira Road, Thane',
    traumaLevel: 'Level I',
    phone: '+91 81081 81081',
    waitTimeMins: 6,
    availableBeds: 34,
    icuBedsAvailable: 11,
    specialties: ['Comprehensive Stroke Center', 'Neuro Trauma', 'Cardiology', 'Emergency Care'],
    mapImage: HOSPITAL_MAP_IMAGE,
    coordinates: { x: 62, y: 25, lat: 19.2825, lng: 72.8596 }
  },
  {
    id: 'ashish-multi',
    name: 'Ashish Multispeciality Hospital',
    distance: '1.8 km',
    distanceMiles: 1.1,
    etaMins: 6,
    erStatus: 'open',
    icuStatus: 'available',
    trafficStatus: 'low',
    trafficText: 'Clear Corridor',
    isFastest: false,
    address: 'Shanti Nagar, Sector 4, Mira Road East',
    city: 'Mira Road, Thane',
    traumaLevel: 'Level II',
    phone: '+91 22 2811 5599',
    waitTimeMins: 5,
    availableBeds: 16,
    icuBedsAvailable: 4,
    specialties: ['24x7 Critical Care', 'Trauma Resuscitation', 'General Surgery', 'Pulmonology'],
    mapImage: HOSPITAL_MAP_IMAGE,
    coordinates: { x: 26, y: 52, lat: 19.2847, lng: 72.8641 }
  },
  {
    id: 'thunga-hospital',
    name: 'Thunga Multispeciality Hospital',
    distance: '3.2 km',
    distanceMiles: 2.0,
    etaMins: 9,
    erStatus: 'open',
    icuStatus: 'limited',
    trafficStatus: 'moderate',
    trafficText: 'Moderate Traffic',
    isFastest: false,
    address: 'Beverly Park, Kanakia Road, Mira Road East',
    city: 'Mira Road, Thane',
    traumaLevel: 'Level II',
    phone: '+91 22 2810 1200',
    waitTimeMins: 11,
    availableBeds: 19,
    icuBedsAvailable: 2,
    specialties: ['Accident Emergency', 'Orthopedic Surgery', 'Cardiology', 'ICU Care'],
    mapImage: HOSPITAL_MAP_IMAGE,
    coordinates: { x: 74, y: 64, lat: 19.2778, lng: 72.8725 }
  },
  {
    id: 'shree-siddhivinayak',
    name: 'Shree Siddhivinayak Multispeciality',
    distance: '2.1 km',
    distanceMiles: 1.3,
    etaMins: 8,
    erStatus: 'open',
    icuStatus: 'available',
    trafficStatus: 'low',
    trafficText: 'Clear Route',
    isFastest: false,
    address: 'Near Shanti Park, Off Western Express Highway, Mira Road East',
    city: 'Mira Road, Thane',
    traumaLevel: 'Level II',
    phone: '+91 22 2812 3636',
    waitTimeMins: 7,
    availableBeds: 14,
    icuBedsAvailable: 3,
    specialties: ['24-Hour Emergency', 'Trauma Care', 'Intensive Care Unit', 'Pediatrics'],
    mapImage: HOSPITAL_MAP_IMAGE,
    coordinates: { x: 48, y: 72, lat: 19.281, lng: 72.871 }
  },
  {
    id: 'mira-superspeciality',
    name: 'MIRA Super Speciality Hospital',
    distance: '4.5 km',
    distanceMiles: 2.8,
    etaMins: 13,
    erStatus: 'open',
    icuStatus: 'available',
    trafficStatus: 'moderate',
    trafficText: 'Med Traffic',
    isFastest: false,
    address: 'Flyover Junction, Mira Bhayandar Highway, Bhayandar East / Mira Road',
    city: 'Mira Bhayandar',
    traumaLevel: 'Level I',
    phone: '+91 22 2818 9000',
    waitTimeMins: 10,
    availableBeds: 24,
    icuBedsAvailable: 6,
    specialties: ['24/7 ER & Advanced Ambulance', 'Cardio-Thoracic', 'Neurosurgery', 'Dialysis'],
    mapImage: HOSPITAL_MAP_IMAGE,
    coordinates: { x: 80, y: 22, lat: 19.2965, lng: 72.8552 }
  }
];

export const PRIMARY_PARAMEDIC: AmbulanceUnit = {
  id: 'unit-42',
  unitNumber: 'ALS Unit 42',
  paramedicName: 'Rohan Deshmukh',
  paramedicRole: 'Lead Paramedic • MBMC EMS Unit 42',
  paramedicRating: 4.9,
  avatar: PARAMEDIC_DAVID_AVATAR,
  status: 'en_route',
  etaMins: 4,
  currentAddress: 'Shanti Nagar Sector 2 & Western Express Connector',
  destinationAddress: 'Srishti Complex, Mira Road East',
  assignedHospitalId: 'bhaktivedanta',
  phone: '+91 98200 42911',
  coordinates: { x: 32, y: 46 }
};

export const INITIAL_USER_PROFILE: UserProfile = {
  name: 'Sharan Moolya',
  avatar: USER_AVATAR_URL,
  email: 'sharankumar.v.moolya24@slrtce.in',
  phone: '+91 98334 12044',
  bloodType: 'O-Negative (Universal Donor)',
  allergies: ['Penicillin', 'Sulfa Antibiotics', 'Latex'],
  medications: ['Albuterol Inhaler (PRN)', 'Daily Multivitamin'],
  medicalConditions: ['Mild Asthma (Exercise-induced)'],
  emergencyContacts: [
    {
      id: 'contact-1',
      name: 'Venkatesh Moolya',
      relation: 'Family',
      phone: '+91 98201 55432',
      isPrimary: true
    },
    {
      id: 'contact-2',
      name: 'Dr. A. K. Sharma, MD',
      relation: 'Primary Physician',
      phone: '+91 22 2811 7700',
      isPrimary: false
    }
  ],
  insuranceProvider: 'Star Health Emergency Comprehensive',
  policyNumber: 'SH-MRD-8892104-X',
  organDonor: true,
  homeAddress: 'Srishti Complex, Sector 3, Mira Road East, Thane 401107',
  workAddress: 'SLRTCE Campus, Mira Road East, Thane 401107'
};

export const INITIAL_HISTORY: DispatchHistoryItem[] = [
  {
    id: 'disp-901',
    date: 'Sep 20, 2026',
    time: '14:22 IST',
    title: 'Rapid SOS Emergency Dispatch',
    type: 'emergency_sos',
    location: 'Srishti Complex, Mira Road East',
    destinationHospital: 'Bhaktivedanta Hospital & Research Institute',
    unitAssigned: 'ALS Unit 42 (Rohan D.)',
    responseTime: '3.6 min',
    status: 'Completed'
  },
  {
    id: 'disp-844',
    date: 'Aug 29, 2026',
    time: '09:15 IST',
    title: 'Urgent Medical Facility Route',
    type: 'hospital_transfer',
    location: 'Shanti Park Junction, Mira Road',
    destinationHospital: 'Wockhardt Super Speciality Hospital',
    unitAssigned: 'Self-Navigated Priority Corridor',
    responseTime: '6.5 min',
    status: 'Completed'
  },
  {
    id: 'disp-712',
    date: 'Jul 11, 2026',
    time: '21:04 IST',
    title: 'Precautionary SOS Alert',
    type: 'emergency_sos',
    location: 'Kanakia Road, Beverly Park',
    destinationHospital: 'Thunga Multispeciality Hospital',
    unitAssigned: 'MBMC Unit 17 (Capt. Patil)',
    responseTime: '4.2 min',
    status: 'Resolved On-Site'
  }
];

export const TURN_BY_TURN_STEPS: TurnInstruction[] = [
  {
    id: 'step-1',
    instruction: 'Head north on Srishti Main Road toward Bhaktivedanta Swami Marg',
    distance: '350 m',
    streetName: 'Srishti Main Rd',
    action: 'straight'
  },
  {
    id: 'step-2',
    instruction: 'Turn right at Royal College Junction onto Mira Bhayandar Road',
    distance: '600 m',
    streetName: 'Mira Bhayandar Rd',
    action: 'turn-right'
  },
  {
    id: 'step-3',
    instruction: 'Keep right on Western Express Highway connector corridor',
    distance: '1.2 km',
    streetName: 'WEH Connector',
    action: 'merge'
  },
  {
    id: 'step-4',
    instruction: 'Take left ramp toward Sector 1 Trauma Bay',
    distance: '250 m',
    streetName: 'Hospital Access Bay',
    action: 'turn-left'
  },
  {
    id: 'step-5',
    instruction: 'Arrive at Bhaktivedanta Hospital Emergency Bay (Direct ICU Entry)',
    distance: '50 m',
    streetName: 'Emergency Bay Gate 2',
    action: 'arrive'
  }
];
