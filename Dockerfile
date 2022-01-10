FROM openjdk:8-jdk-alpine
EXPOSE 5000
ADD target/pokedox-application.jar pokedox-application.jar
ENTRYPOINT ["java","-jar","pokedox-application.jar"]
