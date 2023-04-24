
plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    kotlin("jvm")
    kotlin("plugin.spring")
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.4")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.8.20")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.8.20")
    implementation("org.springframework.security:spring-security-oauth2-client:5.7.3")
    implementation("dev.kord:kord-core:0.8.0-M15")
    implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.8.0")

    runtimeOnly("org.springframework.boot:spring-boot-devtools:2.7.6")
    runtimeOnly("org.postgresql:postgresql:42.3.8")

    testImplementation("io.mockk:mockk-jvm:1.13.2")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

group = "su.skaard"
version = "0.0.1-SNAPSHOT"
description = "skaard"
java.sourceCompatibility = JavaVersion.VERSION_17
