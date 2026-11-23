# Frontend de GIRO

Aplicación Angular standalone responsive para el sistema GIRO.

## Estado actual

- Configuración Angular standalone
- Login JWT contra el backend
- Guard de autenticación
- Interceptor Bearer
- Layout responsive con navegación lateral
- Dashboard inicial
- Rutas de autenticación y operación

## Módulos siguientes

- Pedidos
- Cocina
- Caja
- Compras
- Inventario
- Reportes
- Configuración y seguridad

## Instalación

Desde esta carpeta:

```powershell
npm install
npm start
```

La aplicación quedará disponible en `http://localhost:4200`.

El backend debe estar disponible en `http://localhost:8080` y el origen Angular debe estar permitido por `GIRO_CORS_ALLOWED_ORIGINS`.
