plugins {
    kotlin("jvm") version "1.9.21"
    id("io.github.lexadiky.kat")
}

group = "io.github.lexa-diky"
version = "1.0-SNAPSHOT"

kotlin {
    jvmToolchain(19)
}

dependencies {
    add("katImplementation", "com.google.code.gson:gson:2.10.1")
    add("katImplementation", "com.typesafe:config:1.4.2")
}
