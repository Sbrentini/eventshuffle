create table event (
    id int auto_increment primary key,
    name varchar(255) not null
);

create table event_date (
    id int auto_increment primary key,
    event_id int not null,
    date date not null,
    foreign key (event_id) references event(id)
);