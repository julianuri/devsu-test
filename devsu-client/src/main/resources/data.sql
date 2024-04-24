create sequence if not exists person_seq start with 1 increment by 50
create table if not exists person(age integer not null, status boolean not null, id bigint not null, dtype varchar(31) not null, address varchar(255) not null, gender varchar(255) not null, identification varchar(255) not null, name varchar(255) not null, password varchar(255) not null, phone varchar(255) not null, primary key (id))

create sequence if not exists account_seq start with 1 increment by 50
create table if not exists account(id bigint not null, type varchar(30) not null, status boolean not null, initial_balance numeric(22, 2) not null, current_balance numeric(22, 2) not null, person_id bigint not null, primary key (id), foreign key (person_id) references person on delete cascade)

create sequence if not exists transaction_seq start with 1 increment by 50
create table if not exists transaction(id bigint not null, update_date timestamptz not null, type varchar(30) not null, amount numeric(22, 2) not null, primary key (id), account_id bigint not null, foreign key (account_id) references account on delete cascade)
