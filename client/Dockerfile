ARG JAVA_VERSION=11
FROM openjdk:${JAVA_VERSION}-jdk as builder

WORKDIR /src/client
COPY client ./client
COPY api ./api

RUN javac -Xlint ./*/*.java

FROM openjdk:${JAVA_VERSION}-jre

WORKDIR /app/client
COPY --from=builder /src/client .

ENTRYPOINT ["java", "client.Client"]
