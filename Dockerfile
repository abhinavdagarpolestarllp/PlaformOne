# Stage 1: build
FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /workspace
COPY . .
RUN mvn -B -DskipTests package

# Stage 2: runtime (we keep maven so mvn test runs inside container)
FROM maven:3.9.4-eclipse-temurin-17
WORKDIR /workspace
COPY --from=build /workspace /workspace
COPY docker/entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

ENV SELENIUM_REMOTE_URL="http://selenium-hub:4444/wd/hub"
ENV REPORT_DIR="/reports"
ENV MAVEN_OPTS="-Xmx1024m"

VOLUME /reports
ENTRYPOINT ["/entrypoint.sh"]
