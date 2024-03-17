FROM openapitools/openapi-generator-cli:v6.6.0 as generateOpenApi

COPY iplat-master-service-openapi iplat-master-service-openapi
WORKDIR /iplat-master-service-openapi

RUN bash -c "/usr/local/bin/docker-entrypoint.sh batch"

RUN find "out" -type f -name 'pom.xml' -exec sed -i '/<\/dependencies>/i \
    ' {} \;

FROM maven:3.9.4-ibm-semeru-17-focal as build
COPY iplat-common iplat-common
COPY --from=generateOpenApi iplat-master-service-openapi iplat-master-service-openapi

WORKDIR /iplat-common
RUN mvn -f pom.xml clean install -DskipTests

WORKDIR /iplat-master-service-openapi
RUN mvn clean install -Dmaven.test.skip=true

WORKDIR /app
COPY mvnw .
COPY .mvn .mvn
COPY i18n i18n
COPY pom.xml .
COPY src src
RUN mvn -f pom.xml clean install -Dmaven.test.skip=true

FROM ibm-semeru-runtimes:open-17.0.8_7-jre
COPY --from=build /app/target/iplat-master-0.0.1-SNAPSHOT.jar /usr/app/
#COPY /target/iplat-master-0.0.1-SNAPSHOT.jar /usr/app/
COPY config /usr/app/config/
COPY i18n /usr/app/i18n/

ADD newrelic/newrelic.jar  /usr/app
ADD newrelic/newrelic.yml  /usr/app
# ENV NEW_RELIC_APP_NAME="My Application"
# ENV NEW_RELIC_LICENSE_KEY="license_key"
# ENV NEW_RELIC_LOG_FILE_NAME="STDOUT"

WORKDIR /usr/app
EXPOSE 8284
CMD ["java","-javaagent:newrelic.jar","-jar", "iplat-master-0.0.1-SNAPSHOT.jar"]
