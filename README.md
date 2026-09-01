# GIRO

GIRO es un sistema integral para la gestión y automatización de operaciones en restaurantes, pensado para funcionar en computadores, tablets y dispositivos móviles.

## Objetivo

Digitalizar y centralizar los procesos operativos, administrativos y financieros de un restaurante, cubriendo desde pedidos y cocina hasta caja, compras, inventario, reportes y auditoría.

## Stack propuesto

- Frontend: Angular
- Backend: Spring Boot
- Base de datos: PostgreSQL
- API: REST con OpenAPI
- Contenedores: Docker
- Seguridad: JWT + roles y permisos
- Despliegue: Docker Compose / entorno cloud

## Enfoque multi-dispositivo

GIRO debe ser usable en:

- Escritorio
- Laptop
- Tablet
- Smartphone

La interfaz será pensada con diseño responsive, priorizando flujos rápidos para caja, cocina, salón y administración.

## Módulos principales

- Seguridad y usuarios
- Configuración del restaurante
- Menú y productos
- Mesas y pedidos
- Cocina
- Ventas y pagos
- Fiados
- Caja y cuadre
- Compras y proveedores
- Inventario y cierre operativo
- Personal y turnos
- Reportes
- Auditoría

## Estructura del repositorio

- `backend/`: API y lógica de negocio
- `frontend/`: aplicación web responsive
- `docs/`: arquitectura, roadmap y documentación del proyecto
- `docker/`: configuración de entornos y despliegue

## Documento base

La documentación de requisitos fuente está en:

- [SRS_GIRO_v1.0_Consolidado.txt](SRS_GIRO_v1.0_Consolidado.txt)

## Siguiente paso

Se iniciará con la base técnica del proyecto, empezando por:

1. Arquitectura general
2. Estructura de módulos
3. Modelo de datos inicial
4. Primer MVP funcional
5. Preparación para responsive/mobile-first

---

Este proyecto se construye de manera modular, escalable y centrada en la operación real del restaurante.
