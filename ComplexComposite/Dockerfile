# This will be our parent image
# it contains alpine linux and openjdk8
FROM openjdk:alpine
# Update the alpine package
RUN apk update \
    && apk add maven
# Add the project folders to this container
ADD . .
# Add a volume for spring boot
# boot writes data for tomcat here
VOLUME /tmp
# Package the application
RUN mvn clean package
RUN mv /target/*.jar app.jar
#run the app
ENTRYPOINT ["sh", "-c", "java -jar app.jar"]