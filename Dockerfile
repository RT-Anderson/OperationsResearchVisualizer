FROM java:8-alpine
MAINTAINER Your Name <you@example.com>

ADD target/uberjar/optima.jar /optima/app.jar

EXPOSE 3000

CMD ["java", "-jar", "/optima/app.jar"]
