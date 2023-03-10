# UploadAndParseXmlFile

## What is it

An example app to parse xml files using HTTP REST multipart requests.

## Features

- Parse XML file and save file
- Check syntax error 
- Logging and handle exceptions

## Assumptions:

- Create a program to parse Xml file
- Add spring framework and create endpoint for xml parsing.
- Uploaded files are saved in /src/main/resources/upload/
- Logs are saved in /src/main/resources/log/
- Request:
  POST /file
  MULTIPART request with an xml file
- Response:

```
[
  {
    “name”: “Kalle Anka”,
    “email”: “donald@email.dt”,
    “userName”: “donaldd”
  },
  …
]
```

- Logging errors to file or on console can be changed in "application.properties" file.
Configuration options:

  - *spring.profiles.active=prod* - to logging to file
  - *spring.profiles.active=dev* - to logging on console

## Requirements:

- Spring Boot 2.7.5
- Java 17
- Gradle 7.5.1

## Run

#### To build jar file please use:

```
./gradlew bootJar
```

#### To run the application:

```
java -jar build/libs/UploadAndParseXmlFile-0.0.1-SNAPSHOT.jar
```

#### To run unit test

```
./gradlew test
```

#### Or run them from the IDE.

After above execution, service will start at port 8080.

## Rest API example request

Use HTTP multipart request to upload files. More specifically using API endpoint:

- **DELETE localhost:8080/filename** - to delete file "filename"
- **POST localhost:8080/file** - to upload file

Parameters to upload file:

- key: file
- value: -- xml file to upload, example file: [user.xml](src/main/resources/file/users.xml)

Or command line request:
```
curl --location localhost:8080/file --form file=@"/<path to your xml file.xml>"
```

### Xml file structure

```
<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<users>
    <user>
        <name>Kalle Anka</name>
        <email>donald@email.dt</email>
        <username>donaldd</username>
    </user>
    ...
</users>
```

### Response:

```
201 CREATED
[
  {
    "name": "Kalle Anka",
    "email": "donald@email.dt",
    "userName": "donaldd"
  },
  ...
]
```