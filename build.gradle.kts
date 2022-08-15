import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    dependencies {
        classpath(kotlin("gradle-plugin", version = "1.4.10"))
    }
    repositories {
        mavenCentral()
    }
}

plugins {
    val kotlinVersion = "1.7.10"
    idea
    kotlin("jvm") version kotlinVersion
    kotlin("kapt") version kotlinVersion apply false
    kotlin("plugin.spring") version kotlinVersion apply false
    kotlin("plugin.jpa") version kotlinVersion apply false
    id("org.springframework.boot") version "2.5.3" apply false
    id("io.spring.dependency-management") version "1.0.10.RELEASE"
    id("org.jlleitschuh.gradle.ktlint") version "10.1.0" apply false
    id("org.jlleitschuh.gradle.ktlint-idea") version "10.1.0"
}

allprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")

    repositories {
        mavenCentral()
    }

    ktlint {
        filter {
            exclude("*.kts")
            exclude("**/generated/**")
        }
    }
}

tasks {
    register<Exec>("lint") {
        commandLine = "./gradlew ktlintCheck".split(" ")
    }
}

subprojects {
    apply(plugin = "kotlin")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")

    group = "com.dailyview"
    version = "1.0-SNAPSHOT"
    java.sourceCompatibility = JavaVersion.VERSION_11

    repositories {
        mavenCentral()
    }

    ext {
        set("logstashLogbackVersion", "6.4")
    }
    val logbackVersion = "1.2.0"
    val logbackJsonVersion = "0.1.5"
    val kotestVersion = "4.6.0"
    val jacksonVersion = "2.12.1"

    dependencies {
        implementation(kotlin("stdlib-jdk8"))
        implementation(kotlin("reflect"))
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin:${jacksonVersion}")
        implementation("com.fasterxml.jackson.core:jackson-databind:${jacksonVersion}")
        implementation("com.fasterxml.jackson.core:jackson-core:${jacksonVersion}")
        implementation("com.fasterxml.jackson.core:jackson-annotations:${jacksonVersion}")
        implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:${jacksonVersion}")
        implementation("org.springframework.boot:spring-boot-starter-data-redis")
        implementation("ch.qos.logback:logback-core:${logbackVersion}")
        implementation("ch.qos.logback:logback-classic:${logbackVersion}")
        implementation("ch.qos.logback:logback-access:${logbackVersion}")
        implementation("ch.qos.logback.contrib:logback-jackson:${logbackJsonVersion}")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("io.kotest:kotest-runner-junit5:${kotestVersion}")
        testImplementation("io.kotest:kotest-assertions-core:${kotestVersion}")
        testImplementation("io.kotest:kotest-property:${kotestVersion}")
        testImplementation("io.kotest:kotest-framework-datatest:${kotestVersion}")
        testImplementation("io.kotest:kotest-extensions-spring:4.4.3")
        testImplementation("io.mockk:mockk:1.12.1")
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "11"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

