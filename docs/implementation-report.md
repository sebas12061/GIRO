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

### Pedidos y mesas

- Crear pedidos con mesa, domicilio o recogida.
- Asociar pedidos a mesas por restaurante.
- Impedir que una mesa tenga más de un pedido activo.
- Crear detalles de pedido con producto, cantidad, precio y observaciones.
- Reemplazar detalles de pedidos abiertos.
- Liberar una mesa al cancelar o cerrar el pedido.
- Listar pedidos por restaurante.
- Cambiar estados del pedido mediante State Machine.
- Bloqueo de modificación normal de pedidos cerrados o cancelados.
- Estados definidos en el SRS.

### Cocina

- Cola de pedidos creados, confirmados y en preparación.
- Orden por hora de llegada sin bloquear preparación fuera de orden.
- Confirmación de pedidos creados.
- Inicio de preparación.
- Marcado de pedido como preparado.
- Todas las acciones utilizan la State Machine central de Pedidos.

### Ventas y cobros

- Venta creada al cobrar un pedido pendiente de pago.
- Una venta única asociada a cada pedido.
- Cobro mediante pagos múltiples.
- Venta pagada solo cuando los pagos confirmados cubren el total.
- Transferencias pendientes no cierran el cobro hasta verificarse.
- Pedido cambiado a `PAGADO` desde el servicio de Pedidos.

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
- Registro de gastos y movimientos por caja.
- Cálculo de efectivo esperado mediante Strategy.
- Recuperación de pagos en efectivo, transferencias y fiados.
- Cuadre persistente con efectivo real y diferencia.
- Cierre de caja y cuadre.
- Modificación administrativa de cuadres cerrados.
- Auditoría específica de modificaciones de cuadre.
- Estados abierta/cerrada modelados.

### Compras y proveedores

- CRUD operativo básico de proveedores con estado activo/inactivo.
- Registro de compras por restaurante y proveedor.
- Detalles de compra con producto, cantidad y valor unitario.
- Cálculo automático del total de compra.
- Validación de proveedor activo.
- Validación de productos activos del mismo restaurante.
- Registro opcional contra una caja abierta.
- Movimiento GASTO automático en caja cuando se indica la caja.
- Anulación controlada de compras.
- Reversión de inventario mediante ajustes negativos referenciados a la compra anulada.
- Reversión financiera opcional mediante movimiento INGRESO referenciado a la compra anulada.

### Inventario operativo

- Movimientos por AMANECE, LLEGA, FACT_DESA, FACT_ALMU, REST, G y AJUSTE.
- Existencia calculada por fecha y unidad operativa.
- Validación de unidad correspondiente al producto.
- Cierre operativo diario por restaurante.
- Detalles de cierre con cantidad `QUED`.
- Bloqueo de modificaciones después de cerrar el inventario operativo.
- Bloqueo de ventas que dejarían stock negativo.
- Consumo G generado una sola vez al completar el cobro.

### Clientes y fiados

- Registro de clientes generales, empleados y fiados.
- Desactivación lógica de clientes.
- Creación de cuentas fiado únicas o generales.
- Cargos asociados a ventas.
- Abonos sobre el saldo pendiente.
- Pago automático de fiado al alcanzar saldo cero.
- Bloqueo de abonos superiores al saldo.
- Historial de movimientos de fiado.

### Personal y turnos

- Registro de empleados por restaurante.
- Estados activo/inactivo para empleados.
- Creación de turnos con fecha, tipo, horario y valor.
- Validación de horarios coherentes.
- Prevención de solapes del empleado en una misma fecha.
- Estados programado, iniciado, finalizado y cancelado.
- Consulta de turnos por empleado y fecha.

### Menús y pedidos completos

- Menús con vigencia por fechas.
- Opciones vinculadas a producto, categoría o entradas configurables.
- Precios de opción validados desde el servidor.
- Pedidos que derivan precio desde producto/opción, no desde el cliente.
- Validación de producto activo del mismo restaurante.
- Cobro completo que cambia pedido a PAGADO y luego CERRADO.
- Compra registrada que genera movimiento LLEGA.
- Venta pagada que genera consumos G por detalle.

### Productos

- Producto con restaurante, categoría, unidad operativa, precio y estado.
- Unidades operativas configurables a nivel de entidad.
- Categorías por restaurante.
- Consulta de productos activos filtrada por restaurante.
- Soft delete lógico mediante estado activo/inactivo en entidades aplicables.

### Administración de seguridad

- Creación de usuarios con BCrypt.
- Activación y desactivación de usuarios.
- Cambio de contraseña.
- Vinculación opcional con empleado.
- Asignación de roles.
- Creación y consulta de roles.
- Creación y consulta de permisos.
- Asignación de permisos a roles.
- Restricción administrativa por rol `ADMINISTRADOR`.
- Auditoría desacoplada de cambios críticos de seguridad.

### Reportes operativos

- Reporte por rango de fechas y restaurante.
- Resumen de ventas pagadas y total vendido.
- Desglose de pagos por efectivo, transferencia y fiado.
- Resumen de compras registradas y gasto total.
- Resumen de fiados abiertos, pagados, vencidos y saldo pendiente.
- Cuadres cerrados y suma de diferencias de caja.
- Turnos finalizados y valor total pagado.

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
- Crear mesas por restaurante y consultarlas.
- Crear pedidos de mesa, domicilio o recogida con líneas de producto.
- Editar líneas de pedidos que siguen abiertos.
- Avanzar pedidos respetando la máquina de estados.
- Generar una venta al cobrar un pedido pendiente de pago.
- Registrar pagos múltiples de efectivo, transferencia y fiado.
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
- `POST /api/v1/mesas`
- `GET /api/v1/mesas?restauranteId={id}`
- `POST /api/v1/pedidos`
- `GET /api/v1/pedidos?restauranteId={id}`
- `PUT /api/v1/pedidos/{id}/estado?restauranteId={id}&estado={estado}`
- `PUT /api/v1/pedidos/{id}/detalles?restauranteId={id}`
- `POST /api/v1/pagos`
- `GET /api/v1/pagos?restauranteId={id}&ventaId={id}`
- `PUT /api/v1/pagos/{id}/verificar?restauranteId={id}`
- `POST /api/v1/ventas/cobrar`
- `POST /api/v1/cajas`
- `GET /api/v1/cajas/actual?restauranteId={id}`
- `POST /api/v1/cajas/movimientos`
- `GET /api/v1/cajas/movimientos?restauranteId={id}&cajaId={id}`
- `POST /api/v1/cajas/cerrar`
- `PUT /api/v1/cajas/cuadres/{cuadreId}/efectivo-real`
- `POST /api/v1/menus`
- `GET /api/v1/menus?restauranteId={id}`
- `PUT /api/v1/menus/{menuId}/desactivar`
- `POST /api/v1/proveedores`
- `GET /api/v1/proveedores?restauranteId={id}`
- `POST /api/v1/compras`
- `GET /api/v1/compras?restauranteId={id}`
- `PUT /api/v1/compras/{compraId}/anular`
- `POST /api/v1/inventario/movimientos`
- `GET /api/v1/inventario/movimientos`
- `GET /api/v1/inventario/existencia`
- `POST /api/v1/inventario/cierres`
- `POST /api/v1/clientes`
- `GET /api/v1/clientes?restauranteId={id}`
- `PUT /api/v1/clientes/{clienteId}/desactivar`
- `POST /api/v1/fiados`
- `POST /api/v1/fiados/movimientos`
- `GET /api/v1/fiados?restauranteId={id}`
- `GET /api/v1/fiados/{fiadoId}?restauranteId={id}`
- `POST /api/v1/empleados`
- `GET /api/v1/empleados?restauranteId={id}`
- `PUT /api/v1/empleados/{empleadoId}/desactivar`
- `POST /api/v1/turnos`
- `GET /api/v1/turnos?empleadoId={id}&fecha={yyyy-MM-dd}`
- `PUT /api/v1/turnos/{turnoId}/iniciar`
- `PUT /api/v1/turnos/{turnoId}/finalizar`
- `PUT /api/v1/turnos/{turnoId}/cancelar`
- `POST /api/v1/usuarios`
- `GET /api/v1/usuarios?restauranteId={id}`
- `PUT /api/v1/usuarios/{usuarioId}/activar`
- `PUT /api/v1/usuarios/{usuarioId}/desactivar`
- `PUT /api/v1/usuarios/{usuarioId}/password`
- `PUT /api/v1/usuarios/{usuarioId}/roles`
- `POST /api/v1/administracion/roles`
- `GET /api/v1/administracion/roles`
- `PUT /api/v1/administracion/roles/{rolId}/permisos`
- `POST /api/v1/administracion/permisos`
- `GET /api/v1/administracion/permisos`
- `GET /api/v1/cajas/efectivo-esperado`
- `GET /api/v1/reportes/operativo?restauranteId={id}&desde={yyyy-MM-dd}&hasta={yyyy-MM-dd}`
- `GET /api/v1/productos?restauranteId={id}`
- Swagger UI: `http://localhost:8080/swagger-ui/index.html`

## 4. Pendiente de implementación

### Seguridad y configuración

- Restricción automática de cada operación según permiso específico, además del rol.
- Matriz ampliada de permisos por módulo y acción.
- Rotación y gestión avanzada de secretos JWT.

### Operación

- Menú y opciones de menú completas.
- Validación de productos y disponibilidad al crear detalles.
- Estados operativos completos de mesa.
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

### Concurrencia y consultas

- Lock pesimista en Pedido durante cambios y edición.
- Lock pesimista en Venta durante cobro.
- Lock pesimista en Caja durante movimientos y cierre.
- Control optimista con `@Version` en Pedido, Venta y Caja.
- Paginación REST para pedidos y compras.
- Ordenamiento configurable mediante Spring Pageable.

### Inventario y compras

- Actualización automática de existencias derivada de compras y consumos.
- Integración completa entre compras recibidas y movimientos LLEGA.
- Integración entre pedidos vendidos y consumos operativos.
- Reversión automática de compras anuladas.
- Protección contra existencias negativas.

### Personal y reportes

- Reportes históricos de personal y valores.
- Reportes de ventas, gastos, compras, pagos, fiados y caja.
- Históricos mensuales.
- Indicadores configurables.

### Frontend

- Base Angular 20 standalone creada y compilada.
- Login JWT conectado al backend.
- Guard e interceptor Bearer implementados.
- Layout responsive con navegación por operación.
- Dashboard inicial y rutas lazy-loaded.
- Servidor local validado en `http://localhost:4200/login`.

Pendientes de frontend:

- Lazy loading por feature.
- Componentes smart/dumb.
- Interfaces responsive para computador, tablet y móvil.
- Vistas de administración, salón, cocina, caja y compras.
- Integración con la API y manejo de sesión JWT.

### Infraestructura y calidad

- Docker Desktop debe estar activo para probar PostgreSQL localmente.
- Migración inicial Flyway implementada.
- Validación real pendiente contra PostgreSQL/Flyway.
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
