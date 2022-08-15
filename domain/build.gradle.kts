import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    kotlin("kapt")
    kotlin("plugin.jpa")
    id("kotlin-allopen")
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}

tasks {
    register<Exec>("syncSchema") {
        executable = "sh"
        commandLine = "${project.rootDir}/tools/sync-schema/sync_schema.sh".split(" ")
    }
}

dependencies {
    api("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    api("org.springframework.boot:spring-boot-actuator-autoconfigure:2.7.2")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.apache.commons:commons-text:1.9")
    implementation("com.github.zh79325:open-gif:1.0.4")
    runtimeOnly("mysql:mysql-connector-java")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    kapt("org.springframework.boot:spring-boot-configuration-processor")
    api("com.querydsl:querydsl-jpa:5.0.0")
    kapt("com.querydsl:querydsl-apt:5.0.0:jpa")
    implementation("org.springframework.data:spring-data-envers")
    implementation("org.springframework.retry:spring-retry")
    implementation("commons-io:commons-io:2.8.0")
    api("com.netflix.graphql.dgs:graphql-dgs:5.0.5")
    api("com.netflix.graphql.dgs:graphql-dgs-client:5.0.5")
}
repositories {
    mavenCentral()
}

tasks.withType<Jar> {
    enabled = true
}

tasks.withType<BootJar> {
    enabled = false
}
