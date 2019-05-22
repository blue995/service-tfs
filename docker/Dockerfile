FROM openjdk:8-jdk-alpine AS build

WORKDIR /usr/src/app

# Copy files
COPY ./build.gradle ./build.gradle
COPY ./build-quality.gradle ./build-quality.gradle
COPY ./gradle.properties ./gradle.properties

COPY ./gradlew ./gradlew
COPY ./gradlew.bat ./gradlew.bat

COPY ./settings.gradle ./settings.gradle

# Copy necessary folders folders
COPY ./.git ./.git
COPY ./gradle ./gradle
RUN ./gradlew


# Copy sources and execute gradle build
COPY ./src ./src
RUN ./gradlew build


FROM openjdk:8-jre-alpine AS production

LABEL maintainer="Tobias Blaufuss <tobias.blaufuss@outlook.de>"
ARG version="4.3.0-SNAPSHOT"
LABEL version=$version
#LABEL description="@description@"

ENV JAVA_OPTS="-Xmx256m -Djava.security.egd=file:/dev/./urandom"
ENV JAVA_APP=/app.jar

RUN echo $'#!/bin/sh \n\
exec java $JAVA_OPTS -jar $JAVA_APP' > /start.sh && chmod +x /start.sh
COPY --from=build /usr/src/app/build/libs/service-delegation-$version.jar $JAVA_APP

EXPOSE 8080
ENTRYPOINT ["/start.sh"]
