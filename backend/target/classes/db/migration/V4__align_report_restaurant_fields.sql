ALTER TABLE turnos
    ADD COLUMN IF NOT EXISTS restaurante_id UUID;

UPDATE turnos turno
SET restaurante_id = empleado.restaurante_id
FROM empleados empleado
WHERE turno.empleado_id = empleado.id
  AND turno.restaurante_id IS NULL;

ALTER TABLE turnos
    ALTER COLUMN restaurante_id SET NOT NULL;
