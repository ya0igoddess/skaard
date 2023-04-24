group = rootProject.group
version = rootProject.version
java.sourceCompatibility = JavaVersion.VERSION_17

plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    kotlin("plugin.jpa")
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
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.4")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.8.20")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.8.20")
    implementation("dev.kord:kord-core:0.8.0-M15")
    implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.8.0")

    //skaard dependencies
    implementation(project(":skaard-core"))

    runtimeOnly("org.springframework.boot:spring-boot-devtools:2.7.6")
    runtimeOnly("org.postgresql:postgresql:42.3.8")

    testImplementation("io.mockk:mockk-jvm:1.13.2")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}


