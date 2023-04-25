group = "su.skaard.core"
version = rootProject.version

val kotlinVersion: String by project
val springBootVersion: String by project
val kordVersion:String by project

plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    kotlin("plugin.jpa")
    id("io.spring.dependency-management")
}

repositories {
    mavenCentral()
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.boot:spring-boot-dependencies:$springBootVersion")
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")

    implementation("org.springframework.security:spring-security-oauth2-client:6.0.3")

    implementation("dev.kord:kord-core:$kordVersion")

    implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")

    testImplementation("io.mockk:mockk-jvm:1.13.2")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}