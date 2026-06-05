CREATE TABLE category (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT fk_category_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE account (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT fk_account_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE freelancer_config (
    id BIGSERIAL PRIMARY KEY,
    tax_buffer_target DOUBLE PRECISION DEFAULT 5000.0,
    tax_buffer_current DOUBLE PRECISION DEFAULT 0.0,
    user_id BIGINT NOT NULL UNIQUE,
    CONSTRAINT fk_freelancer_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Add user_id to existing tables. We use DEFAULT 1 to satisfy NOT NULL for existing seed data.
ALTER TABLE transaction ADD COLUMN user_id BIGINT DEFAULT 1 NOT NULL;
ALTER TABLE transaction ADD CONSTRAINT fk_transaction_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;

ALTER TABLE goal ADD COLUMN user_id BIGINT DEFAULT 1 NOT NULL;
ALTER TABLE goal ADD CONSTRAINT fk_goal_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;

ALTER TABLE invoice ADD COLUMN user_id BIGINT DEFAULT 1 NOT NULL;
ALTER TABLE invoice ADD CONSTRAINT fk_invoice_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;

-- Seed default categories for admin
INSERT INTO category (name, user_id) VALUES 
('Groceries', 1), ('Travel', 1), ('Software', 1), ('Housing', 1);

-- Seed default accounts for admin
INSERT INTO account (name, user_id) VALUES 
('Checking Account', 1), ('Savings Account', 1);

-- Seed freelancer config for admin
INSERT INTO freelancer_config (tax_buffer_target, tax_buffer_current, user_id) VALUES 
(5000.0, 1150.0, 1);
