package io.github.lexadiky.kat.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.jvm.tasks.Jar
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class KatGradlePlugin : Plugin<Project> {

    override fun apply(target: Project) {
        val katImplementationConfiguration = target.configurations
            .create("katImplementation")

        target.tasks.create("kat") {
            println(target)
        }

        target.configure<SourceSetContainer> {
            val katSourceSet = create("kat") {
                compileClasspath += katImplementationConfiguration.artifacts.files + resources
                runtimeClasspath += katImplementationConfiguration.artifacts.files + resources
            }


            target.dependencies {
                add(
                    configurationName = "kotlinCompilerPluginClasspathMain",
                    dependencyNotation = katSourceSet.output
                        .filter { it.parentFile.name != "java" }
                )
            }
        }

        target.dependencies {
            // TODO replace with real dependency\
            add(
                configurationName = "katImplementation",
                dependencyNotation = target.subprojects.last()
            )
        }

        target.dependencies {
            add(
                configurationName = "kotlinCompilerPluginClasspathMain",
                dependencyNotation = target.subprojects.first()
            )
        }
    }
}