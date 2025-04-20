FROM maven:sapmachine

WORKDIR /app

COPY pom.xml pom.xml

COPY src src

RUN mvn package

ENTRYPOINT ["mvn","spring-boot:run"]