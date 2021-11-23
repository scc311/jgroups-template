ARG JGROUPS_VERSION=3.6.20
FROM ghcr.io/scc311/jgroups:${JGROUPS_VERSION} as builder

WORKDIR /src/frontend
COPY frontend ./frontend
COPY utility ./utility
COPY api ./api

RUN javac -Xlint ./*/*.java

FROM ghcr.io/scc311/jgroups:jre-${JGROUPS_VERSION}

WORKDIR /app/frontend
COPY --from=builder /src/frontend .

ENTRYPOINT ["java", "-Djava.net.preferIPv4Stack=true", "frontend.Frontend"]
