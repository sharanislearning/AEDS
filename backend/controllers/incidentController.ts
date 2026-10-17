import { Request, Response } from 'express';
import { incidentService } from '../services/incidentService';

export const getActiveIncident = (req: Request, res: Response) => {
  try {
    const incident = incidentService.getActiveIncident();
    res.json({
      success: true,
      data: incident,
      timestamp: new Date().toISOString(),
    });
  } catch (error: any) {
    res.status(500).json({
      success: false,
      error: error.message || 'Failed to fetch active incident',
      timestamp: new Date().toISOString(),
    });
  }
};

export const triggerSos = (req: Request, res: Response) => {
  try {
    const { address, targetHospitalId, incidentType } = req.body || {};
    const incident = incidentService.createSosIncident({
      address,
      targetHospitalId,
      incidentType,
    });
    res.status(201).json({
      success: true,
      data: incident,
      timestamp: new Date().toISOString(),
    });
  } catch (error: any) {
    res.status(500).json({
      success: false,
      error: error.message || 'Failed to create SOS incident',
      timestamp: new Date().toISOString(),
    });
  }
};

export const cancelIncident = (req: Request, res: Response) => {
  try {
    const { id } = req.params;
    const cancelled = incidentService.cancelIncident(id);
    res.json({
      success: true,
      data: { cancelled, id },
      timestamp: new Date().toISOString(),
    });
  } catch (error: any) {
    res.status(500).json({
      success: false,
      error: error.message || 'Failed to cancel incident',
      timestamp: new Date().toISOString(),
    });
  }
};

export const getHistory = (req: Request, res: Response) => {
  try {
    const history = incidentService.getHistory();
    res.json({
      success: true,
      data: history,
      timestamp: new Date().toISOString(),
    });
  } catch (error: any) {
    res.status(500).json({
      success: false,
      error: error.message || 'Failed to fetch dispatch history',
      timestamp: new Date().toISOString(),
    });
  }
};
