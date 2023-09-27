plugins {
    kotlin("jvm") version "1.9.0"
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