# Daily view server side

## Using skill
- dgs framework
- mysql
- jpa
- querydsl
- kotlin
- kotest

## Graphql docs [local]
http://localhost:9000/graphiql

## Lint format
- ./gradlew :api:ktlintFormat
- ./gradlew :domain:ktlintFormat

## Start with docker [local]
```shell
cd ./api
./gradlew bootJar && docker-compose up --build
```
GOTO http://localhost:9000/graphiql

## ERD
![DB-ERD](https://user-images.githubusercontent.com/70758906/185735149-1b7d2ec1-6c12-48a4-bd8f-4ea3fa1357d1.png)