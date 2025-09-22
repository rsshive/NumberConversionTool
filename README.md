# Spring Base Converter Project Structure

```
spring-base-converter/
└── src
    ├── main
    │   ├── java/com/example/baseconverter
    │   │   ├── BaseConverterApplication.java
    │   │   ├── config/
    │   │   │   └── ThymeleafConfig.java                 # (optional)
    │   │   ├── controller/
    │   │   │   ├── ConverterViewController.java         # Controller for View (Thymeleaf)
    │   │   │   └── ConverterApiController.java          # Controller for REST API
    │   │   ├── exception/
    │   │   │   ├── ApiError.java
    │   │   │   └── GlobalExceptionHandler.java
    │   │   ├── model/                                   # (Model/DTO)
    │   │   │   ├── ConvertRequest.java
    │   │   │   └── ConvertResponse.java
    │   │   ├── service/
    │   │   │   ├── BaseConvertService.java              # Interface (business layer)
    │   │   │   └── BaseConvertServiceImpl.java          # Implementation
    │   │   └── util/
    │   │       └── BaseDigits.java                      # Digit table & helpers
    │   └── resources
    │       ├── application.properties
    │       └── templates/
    │           └── index.html                           # View (V in MVC)
    └── test/java/com/example/baseconverter
        └── BaseConvertServiceTests.java                 # Unit test service
```