FROM adoptopenjdk/maven-openjdk11

RUN mkdir -p /opt/nuchallenge
ADD ./ /opt/nuchallenge/repo
WORKDIR /opt/nuchallenge/repo

RUN mvn clean package

WORKDIR /opt/nuchallenge
RUN mv repo/target/nuchallenge.jar .
RUN rm -rf ./repo ~/.m2

ENTRYPOINT ["java", "-jar", "nuchallenge.jar"]