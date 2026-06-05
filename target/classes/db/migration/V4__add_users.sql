CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL
);

-- Seed Initial Users
-- Passwords are 'admin123' and 'client123' hashed with BCrypt
INSERT INTO users (username, password, role) VALUES 
('admin', '$2a$10$wWfX.V9wF/nNfWl3qD/Uv.z.C9G5.L.4h00TfU90y5s57Z.90Y6n6', 'ADMIN'),
('client', '$2a$10$k1wJ5Y38N.M3Gv7w.D2zGe2V9r2O.0v0u.K896l4V027E2X5o32P.', 'CLIENT');
