#This will be our parent imgage
#it contains alpine linux and openjdk8
FROM alpine:latest

# Add package.json to allow for caching
ADD /target/*.jar app.jar 

#Add a volume for spring boot
VOLUME /tmp

#run the app
ENTRYPOINT ["sh", "-c", "java -jar app.jar"]