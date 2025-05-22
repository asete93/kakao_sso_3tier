# FROM openjdk:17-jdk-slim

# RUN mkdir -p /app

# COPY ./camel-api-server-0.0.1-SNAPSHOT.jar /app/app.jar
# EXPOSE 8080

# RUN mkdir -p /app/extracted
# WORKDIR /app/extracted
# COPY ./camel-api-server-0.0.1-SNAPSHOT.jar /app/extracted/
# RUN jar -xf /app/extracted/camel-api-server-0.0.1-SNAPSHOT.jar
# RUN rm -rf /app/extracted

# ENTRYPOINT ["java", "-jar", "/app/app.jar"]



FROM openjdk:17-jdk-slim

RUN mkdir -p /app
RUN apt update -y
RUN apt install maven -y
COPY . ./
RUN ./gradlew bootjar
RUN cp ./build/libs/camel-api-server-0.0.1-SNAPSHOT.jar /app/app.jar
EXPOSE 8080

RUN mkdir -p /app/extracted
RUN cp ./build/libs/camel-api-server-0.0.1-SNAPSHOT.jar /app/extracted/
WORKDIR /app/extracted
RUN jar -xf /app/extracted/camel-api-server-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
