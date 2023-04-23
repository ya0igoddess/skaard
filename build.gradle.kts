import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.7.11"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    kotlin("jvm") version "1.7.10"
    kotlin("plugin.spring") version "1.7.10"
}

group = "su.skaard"
version = "0.0.1-SNAPSHOT"
description = "skaard"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.7.11")
    implementation("org.springframework.boot:spring-boot-starter-web:2.7.11")
    implementation("org.springframework.boot:spring-boot-starter-security:2.7.6")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.4")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.7.10")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.7.10")
    implementation("org.springframework.security:spring-security-oauth2-client:5.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.8.0")
    runtimeOnly("org.springframework.boot:spring-boot-devtools:2.7.6")
    runtimeOnly("org.postgresql:postgresql:42.3.8")

    implementation("dev.kord:kord-core:0.8.0-M15")

    testImplementation("io.mockk:mockk-jvm:1.13.2")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}



