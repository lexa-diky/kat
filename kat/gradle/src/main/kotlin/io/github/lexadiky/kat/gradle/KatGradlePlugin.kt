package io.github.lexadiky.kat.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class KatGradlePlugin : Plugin<Project> {

    override fun apply(target: Project) {
        val katImplementationConfiguration = target.configurations
            .create("katImplementation")

        target.tasks.create("kat") {
            println(target)
        }

        target.configure<SourceSetContainer> {
            create("kat") {
                compileClasspath += katImplementationConfiguration.artifacts.files + resources
                runtimeClasspath += katImplementationConfiguration.artifacts.files + resources
            }
        }

        target.dependencies {
            // TODO replace with real dependency\
            add("katImplementation", target.subprojects.first())
        }
    }
}