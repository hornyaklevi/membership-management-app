/*
 * Copyright (c) 2022. Levente Hornyák
 */

INSERT INTO school (id, group_id, "name", country_code, zip_code, city, state_or_province, street, is_active)
VALUES ('PS', 1, 'Petőfi Sándor Gimnázium', 'HU', '8103', 'Várpalota', 'Veszprém megye', 'Teréz krt. 17.',
        true),
       ('JA', 2, 'József Attila Gimnázium', 'HU', '2840', 'Oroszlány', 'Komárom-Esztergom megye', 'Munkácsy Mihály út 71.',
        true),
       ('KL', 3, 'Kossuth Lajos Gimnázium', 'HU', '1103', 'Budapest', 'Budapest', 'Váci út 17.',
        false),
       ('SI', 3, 'Szent István Gimnázium', 'HU', '2700', 'Cegléd', 'Pest megye', 'Magyar utca 1.',
        true),
       ('VM', 4, 'Vörösmarty Mihály Gimnázium', 'HU', '9681', 'Sótony', 'Vas megye', 'Victor Hugo utca 77.',
        true),
       ('KF', 5, 'Kölcsey Ferenc Gimnázium', 'HU', '5300', 'Karcag', 'Jász-Nagykun-Szolnok megye', 'Apor Péter utca 70.',
        false),
       ('BA', 6, 'Balatonfüredi Széchenyi István Gimnázium', 'HU', '8230', 'Balatonfüred', 'Veszprém megye', 'Jókai utca 15.',
        true),
       ('DF', 7, 'Deák Ferenc Gimnázium', 'HU', '6723', 'Szeged', 'Csongrád-Csanád megye', 'Bajai út 1.',
        false);

INSERT INTO schoolclass (id, year_of_graduation, mark_of_class, form_teacher, school_id)
VALUES ('PS2008B', '2008', 'B', 'Tóth Bertalan', 'PS'),
       ('JA1963A', '1963', 'A', 'Szántó Benjámin', 'JA'),
       ('KL1990A', '1990', 'A', 'Győri Domokos', 'KL'),
       ('KL1990C', '1990', 'C', 'Győri Domokos', 'KL'),
       ('VM1981B', '1981', 'B', 'Gere Balázs, Varga Zsombor', 'VM'),
       ('BA1962B', '1962', 'B', 'Benedek Alexander', 'BA'),
       ('BA2012A', '2012', 'A', 'Kelemen Olívia', 'BA'),
       ('DF1982B', '1982', 'B', 'Nagy Henrietta', 'DF'),
       ('SI1980B', '1980', 'B', 'Tóth Csenge', 'SI'),
       ('SI1984D', '1984', 'D', 'id. Kerekes Noel PhD', 'SI'),
       ('PS1971B', '1971', 'B', 'Tóth Bertalan', 'PS'),
       ('JA2016B', '2016', 'B', 'Orbán Julianna PhD', 'JA'),
       ('KF2012B', '2012', 'B', 'Veres Zsóka, id. Nemes Valéria', 'KF');

INSERT INTO "member" (prefix_of_name, last_name, first_name, suffix_of_name, nick_name, place_of_birth, date_of_birth,
                      school_class_id, country_code, zip_code, city, state_or_province, street, phone_number, email,
                      is_allow_newsletter, status, "type", "comment", created_at, updated_at)
VALUES ('Dr.', 'Végh', 'Ármin', 'PhD', 'Ármin', 'Budapest', '1986-08-06', 'PS2008B', 'HU', '7930', 'Patrik',
        'Pest megye', 'Kristóf árok 499 53. ajtó', '+36760398341', 'gabor.boros@24hinbox.com', true, 'ACTIVE',
        'REGULAR', null, '2022-07-01 16:30:00', '2022-07-01 16:30:00'),
       (null, 'Nagy', 'Bendegúz', null, null, 'Veszprém', '1953-05-06', 'JA1963A', 'HU', '3192', 'Budai',
        'Vas megye', 'Vass dűlősor 04 66. emelet', '+36713892777', 'cmagyar@longdz.site', true, 'ACTIVE',
        'HONORARY', null, '2022-07-01 16:30:00', '2022-07-01 16:30:00'),
       (null, 'Kovács', 'Ádám', null, 'Ádi', 'Szeged', '1953-06-09', 'JA1963A', 'HU', '5074', 'Juhász',
        'Pest megye', 'Olívia sétány 67014', '+36577755522', 'botond74@colevillecapital.com', true, 'ACTIVE',
        'REGULAR', null, '2022-07-01 16:30:00', '2022-07-01 16:30:00'),
       ('Dr.', 'Dobos', 'Vince', null, null, null, null, 'VM1981B', 'HU', '2547', 'Vecsés',
        'Pest megye', 'Szalai köz 0156', '+36301234567', 'mhegedus@gasss.net', false, 'ACTIVE',
        'CONTRIBUTING', null, '2022-07-01 16:30:00', '2022-07-01 16:30:00'),
       (null, 'Teszt', 'Elek', null, null, 'Budapest', '1969-08-03', 'KF2012B', 'HU', '6523', 'Kaposvár',
        'Tolna megye', 'Kossuth Lajos utca 15. 1. em. 25.', '+36083593908', 'szoke.kincso@v-mail.xyz', true, 'ACTIVE',
        'REGULAR', null, '2022-07-01 16:30:00', '2022-07-01 16:30:00');