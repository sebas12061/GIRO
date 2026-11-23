CREATE TABLE IF NOT EXISTS restaurants (
    id UUID PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    identification VARCHAR(50) NOT NULL UNIQUE,
    status VARCHAR(20) NOT NULL,
    fecha_creacion TIMESTAMP WITH TIME ZONE
);

CREATE TABLE IF NOT EXISTS permisos (
    id UUID PRIMARY KEY,
    name VARCHAR(80) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS roles (
    id UUID PRIMARY KEY,
    name VARCHAR(60) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS roles_permisos (
    rol_id UUID NOT NULL,
    permiso_id UUID NOT NULL,
    PRIMARY KEY (rol_id, permiso_id)
);

CREATE TABLE IF NOT EXISTS usuarios (
    id UUID PRIMARY KEY,
    restaurante_id UUID NOT NULL,
    empleado_id UUID,
    username VARCHAR(80) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    activo BOOLEAN NOT NULL,
    FOREIGN KEY (restaurante_id) REFERENCES restaurants(id)
);

CREATE TABLE IF NOT EXISTS usuarios_roles (
    usuario_id UUID NOT NULL,
    rol_id UUID NOT NULL,
    PRIMARY KEY (usuario_id, rol_id)
);

CREATE TABLE IF NOT EXISTS empleados (
    id UUID PRIMARY KEY,
    restaurante_id UUID NOT NULL,
    name VARCHAR(150) NOT NULL,
    documento VARCHAR(50),
    telefono VARCHAR(30),
    status VARCHAR(20) NOT NULL,
    FOREIGN KEY (restaurante_id) REFERENCES restaurants(id)
);

CREATE TABLE IF NOT EXISTS turnos (
    id UUID PRIMARY KEY,
    empleado_id UUID NOT NULL,
    fecha DATE NOT NULL,
    tipo VARCHAR(20) NOT NULL,
    hora_inicio TIME NOT NULL,
    hora_fin TIME NOT NULL,
    valor NUMERIC(14,2) NOT NULL,
    status VARCHAR(20) NOT NULL,
    FOREIGN KEY (empleado_id) REFERENCES empleados(id)
);

CREATE TABLE IF NOT EXISTS categorias (
    id UUID PRIMARY KEY,
    restaurante_id UUID NOT NULL,
    name VARCHAR(100) NOT NULL,
    activo BOOLEAN NOT NULL,
    FOREIGN KEY (restaurante_id) REFERENCES restaurants(id)
);

CREATE TABLE IF NOT EXISTS unidades_operativas (
    id UUID PRIMARY KEY,
    name VARCHAR(40) NOT NULL UNIQUE,
    abbreviation VARCHAR(10) NOT NULL
);

CREATE TABLE IF NOT EXISTS productos (
    id UUID PRIMARY KEY,
    restaurante_id UUID NOT NULL,
    categoria_id UUID NOT NULL,
    unidad_id UUID NOT NULL,
    name VARCHAR(150) NOT NULL,
    price NUMERIC(14,2) NOT NULL,
    status VARCHAR(20) NOT NULL,
    FOREIGN KEY (restaurante_id) REFERENCES restaurants(id),
    FOREIGN KEY (categoria_id) REFERENCES categorias(id),
    FOREIGN KEY (unidad_id) REFERENCES unidades_operativas(id)
);

CREATE TABLE IF NOT EXISTS mesas (
    id UUID PRIMARY KEY,
    restaurante_id UUID NOT NULL,
    numero INTEGER NOT NULL,
    capacidad INTEGER NOT NULL,
    estado VARCHAR(20) NOT NULL,
    UNIQUE (restaurante_id, numero),
    FOREIGN KEY (restaurante_id) REFERENCES restaurants(id)
);

CREATE TABLE IF NOT EXISTS clientes (
    id UUID PRIMARY KEY,
    restaurante_id UUID NOT NULL,
    name VARCHAR(150) NOT NULL,
    telefono VARCHAR(30),
    direccion_referencia VARCHAR(255),
    tipo_cliente VARCHAR(20) NOT NULL,
    status VARCHAR(20) NOT NULL,
    FOREIGN KEY (restaurante_id) REFERENCES restaurants(id)
);

CREATE TABLE IF NOT EXISTS menus (
    id UUID PRIMARY KEY,
    restaurante_id UUID NOT NULL,
    name VARCHAR(150) NOT NULL,
    fecha_inicio DATE,
    fecha_fin DATE,
    status VARCHAR(20) NOT NULL,
    FOREIGN KEY (restaurante_id) REFERENCES restaurants(id)
);

CREATE TABLE IF NOT EXISTS opciones_menu (
    id UUID PRIMARY KEY,
    menu_id UUID NOT NULL,
    producto_id UUID,
    categoria_id UUID,
    name VARCHAR(150) NOT NULL,
    price NUMERIC(14,2) NOT NULL,
    orden INTEGER NOT NULL,
    status VARCHAR(20) NOT NULL,
    FOREIGN KEY (menu_id) REFERENCES menus(id)
);

CREATE TABLE IF NOT EXISTS pedidos (
    id UUID PRIMARY KEY,
    version BIGINT,
    restaurante_id UUID NOT NULL,
    numero BIGINT NOT NULL,
    estado VARCHAR(30) NOT NULL,
    total NUMERIC(14,2) NOT NULL,
    fecha_hora TIMESTAMP WITH TIME ZONE NOT NULL,
    mesa_id UUID,
    cliente_id UUID,
    tipo_servicio VARCHAR(20) NOT NULL,
    observaciones VARCHAR(1000),
    FOREIGN KEY (restaurante_id) REFERENCES restaurants(id)
);

CREATE TABLE IF NOT EXISTS detalles_pedido (
    id UUID PRIMARY KEY,
    pedido_id UUID NOT NULL,
    producto_id UUID NOT NULL,
    opcion_menu_id UUID,
    cantidad NUMERIC(12,3) NOT NULL,
    precio_unitario NUMERIC(14,2) NOT NULL,
    subtotal NUMERIC(14,2) NOT NULL,
    observaciones VARCHAR(500),
    estado VARCHAR(20) NOT NULL,
    FOREIGN KEY (pedido_id) REFERENCES pedidos(id)
);

CREATE TABLE IF NOT EXISTS ventas (
    id UUID PRIMARY KEY,
    version BIGINT,
    restaurante_id UUID NOT NULL,
    pedido_id UUID NOT NULL UNIQUE,
    subtotal NUMERIC(14,2) NOT NULL,
    total NUMERIC(14,2) NOT NULL,
    status VARCHAR(20) NOT NULL,
    fecha_hora TIMESTAMP WITH TIME ZONE NOT NULL,
    FOREIGN KEY (restaurante_id) REFERENCES restaurants(id),
    FOREIGN KEY (pedido_id) REFERENCES pedidos(id)
);

CREATE TABLE IF NOT EXISTS pagos (
    id UUID PRIMARY KEY,
    restaurante_id UUID NOT NULL,
    venta_id UUID NOT NULL,
    tipo VARCHAR(20) NOT NULL,
    valor NUMERIC(14,2) NOT NULL,
    referencia VARCHAR(120),
    status VARCHAR(30) NOT NULL,
    fecha_hora TIMESTAMP WITH TIME ZONE NOT NULL,
    FOREIGN KEY (restaurante_id) REFERENCES restaurants(id),
    FOREIGN KEY (venta_id) REFERENCES ventas(id)
);

CREATE TABLE IF NOT EXISTS fiados (
    id UUID PRIMARY KEY,
    restaurante_id UUID NOT NULL,
    cliente_id UUID NOT NULL,
    tipo VARCHAR(20) NOT NULL,
    fecha_inicio DATE NOT NULL,
    fecha_vencimiento DATE,
    saldo NUMERIC(14,2) NOT NULL,
    status VARCHAR(20) NOT NULL,
    fecha_hora TIMESTAMP WITH TIME ZONE NOT NULL,
    FOREIGN KEY (restaurante_id) REFERENCES restaurants(id),
    FOREIGN KEY (cliente_id) REFERENCES clientes(id)
);

CREATE TABLE IF NOT EXISTS movimientos_fiado (
    id UUID PRIMARY KEY,
    fiado_id UUID NOT NULL,
    venta_id UUID,
    tipo_movimiento VARCHAR(20) NOT NULL,
    valor NUMERIC(14,2) NOT NULL,
    fecha_hora TIMESTAMP WITH TIME ZONE NOT NULL,
    observacion VARCHAR(500),
    FOREIGN KEY (fiado_id) REFERENCES fiados(id)
);

CREATE TABLE IF NOT EXISTS cajas (
    id UUID PRIMARY KEY,
    version BIGINT,
    restaurante_id UUID NOT NULL,
    base_inicial NUMERIC(14,2) NOT NULL,
    fecha_apertura TIMESTAMP WITH TIME ZONE NOT NULL,
    fecha_cierre TIMESTAMP WITH TIME ZONE,
    status VARCHAR(20) NOT NULL,
    FOREIGN KEY (restaurante_id) REFERENCES restaurants(id)
);

CREATE TABLE IF NOT EXISTS movimientos_caja (
    id UUID PRIMARY KEY,
    caja_id UUID NOT NULL,
    restaurante_id UUID NOT NULL,
    tipo VARCHAR(20) NOT NULL,
    concepto VARCHAR(180) NOT NULL,
    valor NUMERIC(14,2) NOT NULL,
    origen VARCHAR(30),
    usuario_id UUID,
    fecha_hora TIMESTAMP WITH TIME ZONE NOT NULL,
    FOREIGN KEY (caja_id) REFERENCES cajas(id)
);

CREATE TABLE IF NOT EXISTS cuadres (
    id UUID PRIMARY KEY,
    caja_id UUID NOT NULL UNIQUE,
    base NUMERIC(14,2) NOT NULL,
    ventas_efectivo NUMERIC(14,2) NOT NULL,
    transferencias NUMERIC(14,2) NOT NULL,
    fiados NUMERIC(14,2) NOT NULL,
    gastos NUMERIC(14,2) NOT NULL,
    efectivo_esperado NUMERIC(14,2) NOT NULL,
    efectivo_real NUMERIC(14,2) NOT NULL,
    diferencia NUMERIC(14,2) NOT NULL,
    usuario_id UUID,
    fecha_hora TIMESTAMP WITH TIME ZONE NOT NULL,
    status VARCHAR(20) NOT NULL,
    FOREIGN KEY (caja_id) REFERENCES cajas(id)
);

CREATE TABLE IF NOT EXISTS proveedores (
    id UUID PRIMARY KEY,
    restaurante_id UUID NOT NULL,
    name VARCHAR(150) NOT NULL,
    identificacion VARCHAR(50),
    telefono VARCHAR(30),
    direccion VARCHAR(255),
    status VARCHAR(20) NOT NULL,
    FOREIGN KEY (restaurante_id) REFERENCES restaurants(id)
);

CREATE TABLE IF NOT EXISTS compras (
    id UUID PRIMARY KEY,
    restaurante_id UUID NOT NULL,
    proveedor_id UUID NOT NULL,
    caja_id UUID,
    fecha TIMESTAMP WITH TIME ZONE NOT NULL,
    total NUMERIC(14,2) NOT NULL,
    status VARCHAR(20) NOT NULL,
    observacion VARCHAR(1000),
    FOREIGN KEY (restaurante_id) REFERENCES restaurants(id),
    FOREIGN KEY (proveedor_id) REFERENCES proveedores(id)
);

CREATE TABLE IF NOT EXISTS detalles_compra (
    id UUID PRIMARY KEY,
    compra_id UUID NOT NULL,
    producto_id UUID NOT NULL,
    cantidad NUMERIC(12,3) NOT NULL,
    valor_unitario NUMERIC(14,2) NOT NULL,
    subtotal NUMERIC(14,2) NOT NULL,
    FOREIGN KEY (compra_id) REFERENCES compras(id)
);

CREATE TABLE IF NOT EXISTS movimientos_producto (
    id UUID PRIMARY KEY,
    restaurante_id UUID NOT NULL,
    producto_id UUID NOT NULL,
    unidad_id UUID NOT NULL,
    fecha DATE NOT NULL,
    tipo_movimiento VARCHAR(20) NOT NULL,
    cantidad NUMERIC(12,3) NOT NULL,
    referencia VARCHAR(120),
    usuario_id UUID,
    observacion VARCHAR(500),
    fecha_hora TIMESTAMP WITH TIME ZONE NOT NULL,
    FOREIGN KEY (restaurante_id) REFERENCES restaurants(id),
    FOREIGN KEY (producto_id) REFERENCES productos(id),
    FOREIGN KEY (unidad_id) REFERENCES unidades_operativas(id)
);

CREATE TABLE IF NOT EXISTS cierres_operativos (
    id UUID PRIMARY KEY,
    restaurante_id UUID NOT NULL,
    fecha DATE NOT NULL,
    usuario_id UUID,
    status VARCHAR(20) NOT NULL,
    UNIQUE (restaurante_id, fecha),
    FOREIGN KEY (restaurante_id) REFERENCES restaurants(id)
);

CREATE TABLE IF NOT EXISTS detalles_cierre_operativo (
    id UUID PRIMARY KEY,
    cierre_id UUID NOT NULL,
    producto_id UUID NOT NULL,
    cantidad_queda NUMERIC(12,3) NOT NULL,
    unidad_id UUID NOT NULL,
    observacion VARCHAR(500),
    FOREIGN KEY (cierre_id) REFERENCES cierres_operativos(id)
);

CREATE TABLE IF NOT EXISTS auditoria (
    id UUID PRIMARY KEY,
    restaurante_id UUID,
    usuario_id UUID,
    action VARCHAR(80) NOT NULL,
    entity VARCHAR(80) NOT NULL,
    entity_id UUID,
    old_value VARCHAR(2000),
    new_value VARCHAR(2000),
    occurred_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_pedidos_restaurante_estado ON pedidos(restaurante_id, estado);
CREATE INDEX IF NOT EXISTS idx_pagos_restaurante_tipo_status ON pagos(restaurante_id, tipo, status);
CREATE INDEX IF NOT EXISTS idx_movimientos_producto_restaurante_producto_fecha
    ON movimientos_producto(restaurante_id, producto_id, fecha);
CREATE INDEX IF NOT EXISTS idx_movimientos_caja_caja ON movimientos_caja(caja_id);
CREATE INDEX IF NOT EXISTS idx_compras_restaurante_fecha ON compras(restaurante_id, fecha);
CREATE INDEX IF NOT EXISTS idx_auditoria_restaurante_fecha ON auditoria(restaurante_id, occurred_at);
