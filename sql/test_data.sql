-- setting up some test data for the database
insert into event (name) values
    ('Visit Helsinki'),
    ('Birthday party'),
    ('Cat agility event');

insert into event_date (event_id, date) values
    ((select id from event where name = 'Visit Helsinki'), '2025-08-01'),
    ((select id from event where name = 'Visit Helsinki'), '2025-08-02'),
    ((select id from event where name = 'Visit Helsinki'), '2025-08-03'),
    ((select id from event where name = 'Birthday party'), '2025-10-06'),
    ((select id from event where name = 'Birthday party'), '2025-10-15'),
    ((select id from event where name = 'Birthday party'), '2025-10-29'),
    ((select id from event where name = 'Birthday party'), '2025-10-30'),
    ((select id from event where name = 'Cat agility event'), '2025-11-10'),
    ((select id from event where name = 'Cat agility event'), '2025-11-12'),
    ((select id from event where name = 'Cat agility event'), '2025-11-14');

insert into person (name) values
    ('Anna'),
    ('Jessie'),
    ('Jerry'),
    ('Rafael');

insert into event_date_vote (event_date_id, voter_id)
values
    ((select id from event_date where date = '2025-08-01'), (select id from person where name = 'Anna')),
    ((select id from event_date where date = '2025-08-02'), (select id from person where name = 'Anna')),
    ((select id from event_date where date = '2025-08-03'), (select id from person where name = 'Anna')),
    ((select id from event_date where date = '2025-08-01'), (select id from person where name = 'Jessie')),
    ((select id from event_date where date = '2025-08-01'), (select id from person where name = 'Jerry')),
    ((select id from event_date where date = '2025-08-01'), (select id from person where name = 'Rafael')),
    ((select id from event_date where date = '2025-08-03'), (select id from person where name = 'Jerry'));

