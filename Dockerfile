FROM maven:3.8.3-openjdk-11-slim AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:11-jre-slim
COPY --from=build /app/target/car-fixers-1.0-SNAPSHOT-war-exec.jar /myapp.jar
EXPOSE 8080
CMD ["java", "-jar", "/myapp.jar"]
