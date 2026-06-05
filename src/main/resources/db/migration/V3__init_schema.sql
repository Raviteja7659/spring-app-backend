DROP TABLE IF EXISTS transaction CASCADE;
DROP TABLE IF EXISTS goal CASCADE;
DROP TABLE IF EXISTS invoice CASCADE;

CREATE TABLE transaction (
    id BIGSERIAL PRIMARY KEY,
    date DATE NOT NULL,
    merchant VARCHAR(255) NOT NULL,
    category VARCHAR(255),
    amount DOUBLE PRECISION NOT NULL,
    account VARCHAR(255)
);

CREATE TABLE goal (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    target_amount DOUBLE PRECISION NOT NULL,
    current_amount DOUBLE PRECISION DEFAULT 0.0,
    status VARCHAR(50)
);

CREATE TABLE invoice (
    id BIGSERIAL PRIMARY KEY,
    client VARCHAR(255) NOT NULL,
    amount DOUBLE PRECISION NOT NULL,
    status VARCHAR(50),
    due_date DATE
);

-- Seed Initial Data
INSERT INTO transaction (date, merchant, category, amount, account) VALUES
(CURRENT_DATE, 'Whole Foods', 'Groceries', 84.20, 'Chase ••4521'),
(CURRENT_DATE - INTERVAL '2 days', 'United Airlines', 'Travel', 420.00, 'Amex ••9912'),
(CURRENT_DATE - INTERVAL '3 days', 'Figma Pro', 'Software', 15.00, 'Chase ••4521'),
(CURRENT_DATE - INTERVAL '4 days', 'Acme Corp payment', 'Income', -3200.00, 'Chase ••4521');

INSERT INTO goal (name, target_amount, current_amount, status) VALUES
('Japan trip', 4000.0, 2400.0, 'ON_TRACK'),
('Emergency fund', 15000.0, 8000.0, 'ON_TRACK'),
('New MacBook', 2500.0, 500.0, 'BEHIND');

INSERT INTO invoice (client, amount, status, due_date) VALUES
('Acme Corp', 3200.0, 'PAID', CURRENT_DATE - INTERVAL '4 days'),
('Startup Inc', 1800.0, 'PENDING', CURRENT_DATE + INTERVAL '5 days');
