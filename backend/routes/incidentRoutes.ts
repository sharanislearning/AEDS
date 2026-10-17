import { Router } from 'express';
import {
  getActiveIncident,
  triggerSos,
  cancelIncident,
  getHistory,
} from '../controllers/incidentController';

const router = Router();

router.get('/active', getActiveIncident);
router.post('/sos', triggerSos);
router.post('/:id/cancel', cancelIncident);
router.get('/history', getHistory);

export default router;
