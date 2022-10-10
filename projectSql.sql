create table if not exists users (
	user_id serial primary key unique,
	user_name VARCHAR(50) not null unique,
    password VARCHAR(50) not null,
    f_name VARCHAR(50) not null,
    l_name VARCHAR(50) not null,
	role VARCHAR(50) not null
);

create table if not exists tickets (
	ticket_id serial primary key unique,
	amount float not null,
	description VARCHAR(500) not null,
	status VARCHAR(50) not null,
	user_id int not null,
	foreign key(user_id) references users(user_id)
);

insert into "users"(user_name ,password ,role ,f_name ,l_name ) values
('jdoe','12345','Employee','John','Doe'),
('Jane.doe','56789','Manager','Jane','Doe')
;

insert into "tickets"(amount,description,status,user_id) values
(50.00,'gas to and from client location','pending',1),
(75.25,'overnight hotel lodging for expo detail','pending',2)
;

select * from "users";
select * from "tickets";

drop table users,tickets;