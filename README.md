# Architecture training

## Goal of the module

The participants know how a web application looks like under the hood. They are familiar with the layers of a web application architecture and the basic flows between them. They are familiar with REST, API, XML and JSON. They understand the difference between front-end and back-end, and know how different services can connect to each other (micro services). They understand the purpose of mocking and integration testing and practiced endpoint testing with REST assured.

## Environment

Please install the following software:

- [Java SE Development Kit 8](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [IntelliJ IDEA Community edition](https://www.jetbrains.com/idea/download/)
- [Postman](https://www.getpostman.com/downloads/)
- MySQL Workbench (Optional)

**Clone this repo so that you have all the exercise files.**

## Schedule

- [Day 1: Web app architecture, REST](./day1.md)
- [Day 2: How to test web applications, Mocking, REST assured](./day2.md)

## Typical problems

### Lufthansa proxy settings

**Git**

Edit your `.gitconfig` file in your HOME directory:

```
[http]
    proxy = http://Uxxxxxx:password@proxy.lsy.bud.dlh.de:3128
    sslVerify = false
```

https://gist.github.com/evantoli/f8c23a37eb3558ab8765

**Gradle**

https://stackoverflow.com/questions/5991194/gradle-proxy-configuration

Open `gradle/wrapper/gradle-wrapper.properties`

```
systemProp.http.proxyHost=proxy.lsy.bud.dlh.de
systemProp.http.proxyPort=3128
systemProp.https.proxyHost=proxy.lsy.bud.dlh.de
systemProp.https.proxyPort=3128
```

### JAXB is deprecated

JAXB is deprecated in Java 9 and removed in 11.
