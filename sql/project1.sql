create table ERS_REIMBURSEMENT_STATUSES(
	REIMB_STATUS_ID serial,
	REIMB_STUTUS varchar(10) unique,
	
	constraint ERS_STATUS_PK
	primary key (REIMB_STATUS_ID)
);

create table ERS_reimbursement_types(
	reimb_type_id serial,
	reimb_type varchar(10) unique,
	
	constraint ers_types_pk
	primary key (reimb_type_id)
);

create table ers_user_roles (
	role_id serial,
	role_name varchar(10) unique,
	
	constraint ers_roles_pk
	primary key (role_id)
);


create table ers_reimbursements (
	reimb_id serial,
	amount numeric(6,2),
	submitted timestamp,
	resolved timestamp,
	description text,
	reciept varchar(500),
	author_id int,
	resolver_id int,
	reimb_status_id int,
	reimb_type_id int,
	
	constraint ers_reimb_pk
	primary key (reimb_id),
	
	constraint ers_reimb_status_fk
	foreign key (reimb_status_id)
	references ers_reimbursement_statuses,
	
	constraint ers_reimb_types_fk
	foreign key (reimb_type_id)
	references ers_reimbursement_types
);

create table ers_users (
	ers_user_ID serial,
	username varchar(25) unique,
	user_password varchar(256),
	first_name varchar(25),
	last_name varchar(25),
	email varchar(256) unique,
	user_role_id int,
	
	constraint ers_users_pk
	primary key (ers_user_id),
	
	constraint ers_users_roles
	foreign key (user_role_id)
	references ers_user_roles
);

alter table ers_reimbursements
add
constraint ers_reimb_users_author_fk
foreign key (author_id)
references ers_users;

alter table ers_reimbursements
add
constraint ers_reimb_users_resolver_fk
foreign key (resolver_id)
references ers_users;

ALTER DEFAULT PRIVILEGES IN SCHEMA project1 GRANT SELECT ON TABLES TO project_app;
GRANT SELECT ON ALL TABLES IN SCHEMA project1 TO project_app;
GRANT update ON ALL TABLES IN SCHEMA project1 TO project_app;
GRANT insert ON ALL TABLES IN SCHEMA project1 TO project_app;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA project1 TO project_app;
grant usage on schema project1 to project_app;

commit;

insert into ers_user_roles (role_name ) values ('Employee');
insert into ers_user_roles (role_name ) values ('Admin');
insert into ers_user_roles (role_name ) values ('Manager');
delete from ers_users eu where ers_user_id =2;

