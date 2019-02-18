CREATE DATABASE reimburseDB;
--DROP DATABASE reimburseDB;

CREATE SCHEMA reimburse_process;
--DROP SCHEMA reimburse_process;

SET search_path TO reimburse_process,public;

-- DROP TABLE reimburse_process.profile_info
CREATE TABLE reimburse_process.profile_info(
    id SERIAL NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    routing_no INTEGER,
    account_no INTEGER,
    CONSTRAINT chk_min_profile_password CHECK (LENGTH(password) >= 8),
    CONSTRAINT pk_reimburse_id PRIMARY KEY(id)
);

-- DROP TABLE reimburse_process.requests
CREATE TABLE reimburse_process.requests(
    id SERIAL NOT NULL,
    status_id VARCHAR(100) NOT NULL,
    reciept BYTEA NULL,
    last_updated TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT (CURRENT_TIMESTAMP),
    CONSTRAINT pk_requests_id PRIMARY KEY(id)
);

-- DROP TABLE reimburse_process.status
CREATE TABLE reimburse_process.status(
    id SERIAL NOT NULL,
    status VARCHAR(100) NOT NULL,
    CONSTRAINT pk_status_id PRIMARY KEY(id)
);

 -- DROP TABLE reimburse_request.managers
 CREATE TABLE reimburse_process.managers(
     id SERIAL NOT NULL,
     profile_id INTEGER NOT NULL,
     request_id INTEGER NULL,
     CONSTRAINT pk_request_id PRIMARY KEY(id),
     CONSTRAINT u_managers_id_request UNIQUE(id, request_id),
     CONSTRAINT u_managers_id_profile UNIQUE(id, profile_id)
 );

 -- DROP TABLE eimburse_request.employess
 CREATE TABLE reimburse_process.employees(
     id SERIAL NOT NULL,
     manager_id INTEGER NOT NULL,
     CONSTRAINT pk_employees_id PRIMARY KEY(id),
     CONSTRAINT u_employees_id_manager UNIQUE(id, manager_id)
 );
