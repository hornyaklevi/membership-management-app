/*
 * Copyright (c) 2022. Levente Hornyák
 */

DROP TABLE IF EXISTS schoolclass CASCADE;

CREATE TABLE IF NOT EXISTS schoolclass
(
    id                 varchar(7) NOT NULL PRIMARY KEY CHECK (upper(id) = id),
    year_of_graduation int2        NOT NULL,
    mark_of_class      varchar(1) NOT NULL,
    form_teacher       varchar(255),
    school_id          varchar(2) NOT NULL CHECK (upper(school_id) = school_id),
    CONSTRAINT fk_class_school FOREIGN KEY (school_id) REFERENCES school (id)
);
