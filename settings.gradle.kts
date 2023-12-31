pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
    }
    plugins {
        id("org.jetbrains.kotlin.jvm") version "1.9.21"
        id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
        id("org.gradle.kotlin.kotlin-dsl") version "4.1.2"
    }
}

dependencyResolutionManagement {
    repositoriesMode = RepositoriesMode.FAIL_ON_PROJECT_REPOS
    repositories {
        mavenCentral()
        google()
    }
    versionCatalogs {
        create("libs") {
            from(files("libs.versions.toml"))
        }
    }
}

rootProject.name = "kotlin-architecture-testing"

includeBuild("./build-logic")
include(":kat:gradle", ":kat:k2", ":kat:sdk")