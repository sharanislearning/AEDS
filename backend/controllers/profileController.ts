import { Request, Response } from 'express';
import { profileService } from '../services/profileService';

export const getProfile = (req: Request, res: Response) => {
  try {
    const profile = profileService.getProfile();
    res.json({
      success: true,
      data: profile,
      timestamp: new Date().toISOString(),
    });
  } catch (error: any) {
    res.status(500).json({
      success: false,
      error: error.message || 'Failed to fetch user profile',
      timestamp: new Date().toISOString(),
    });
  }
};

export const updateProfile = (req: Request, res: Response) => {
  try {
    const updates = req.body;
    const updated = profileService.updateProfile(updates);
    res.json({
      success: true,
      data: updated,
      timestamp: new Date().toISOString(),
    });
  } catch (error: any) {
    res.status(500).json({
      success: false,
      error: error.message || 'Failed to update user profile',
      timestamp: new Date().toISOString(),
    });
  }
};
