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

## Ejecutar

Desde esta carpeta:

```powershell
mvn test
mvn spring-boot:run
```

Para iniciar PostgreSQL desde la raíz del proyecto:

```powershell
docker compose up -d postgres
```

La API queda disponible en `http://localhost:8080` y el endpoint público de salud es:

```text
GET http://localhost:8080/api/v1/public/health
```

La seguridad protege todas las rutas salvo salud y login. El endpoint de autenticación es:

```text
POST http://localhost:8080/api/v1/auth/login
```

El secreto JWT debe cambiarse mediante `GIRO_JWT_SECRET` antes de cualquier despliegue real.
