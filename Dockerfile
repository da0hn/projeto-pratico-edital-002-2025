FROM maven:3.9-amazoncorretto-21 AS build
WORKDIR /build

COPY pom.xml .
COPY src ./src/

RUN mvn clean package -DskipTests

FROM amazoncorretto:21-alpine

WORKDIR /app

COPY --from=build /build/target/sistema-gestao-institucional-api.jar app.jar

ENTRYPOINT ["sh", "-c", "java -Duser.timezone=\"America/Cuiaba\" -jar /app/app.jar"]
