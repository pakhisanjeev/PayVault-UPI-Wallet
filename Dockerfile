# Stage 1: Maven aur Java 21 se project build karo
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Java 21 runtime ke sath run karo
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
## Stage 1: Maven se project build karo
#FROM maven:3.8.5-openjdk-17 AS build
#WORKDIR /app
#COPY . .
#RUN mvn clean package -DskipTests
#
## Stage 2: Application run karo
#FROM eclipse-temurin:17-jdk-alpine
#WORKDIR /app
#COPY --from=build /app/target/*.jar app.jar
#ENTRYPOINT ["java","-jar","app.jar"]
##FROM eclipse-temurin:17-jdk-alpine
##VOLUME /tmp
##COPY target/*.jar app.jar
##ENTRYPOINT ["java","-jar","/app.jar"]