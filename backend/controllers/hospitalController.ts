import { Request, Response } from 'express';
import { hospitalService } from '../services/hospitalService';

export const getHospitals = (req: Request, res: Response) => {
  try {
    const hospitals = hospitalService.getAllHospitals();
    res.json({
      success: true,
      data: hospitals,
      timestamp: new Date().toISOString(),
    });
  } catch (error: any) {
    res.status(500).json({
      success: false,
      error: error.message || 'Failed to fetch hospitals',
      timestamp: new Date().toISOString(),
    });
  }
};

export const getHospitalById = (req: Request, res: Response) => {
  try {
    const { id } = req.params;
    const hospital = hospitalService.getHospitalById(id);
    if (!hospital) {
      return res.status(404).json({
        success: false,
        error: 'Hospital not found',
        timestamp: new Date().toISOString(),
      });
    }
    res.json({
      success: true,
      data: hospital,
      timestamp: new Date().toISOString(),
    });
  } catch (error: any) {
    res.status(500).json({
      success: false,
      error: error.message || 'Failed to fetch hospital',
      timestamp: new Date().toISOString(),
    });
  }
};
