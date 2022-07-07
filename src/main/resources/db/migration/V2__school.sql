/*
 * Copyright (c) 2022. Levente Hornyák
 */

DROP TABLE IF EXISTS school CASCADE;

CREATE TABLE IF NOT EXISTS school
(
    id                varchar(2) NOT NULL PRIMARY KEY CHECK (upper(id) = id),
    group_id          int2 NOT NULL,
    "name"            varchar(255) NOT NULL,
    country_code      varchar(2),
    zip_code          varchar(20),
    city              varchar(255),
    state_or_province varchar(255),
    street            varchar(255),
    is_active         boolean NOT NULL,
    CONSTRAINT fk_address_country FOREIGN KEY (country_code) REFERENCES country (code)
);
