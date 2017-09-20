# This will be our parent image
# it contains alpine linux and openjdk8
FROM openjdk:alpine

# Add the jar file into image
ADD /target/*.jar app.jar

# Add a volume for spring boot
# boot writes data for tomcat here
VOLUME /tmp

#run the app
ENTRYPOINT ["sh", "-c", "java -jar app.jar"]
