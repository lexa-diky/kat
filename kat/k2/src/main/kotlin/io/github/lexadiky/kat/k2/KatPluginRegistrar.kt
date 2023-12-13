@file:OptIn(ExperimentalCompilerApi::class)

package io.github.lexadiky.kat.k2

import io.github.lexadiky.kat.sdk.KatRule
import org.jetbrains.kotlin.cli.jvm.config.jvmClasspathRoots
import org.jetbrains.kotlin.compiler.plugin.CompilerPluginRegistrar
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.ir.backend.js.resolverLogger
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.ServiceLoader


class KatPluginRegistrar : CompilerPluginRegistrar() {

    override val supportsK2: Boolean = true

    override fun ExtensionStorage.registerExtensions(configuration: CompilerConfiguration) {

        configuration.resolverLogger
            .warning(">>> AT HERE")

        ServiceLoader.load(KatRule::class.java).forEach {
            configuration.resolverLogger
                .warning(">>> $it")
        }

        val classLoader = Thread.currentThread().contextClassLoader
        classLoader.resources("META-INF/services/io.github.lexadiky.kat.sdk.KatRule").forEach { file ->

        }
    }
}