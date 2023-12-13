plugins {
    kotlin("jvm") version "1.9.21"
    `kotlin-dsl`
}

group = "io.github.lexa-diky"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.10")
}

kotlin {
    jvmToolchain(19)
}

gradlePlugin {
    plugins {
        create("kat-convention") {
            id = name
            implementationClass = "io.github.lexadiky.kat.buildlogic.KatConventionPlugin"
        }
    }
}
