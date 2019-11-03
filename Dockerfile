FROM openjdk:8-jdk-alpine
EXPOSE 8080
VOLUME /tmp
ADD /target/boilerplate.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
#docker build -t stocker_api .