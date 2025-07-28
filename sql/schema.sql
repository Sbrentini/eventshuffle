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

create table person (
    id int auto_increment primary key,
    name varchar(255) not null
);

create table event_date_vote (
    event_date_id int not null,
    voter_id int not null,
    primary key (event_date_id, voter_id),
    foreign key (event_date_id) references event_date(id),
    foreign key (voter_id) references person(id)
);