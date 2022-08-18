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