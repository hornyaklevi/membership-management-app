#!/bin/sh
export SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5433/test-db
export SPRING_DATASOURCE_USERNAME=test
export SPRING_DATASOURCE_PASSWORD=password

docker run -d --rm --name testdb --env-file .test-env -p 5433:5432 postgres:14.3-alpine@sha256:5973fec8d8284c8961ac3c23945e52d533dd0d21cc7b3c7dd7a46fd199bed4a4
mvn verify
docker stop testdb