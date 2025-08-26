ALTER TABLE users
    ADD account_id BIGINT;

ALTER TABLE users
    ADD CONSTRAINT uc_users_account UNIQUE (account_id);

ALTER TABLE users
    ADD CONSTRAINT FK_USERS_ON_ACCOUNT FOREIGN KEY (account_id) REFERENCES accounts (id);