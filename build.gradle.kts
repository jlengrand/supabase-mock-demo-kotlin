plugins {
    kotlin("jvm") version "1.9.0"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.8.22"

    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {

    implementation(platform("io.github.jan-tennert.supabase:bom:1.0.3"))
    implementation("io.github.jan-tennert.supabase:postgrest-kt"){
        exclude(group = "org.slf4j", module = "slf4j-api")
    }

    testImplementation(kotlin("test"))
    testImplementation("io.mockk:mockk:1.13.7")


    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    testImplementation("org.testcontainers:junit-jupiter:1.19.0")

    testImplementation("org.testcontainers:testcontainers:1.19.0")
    testImplementation("org.testcontainers:postgresql:1.19.0")

    testImplementation("org.postgresql:postgresql:42.3.1")

    testImplementation("io.ktor:ktor-client-core:2.3.4")
    testImplementation("io.ktor:ktor-client-cio:2.3.4")


}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}

application {
    mainClass.set("MainKt")
}