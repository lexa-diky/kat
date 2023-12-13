package io.github.lexadiky.kat.buildlogic

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.withType

class KatConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.plugins.apply("org.jetbrains.kotlin.jvm")

        target.group = "io.github.lexa-diky"
        target.version = "1.0-SNAPSHOT"

        target.tasks.withType<Test> {
            useJUnitPlatform()
        }
    }
}