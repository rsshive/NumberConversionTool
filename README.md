# Number Conversion Tool

A Spring Boot application for converting numbers and text between different bases (e.g., binary, decimal, hexadecimal, text).

## Features

- Convert numbers between bases 2–36
- Convert text to numeric base representations and vice versa
- REST API and web interface
- Input validation and error handling

## Technologies

- Java
- Spring Boot
- Maven

## Getting Started

### Prerequisites

- Java 17+
- Maven

### Build and Run

```sh
mvn clean package
java -jar target/numberconversiontool-0.0.1-SNAPSHOT.jar
```

### Structure
```
number-conversion-tool/
├─ pom.xml
├─ README.md
└─ src/
   ├─ main/
   │  ├─ java/rsshive/prac/numberconversiontool/
   │  │  ├─ NumberConversionToolApplication.java
   │  │  ├─ controller/
   │  │  │  ├─ ConverterApiController.java
   │  │  │  └─ ConverterViewController.java
   │  │  ├─ exception/
   │  │  │  ├─ ConversionException.java
   │  │  │  ├─ GlobalExceptionHandler.java
   │  │  │  └─ ApiExceptionHandler.java
   │  │  ├─ model/
   │  │  │  ├─ BaseType.java
   │  │  │  ├─ ConvertRequest.java
   │  │  │  └─ ConvertResponse.java
   │  │  ├─ service/
   │  │  │  ├─ BaseConvertService.java
   │  │  │  └─ BaseConvertServiceImpl.java
   │  │  └─ util/
   │  │     └─ BaseConverterUtil.java
   │  └─ resources/
   │     ├─ application.properties
   │     └─ templates/
   │        └─ index.html
   └─ test/java/.../BaseConvertServiceTests.java
```