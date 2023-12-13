plugins {
    kotlin("jvm") version "1.9.21"
    `kotlin-dsl`
}

group = "io.github.lexa-diky"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain(17)
}
