plugins {
    id("com.netflix.dgs.codegen") version "5.1.2"
}

tasks.withType<com.netflix.graphql.dgs.codegen.gradle.GenerateJavaTask> {
    generateClient = true
    packageName = "com.dailyview.api.generated"
    generateDataTypes = true
    snakeCaseConstantNames = true
    language = "kotlin"
}

group = "com.dailyview"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

val logstashLogbackVersion: String? by ext

dependencies {
    api(project(":domain"))
    implementation(platform("com.netflix.graphql.dgs:graphql-dgs-platform-dependencies:latest.release"))
    implementation("com.netflix.graphql.dgs:graphql-dgs-spring-boot-starter")
    implementation("com.netflix.graphql.dgs:graphql-dgs-extended-scalars")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("net.logstash.logback:logstash-logback-encoder:7.2")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.13.3")
    testImplementation(testFixtures(project(":domain")))
    testImplementation("com.netflix.graphql.dgs:graphql-dgs-client")
}
