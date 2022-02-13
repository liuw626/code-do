FROM java:8
FROM maven:3.6.0-jdk-8-slim as build
MAINTAINER godric
WORKDIR /app
COPY code-do-application /app/code-do-application
COPY code-do-api /app/code-do-api
COPY code-do-service /app/code-do-service
COPY code-do-repository /app/code-do-repository
COPY code-do-dao /app/code-do-dao
COPY code-do-common /app/code-do-common

COPY settings.xml pom.xml /app/

RUN mvn -s /app/settings.xml -f /app/pom.xml clean package

FROM alpine:3.13

# 安装依赖包，如需其他依赖包，请到alpine依赖包管理(https://pkgs.alpinelinux.org/packages?name=php8*imagick*&branch=v3.13)查找。
# 选用国内镜像源以提高下载速度
RUN sed -i 's/dl-cdn.alpinelinux.org/mirrors.tencent.com/g' /etc/apk/repositories \
    && apk add --update --no-cache openjdk8-jre-base \
    && rm -f /var/cache/apk/*


WORKDIR /app

# 将构建产物jar包拷贝到运行时目录中
COPY --from=build /app/code-do-application/target/code-do-application-0.0.1-SNAPSHOT.jar ./

CMD ls
RUN ls

EXPOSE 80
# RUN mvn pom.xml clean package
# ADD ./code-do-application/target/code-do-application-0.0.1-SNAPSHOT.jar code-do.jar
# RUN bash -c 'touch /code-do.jar'
# ENTRYPOINT ["java", "-jar", "/code-do.jar"]
CMD ["java", "-jar", "/code-do-application-0.0.1-SNAPSHOT.jar"]