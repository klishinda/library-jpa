FROM openjdk:8-jdk-alpine
ADD target/library.jar library.jar
EXPOSE 8089
EXPOSE 27017
ENTRYPOINT ["java", "-jar", "library.jar"]