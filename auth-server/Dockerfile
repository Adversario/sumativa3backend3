FROM eclipse-temurin:17-jre
WORKDIR /app
ARG JAR=target/auth-server-1.0.0.jar
COPY ${JAR} app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]
