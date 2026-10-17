import express, { Express, Request, Response, NextFunction } from 'express';
import hospitalRoutes from './routes/hospitalRoutes';
import incidentRoutes from './routes/incidentRoutes';
import profileRoutes from './routes/profileRoutes';

export function createBackendApp(): Express {
  const app = express();

  // Middleware
  app.use(express.json());
  app.use(express.urlencoded({ extended: true }));

  // Health check endpoint
  app.get('/api/health', (req: Request, res: Response) => {
    res.json({
      status: 'ok',
      service: 'AERS Pro Tactical Emergency Backend',
      timestamp: new Date().toISOString(),
    });
  });

  // API Routes
  app.use('/api/hospitals', hospitalRoutes);
  app.use('/api/incidents', incidentRoutes);
  app.use('/api/profile', profileRoutes);

  // Global API 404 handler for unmatched /api routes
  app.all('/api/*', (req: Request, res: Response) => {
    res.status(404).json({
      success: false,
      error: `API Route not found: ${req.method} ${req.url}`,
      timestamp: new Date().toISOString(),
    });
  });

  // Error handling middleware
  app.use((err: any, req: Request, res: Response, next: NextFunction) => {
    console.error('[Backend Error]:', err);
    res.status(500).json({
      success: false,
      error: err.message || 'Internal Server Error',
      timestamp: new Date().toISOString(),
    });
  });

  return app;
}

export default createBackendApp;
