-- Crea el usuario y contraseña
CREATE USER crediya_user WITH PASSWORD 'C0ntr4s3n4C0mp13j4';

-- Otorga los permisos solo de SELECT, INSERT, UPDATE, DELETE sobre todas las tablas del schquema public
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public TO crediya_user;

-- Permite que el usuario reciba automáticamente los permisos del schema public
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO crediya_user;

-- Otorga permisos de uso y creación de secuencias en el schema public
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA public TO crediya_user