FROM adoptopenjdk/openjdk11:alpine-jre

ARG JAR_FILE=target/bank-management-app-*.jar

COPY ${JAR_FILE} bank-management-app.jar

ENTRYPOINT ["java","-jar","bank-management-app.jar"]