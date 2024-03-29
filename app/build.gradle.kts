group = rootProject.group
version = rootProject.version
java.sourceCompatibility = JavaVersion.VERSION_17

val kordVersion: String by project

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
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.4")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.8.20")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.8.20")
    implementation("dev.kord:kord-core:$kordVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.8.0")

    //skaard dependencies
    implementation(project(":core"))
    //implementation(project(":channel-presence"))

    //runtimeOnly("org.springframework.boot:spring-boot-devtools:2.7.6")
    runtimeOnly("org.postgresql:r2dbc-postgresql:1.0.1.RELEASE")

    testImplementation("io.mockk:mockk-jvm:1.13.2")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.test {
    useJUnitPlatform()
}


