-- clients table -------------------------------------------------------------------------------------------
DROP TABLE IF EXISTS clients;

TRUNCATE TABLE clients, bank_accounts RESTART IDENTITY;

CREATE TABLE clients (
	client_id SERIAL PRIMARY KEY,
	client_first_name VARCHAR(30) NOT NULL,
	client_last_name VARCHAR(30) NOT NULL,
	street_address VARCHAR (50) NOT NULL,
	city VARCHAR (30) NOT NULL,
	state VARCHAR (5)NOT NULL,
	zip_code VARCHAR (12) NOT NULL,
	email VARCHAR (50)NOT NULL,
	phone_number VARCHAR (20) NOT NULL
);

SELECT *
FROM clients;

INSERT INTO 
	clients (client_first_name, client_last_name, street_address, city, state, zip_code, email, phone_number)
VALUES
	('Jymm', 'Enriquez', '1212 Main Street', 'Corpus Christi', 'TX', '78415','jymmme@gmail.com', '3615491621');

		
UPDATE clients
SET client_first_name = 'John', client_last_name = 'Doe', street_address = '1212 Main Street', 
city = 'Corpus Christi', state = 'TX', zip_code = '78415', email = 'jymmme@gmail.com', phone_number = '3615491621'
WHERE
client_id = 1;

DELETE FROM clients
WHERE client_id = 1;

---------------------------------------------------------------------------------------------------------------
-- Copy this to java ------------------------------------------------------------------------------------------
SELECT *
FROM clients;

INSERT INTO 
	clients (client_first_name, client_last_name, street_address, city, state, zip_code, email, phone_number)
VALUES
(?, ?, ?, ?, ?, ?, ?, ?);

UPDATE clients
SET client_first_name = ?, client_last_name = ?, street_address = ?, city = ?, state = ?, 
zip_code = ?, email = ?, phone_number = ?
WHERE
client_id = ?;

DELETE FROM clients
WHERE client_id = ?;
---------------------------------------------------------------------------------------------------------------

------------------------------------------------------------------------
-- bank_accounts table -------------------------------------------------

DROP TABLE IF EXISTS bank_accounts;

TRUNCATE TABLE bank_accounts RESTART IDENTITY;

CREATE TABLE bank_accounts (
	bank_id SERIAL PRIMARY KEY,
	client_id INTEGER NOT NULL,
	bank_account_no VARCHAR(30) UNIQUE NOT NULL,
	bank_account_type VARCHAR (10) NOT NULL,
	amount DECIMAL (13,2) NOT NULL,
	
	CONSTRAINT fk_clients FOREIGN KEY(client_id)
		REFERENCES clients(client_id)
);

SELECT *
FROM bank_accounts
ORDER BY client_id;

SELECT *
FROM bank_accounts;

SELECT *
FROM bank_accounts
WHERE
bank_id = 5 AND client_id = 3;

SELECT b.bank_id, c.client_first_name, c.client_last_name, 
b.bank_account_no, b.bank_account_type, b.amount
FROM clients c
INNER JOIN bank_accounts b
ON c.client_id = b.client_id
WHERE
c.client_id = 1 AND b.amount BETWEEN 0 AND 1000000;

INSERT INTO 
	bank_accounts (client_id, bank_account_no, bank_account_type, amount)
VALUES
	(1, '898781669', 'Savings', 3000);

UPDATE bank_accounts
SET bank_account_no = '123456789', bank_account_type = 'Savings', amount = 1500
WHERE
client_id = 1 AND bank_id = 2;

DELETE FROM bank_accounts 
WHERE client_id = 3 AND bank_id = 1;

------------------------------------------------------------------
-- copy this to java ---------------------------------------------

SELECT *
FROM bank_accounts
WHERE
client_id = ?;

SELECT *
FROM bank_accounts
WHERE
bank_id = ? AND client_id = ?;

SELECT b.bank_id, c.client_first_name, c.client_last_name, 
b.bank_account_no, b.bank_account_type, b.amount
FROM clients c
INNER JOIN bank_accounts b
ON c.client_id = b.client_id
WHERE
c.client_id = ? AND b.amount BETWEEN ? AND ?;

INSERT INTO 
	bank_accounts (client_id, bank_account_no, bank_account_type, amount)
VALUES
	(?, ?, ?, ?);

UPDATE bank_accounts
SET bank_account_no = ?, bank_account_type = ?, amount = ?
WHERE
client_id = ? AND bank_id = ?;

DELETE FROM bank_accounts 
WHERE client_id = ? AND bank_id = ?;
------------------------------------------------------------------

