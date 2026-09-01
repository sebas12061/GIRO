# Arquitectura propuesta de GIRO

## 1. Visión general

GIRO se diseña como una plataforma modular para la gestión integral de restaurantes. La arquitectura centraliza la lógica de negocio en un backend robusto, mientras el frontend ofrece una experiencia adaptable a distintos tamaños de pantalla.

## 2. Principios de diseño

- Modularidad por dominio funcional
- Responsividad multi-dispositivo
- Seguridad por roles y permisos
- Trazabilidad por auditoría
- Extensibilidad para distintos tipos de restaurante
- Separación clara entre presentación, negocio y persistencia

## 3. Stack tecnológico

### Frontend

- Angular
- Angular Material o componentes propios
- Diseño responsive y mobile-first
- Soporte para tablet y celular

### Backend

- Java 21
- Spring Boot 3.x
- Spring Security
- Spring Data JPA
- Validation
- OpenAPI / Swagger

### Base de datos

- PostgreSQL

### Infraestructura

- Docker
- Docker Compose
- Posible despliegue en nube más adelante

## 4. Capa de presentación

La capa frontend estará orientada a varios perfiles de usuario:

- administrador
- caja
- salón
- cocina
- compras
- personal operativo

Cada perfil tendrá una vista adaptada a su flujo, con componentes propios y accesos según permisos.

### Requisitos de UI

- Escritorio: dashboards, reportes, administración
- Tablet: flujo rápido de mesas, pedidos y cobro
- Móvil: consultas, validaciones rápidas, atención operativa

## 5. Capa de negocio

El backend manejará la lógica crítica del negocio:

- autenticación y autorización
- gestión de usuarios y roles
- configuración del restaurante
- pedidos, pagos, fiados y caja
- compras, productos y inventario
- turnos, gastos y cierres
- reportes y auditoría

## 6. Capa de persistencia

PostgreSQL almacenará:

- restaurantes
- usuarios
- empleados
- mesas
- categorías
- productos
- menús
- pedidos
- ventas
- pagos
- fiados
- caja
- movimientos
- compras
- cierres operativos
- auditoría

## 7. Módulos funcionales

### Seguridad

- login
- roles
- permisos
- usuarios activos/inactivos
- control de acceso por módulo

### Configuración

- restaurante
- mesas
- categorías
- unidades operativas
- parámetros generales

### Catálogo

- productos
- menús
- opciones de menú
- precios
- estado activo/inactivo

### Pedidos

- crear pedido
- asociar a mesa o domicilio
- editar pedido abierto
- cancelar pedido
- consultar estado

### Cocina

- visualizar pedidos pendientes
- actualizar estados de preparación
- marcar como listo/servido

### Ventas y pagos

- registrar venta al cobro
- pagos en efectivo
- transferencias
- múltiples pagos
- fiados

### Caja

- apertura
- base inicial
- gastos
- movimientos
- cuadratura
- cierre
- diferencias

### Compras e inventario

- proveedores
- compras
- movimientos por producto
- cierre operativo
- existencias finales

### Personal

- empleados
- turnos
- valores asignados

### Reportes y auditoría

- ventas por período
- compras
- gastos
- transferencias
- fiados
- historial de cambios

## 8. Flujo principal de negocio

Pedido → cocina → venta → cobro → caja → cierre operativo → reportes

## 9. Recomendación de diseño UX

- Mobile-first para pantallas operativas
- Desktop para administración y reportes
- Tablet para punto de venta y servicio al cliente
- Componentes grandes, claros y con baja fricción de uso

## 10. Roadmap de implementación

### Fase 1: base técnica

- scaffolding del proyecto
- autenticación
- configuración inicial
- modelo de base de datos

### Fase 2: operación diaria

- mesas, pedidos, cocina, ventas

### Fase 3: finanzas y inventario

- caja, compras, productos, fiados

### Fase 4: reportes y auditoría

- dashboards, historial, auditoría

### Fase 5: responsive y despliegue

- adaptaciones para tablet/móvil
- Docker
- despliegue inicial

## 11. Decisión de arquitectura final

La arquitectura recomendada es:

- Angular para la capa frontend
- Spring Boot para la capa backend
- PostgreSQL para la persistencia
- Docker para ejecutar el sistema de forma reproducible

Esto responde a la necesidad funcional y operativa de GIRO, sin perder la modularidad ni el enfoque multi-dispositivo.
