CREATE DATABASE reimburseDB;
--DROP DATABASE reimburseDB;

CREATE SCHEMA reimburse_process;
--DROP SCHEMA reimburse_process;

SET search_path TO reimburse_process,public;

-- DROP TABLE reimburse_process.employees
CREATE TABLE reimburse_process.employees(
    id SERIAL NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    --routing_no INTEGER,
    --account_no INTEGER,
	isManager BOOLEAN, 
    CONSTRAINT pk_employee_id PRIMARY KEY(id)
);

-- DROP TABLE reimburse_process.reimbursements
CREATE TABLE reimburse_process.reimbursements(
	id SERIAL NOT NULL,
	status VARCHAR(100) NULL,
	reason VARCHAR(100) NOT NULL,
	employee_id INTEGER,
	receipt BYTEA NULL,
	CONSTRAINT pk_reimbursement_id PRIMARY KEY(id)
);
ALTER TABLE reimbursements ALTER COLUMN status DROP NOT NULL;
ALTER TABLE reimbursements DROP COLUMN status;
SELECT * FROM reimbursements;

--DROP TABLE reimburse_process.credentials
CREATE TABLE reimburse_process.credentials(
	id SERIAL NOT NULL,
	user_name VARCHAR(100) NOT NULL,
	password VARCHAR(100) NOT NULL,
	CONSTRAINT pk_credential_id PRIMARY KEY(id)
);
SELECT * FROM reimburse_process.employees;
DELETE FROM reimburse_process.employees;
INSERT INTO reimburse_process.employees VALUES
(1,'Mehrab', 'Rahman','mehrab@email.com', TRUE),
(2,'Mark', 'Hoppus','mark@email.com', FALSE),
(3,'Tom', 'Delonge','tom@email.com', FALSE),
(4,'Travis', 'Barker','travis@email.com', FALSE),
(5,'Bernie', 'Sanders','bernie@email.com', FALSE);


SELECT * FROM reimburse_process.credentials;
INSERT INTO credentials VALUES
(1,'mehrab@email.com','password'),
(2,'mark@email.com','password'),
(3,'tom@email.com','password'),
(4,'travis@email.com','password'),
(5,'bernie@email.com','password');

SELECT * FROM reimburse_process.reimbursements;
INNER JOIN employees
ON employees.id = reimbursements.employee_id;
DELETE FROM reimburse_process.reimbursements
WHERE id = '4';
INSERT INTO credentials VALUES
