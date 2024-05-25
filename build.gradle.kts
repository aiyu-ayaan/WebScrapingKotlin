plugins {
    kotlin("jvm") version "1.9.23"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation ("com.squareup.retrofit2:retrofit:2.11.0")
    implementation ("com.squareup.retrofit2:converter-scalars:2.11.0")
    implementation ("org.jsoup:jsoup:1.17.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
}

tasks.test {
    useJUnitPlatform()
}