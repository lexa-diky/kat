package io.github.lexadiky.kat.buildlogic

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

class KatConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.plugins.apply("org.jetbrains.kotlin.jvm")

        target.group = "io.github.lexa-diky"
        target.version = "1.0-SNAPSHOT"

        target.tasks.withType<Test> {
            useJUnitPlatform()
        }

        target.extensions.configure<KotlinJvmProjectExtension>() {
            jvmToolchain(19)
        }
    }
}