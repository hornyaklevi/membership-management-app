/*
 * Copyright (c) 2022. Levente Hornyák
 */

DROP TABLE IF EXISTS "member" CASCADE;

CREATE TABLE IF NOT EXISTS "member"
(
    member_id           bigserial    NOT NULL PRIMARY KEY,
    prefix_of_name      varchar(255),
    last_name           varchar(255) NOT NULL,
    first_name          varchar(255) NOT NULL,
    suffix_of_name      varchar(255),
    nick_name           varchar(255),
    place_of_birth      varchar(255),
    date_of_birth       date,
    school_class_id     varchar(7) CHECK (upper(school_class_id) = school_class_id),
    country_code        varchar(2) CHECK (upper(country_code) = country_code),
    zip_code            varchar(20),
    city                varchar(255),
    state_or_province   varchar(255),
    street              varchar(255),
    phone_number        varchar(255),
    email               varchar(255),
    is_allow_newsletter bool,
    status              varchar(255) NOT NULL,
    "type"              varchar(255) NOT NULL,
    "comment"           varchar(255),
    created_at          timestamp,
    updated_at          timestamp,
    CONSTRAINT fk_address_country FOREIGN KEY (country_code) REFERENCES country (code),
    CONSTRAINT fk_member_class FOREIGN KEY (school_class_id) REFERENCES schoolclass (id)
        ON DELETE SET NULL ON UPDATE CASCADE
);
