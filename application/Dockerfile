FROM amazoncorretto:17-alpine
ENV APP_HOME=/usr/app/
ARG JAR_FILE=build/libs/*SNAPSHOT.jar
WORKDIR $APP_HOME
COPY ${JAR_FILE} app.jar
EXPOSE 8880
RUN apk add tzdata && ln -snf /usr/share/zoneinfo/Asia/Seoul /etc/localtime
ENTRYPOINT ["java","-jar","app.jar","--spring.profiles.active=${SPRING_PROFILE}", "-Duser.timezone=Asia/Seoul"]