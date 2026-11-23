# Backend de GIRO

Esta carpeta contiene la API principal de GIRO desarrollada con Spring Boot.

## Objetivo

Centralizar la lógica de negocio, validaciones, seguridad y acceso a la base de datos.

## Módulos esperados

- auth
- users
- roles
- restaurants
- products
- menus
- orders
- kitchen
- sales
- cash
- purchases
- inventory
- staff
- reports
- audit

## Tecnologías esperadas

- Java 21
- Spring Boot 3.x
- Spring Security
- Spring Data JPA
- PostgreSQL
- Maven o Gradle

## Primera tarea

La primera base ya está creada con:

- configuración de Spring Boot
- endpoint `GET /api/v1/public/health`
- entidad base `Restaurant`
- conexión a PostgreSQL
- autenticación JWT stateless
- usuarios, roles y permisos con BCrypt
- prueba de contexto con H2

## Patrones aplicados

- Arquitectura por módulo: `pedidos/`, `pagos/` y `auditoria/` mantienen sus responsabilidades agrupadas.
- DTO + MapStruct: la API de pedidos no expone entidades JPA.
- State: `PedidoStateMachine` centraliza las transiciones y bloquea cambios desde `CERRADO`.
- Strategy: los medios de pago y el cálculo de efectivo esperado son extensibles.
- Eventos + AOP: las operaciones anotadas publican eventos de auditoría sin acoplar el módulo de Pedidos al repositorio de auditoría.
- Multi-restaurante: las consultas de pedidos requieren `restauranteId`.
- BCrypt y method security: RBAC y contraseñas hasheadas están conectados a Spring Security.

## Migraciones y perfiles

La base usa Flyway. JPA valida el esquema y ya no crea/modifica tablas automáticamente.

- Perfil `test`: H2, Flyway desactivado.
- Perfil `dev`: PostgreSQL y Flyway habilitado.
- Perfil `prod`: validación y Flyway habilitados.

Migración inicial: `src/main/resources/db/migration/V1__baseline_giro.sql`.

Para iniciar todo el entorno desde Docker:

```powershell
cd C:\Users\sebas\Documents\Giro
docker compose up -d --build
```

## Pruebas de integración PostgreSQL

Con Docker Desktop iniciado:

```powershell
cd C:\Users\sebas\Documents\Giro
docker compose up -d postgres
cd backend
mvn test "-Dspring.profiles.active=integration"
```

La prueba `PostgresIntegrationTest` usará PostgreSQL y Flyway. Si PostgreSQL no está disponible, asumirá la prueba en vez de romper la suite normal.

## CORS Angular

El origen local predeterminado es `http://localhost:4200`. Puede cambiarse con:

```powershell
$env:GIRO_CORS_ALLOWED_ORIGINS="http://localhost:4200,http://127.0.0.1:4200"
```

## Ejecutar

Desde esta carpeta:

```powershell
mvn test
mvn spring-boot:run
```

Para iniciar PostgreSQL desde la raíz del proyecto. GIRO usa el puerto host `5433` para evitar conflictos con instalaciones locales de PostgreSQL:

```powershell
docker compose up -d postgres
```

La API queda disponible en `http://localhost:8080` y el endpoint público de salud es:

```text
GET http://localhost:8080/api/v1/public/health
```

La documentación interactiva OpenAPI queda disponible en:

```text
http://localhost:8080/swagger-ui/index.html
```

## Crear datos iniciales de desarrollo

El bootstrap está desactivado por defecto. Para crear un restaurante local, el rol `ADMINISTRADOR`, el permiso `GIRO_ADMIN` y un usuario administrador:

```powershell
$env:GIRO_BOOTSTRAP_ENABLED="true"
$env:GIRO_ADMIN_USERNAME="admin"
$env:GIRO_ADMIN_PASSWORD="cambia-esta-clave-local"
mvn spring-boot:run
```

Después del primer arranque puedes iniciar sesión en Swagger con:

```text
POST /api/v1/auth/login
{
	"username": "admin",
	"password": "cambia-esta-clave-local"
}
```

En Swagger pulsa `Authorize` y escribe `Bearer <accessToken>` para probar las rutas protegidas.

La seguridad protege todas las rutas salvo salud y login. El endpoint de autenticación es:

```text
POST http://localhost:8080/api/v1/auth/login
```

El secreto JWT debe cambiarse mediante `GIRO_JWT_SECRET` antes de cualquier despliegue real.
