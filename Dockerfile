FROM amazoncorretto:23-alpine

RUN apk --no-cache update

WORKDIR /app

COPY . .

RUN ./gradlew bootJar

CMD ["java", "-jar", "/app/build/libs/jauth-0.0.1-SNAPSHOT.jar"]
