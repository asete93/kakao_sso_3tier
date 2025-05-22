#!/bin/bash
docker compose stop
apt install openjdk-17-jdk -y
apt install maven -y
./gradlew build
./gradlew bootjar
rm -f ./*.jar
cp ./build/libs/camel-api-server-0.0.1-SNAPSHOT.jar ./

sleep 1s
docker compose up -d --build
