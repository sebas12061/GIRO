# Informe de implementación de GIRO

Fecha: 2026-10-12

## 1. Estado general

GIRO cuenta actualmente con una base backend Spring Boot modular y compilable. La arquitectura sigue el SRS y organiza el código por dominio, no por carpetas técnicas globales.

Stack actual:

- Java 21
- Spring Boot 3.5.6
- Spring Web
- Spring Data JPA / Hibernate
- Spring Security
- JWT con JJWT
- BCrypt
- MapStruct
- Spring AOP y eventos de aplicación
- PostgreSQL
- H2 para pruebas
- Maven
- Docker Compose preparado
- Swagger/OpenAPI con esquema Bearer JWT

## 2. Implementado

### Plataforma y arquitectura

- Monolito modular preparado para Docker.
- Configuración externalizada por variables de entorno.
- Persistencia JPA con PostgreSQL.
- DTOs separados de las entidades JPA en los módulos implementados.
- MapStruct configurado como procesador de mapeo.
- Soporte responsive queda reservado para el frontend Angular, todavía no implementado.

### Seguridad

- Modelo `Usuario`, `Rol` y `Permiso`.
- Contraseñas mediante BCrypt.
- Autenticación JWT stateless.
- Filtro Bearer JWT.
- `AuthenticationManager`.
- Autorización por métodos preparada con `@EnableMethodSecurity`.
- El token incluye usuario y restaurante.
- Bootstrap opcional para crear restaurante, administrador, rol y permiso iniciales.

### Pedidos

- Crear pedidos.
- Listar pedidos por restaurante.
- Cambiar estados del pedido.
- State Machine con transiciones válidas.
- Bloqueo de modificación normal de pedidos cerrados o cancelados.
- Estados definidos en el SRS.

### Pagos

- Persistencia de pagos.
- Múltiples pagos asociados a una venta.
- Efectivo, transferencia y fiado.
- Strategy por tipo de pago.
- Validación de referencia para transferencia y fiado.
- Transferencias pendientes de verificación.
- Endpoint para verificar transferencias.

### Caja

- Apertura de caja con base inicial.
- Restricción de una caja abierta por restaurante.
- Consulta de caja actual.
- Cálculo de efectivo esperado mediante Strategy.
- Estados abierta/cerrada modelados.

### Productos

- Producto con restaurante, categoría, unidad operativa, precio y estado.
- Unidades operativas configurables a nivel de entidad.
- Categorías por restaurante.
- Consulta de productos activos filtrada por restaurante.
- Soft delete lógico mediante estado activo/inactivo en entidades aplicables.

### Auditoría

- Entidad de auditoría.
- Repository de auditoría.
- Eventos de aplicación.
- AOP para publicar auditoría en operaciones críticas anotadas.
- Auditoría desacoplada del módulo que origina la operación.

## 3. Qué permite hacer actualmente

La API permite, con una base de datos configurada y usuarios cargados:

- Consultar salud del backend.
- Autenticarse y obtener un JWT.
- Crear y consultar pedidos por restaurante.
- Avanzar pedidos respetando la máquina de estados.
- Registrar pagos de efectivo, transferencia y fiado.
- Consultar pagos de una venta.
- Verificar transferencias.
- Abrir una caja.
- Consultar la caja abierta.
- Calcular efectivo esperado.
- Consultar productos activos de un restaurante.
- Registrar eventos de auditoría de operaciones anotadas.

Endpoints principales:

- `GET /api/v1/public/health`
- `POST /api/v1/auth/login`
- `POST /api/v1/pedidos`
- `GET /api/v1/pedidos?restauranteId={id}`
- `PUT /api/v1/pedidos/{id}/estado?restauranteId={id}&estado={estado}`
- `POST /api/v1/pagos`
- `GET /api/v1/pagos?restauranteId={id}&ventaId={id}`
- `PUT /api/v1/pagos/{id}/verificar?restauranteId={id}`
- `POST /api/v1/cajas`
- `GET /api/v1/cajas/actual?restauranteId={id}`
- `GET /api/v1/cajas/efectivo-esperado`
- `GET /api/v1/productos?restauranteId={id}`
- Swagger UI: `http://localhost:8080/swagger-ui/index.html`

## 4. Pendiente de implementación

### Seguridad y configuración

- Endpoint administrativo para crear usuarios.
- Gestión REST de roles y permisos.
- Asignación de roles desde la interfaz.
- Restricción automática de cada operación según permiso específico.
- Registro inicial seguro de restaurante, roles y administrador.
- Rotación y gestión avanzada de secretos JWT.

### Operación

- Mesas y estados de mesa.
- Detalles de pedido, productos y adicionales.
- Menús y opciones de menú.
- Domicilios y clientes.
- Vista de cocina y actualización operativa de preparación.
- Venta vinculada al pedido durante el cobro.
- Cierre completo del pedido después del pago.

### Caja y finanzas

- Movimientos de caja.
- Gastos.
- Cuadre con efectivo real, diferencia, sobrante y faltante.
- Cierre de caja.
- Modificación administrativa de cuadre cerrado con auditoría.

### Inventario y compras

- Movimientos de producto: AMANECE, LLEGA, consumos, REST y G.
- Cierre operativo y QUED.
- Proveedores.
- Compras y detalles de compra.
- Actualización de inventario derivada de compras y consumos.

### Personal y reportes

- Empleados.
- Turnos y valores.
- Reportes de ventas, gastos, compras, pagos, fiados y caja.
- Históricos mensuales.
- Indicadores configurables.

### Frontend

- Aplicación Angular.
- Lazy loading por feature.
- Componentes smart/dumb.
- Interfaces responsive para computador, tablet y móvil.
- Vistas de administración, salón, cocina, caja y compras.
- Integración con la API y manejo de sesión JWT.

### Infraestructura y calidad

- Docker Desktop debe estar activo para probar PostgreSQL localmente.
- Migraciones versionadas con Flyway o Liquibase.
- OpenAPI/Swagger publicado y mantenido.
- Pruebas de integración con PostgreSQL real.
- Pruebas de autorización por rol y restaurante.
- CI/CD y despliegue.

## 5. Validación realizada

La ejecución de `mvn test` ha pasado en las iteraciones actuales, incluyendo:

- Carga del contexto Spring.
- Pruebas de máquina de estados.
- Pruebas de estrategias de pago.
- Pruebas de publicación de auditoría.
- Pruebas de cálculo de efectivo esperado.
- Prueba de verificación de transferencia.

El backend todavía no se ha validado contra PostgreSQL real porque Docker Desktop no estaba ejecutando el motor Linux en el entorno de desarrollo.

## 6. Conclusión

La base implementada ya refleja la arquitectura profesional definida para GIRO y permite probar los flujos iniciales de pedidos, pagos, caja, productos, autenticación y auditoría. No debe considerarse todavía una versión completa del sistema del SRS: faltan los módulos de operación avanzada, inventario, compras, personal, reportes y todo el frontend Angular.
