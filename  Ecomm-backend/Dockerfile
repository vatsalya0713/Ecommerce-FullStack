FROM openjdk:21
WORKDIR /app


#copy the jar file into the container
COPY target/Ecomm-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 9090

#Command to run the Spring Boot app
CMD ["java","-jar","app.jar"]