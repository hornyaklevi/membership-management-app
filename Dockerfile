FROM maven:3.8.6-openjdk-18-slim@sha256:5730605b4b2b24a82329e31b533cb79458026bda012adae26ffea0c90c33fcc8 as builder
WORKDIR /app
EXPOSE 8080
COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean install -DskipTests

FROM openjdk:18-alpine@sha256:e5c5b35b831a4f655074a25604130ce53e33567b82c8a7204f0e5641b66d477e
WORKDIR /app
COPY --from=builder /app/target/MembershipManagementApp-0.0.1-SNAPSHOT.jar ./app.jar
CMD java -jar ./app.jar