FROM maven as builder
RUN mkdir /build /build/src
COPY pom.xml /build
COPY src/main /build/src/main
WORKDIR /build
RUN mvn -f pom.xml clean package
FROM adoptopenjdk:8-jre-hotspot
COPY --from=builder /build/target/*.jar /app/app.jar
WORKDIR /app
ENTRYPOINT ["java", "-jar", "app.jar"]