FROM maven:3-openjdk-11
RUN mvn clean install

FROM openjdk:11
ADD target/comic-rss-feed.jar comic-rss-feed.jar
ENTRYPOINT ["java","-jar","comic-rss-feed.jar"]