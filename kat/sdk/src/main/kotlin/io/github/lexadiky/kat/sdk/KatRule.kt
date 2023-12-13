package io.github.lexadiky.kat.sdk

import io.github.lexadiky.kat.sdk.dsl.FirRuleDeclaration
import io.github.lexadiky.kat.sdk.impl.KatRuleAdditionalCheckersExtension
import org.jetbrains.kotlin.compiler.plugin.CompilerPluginRegistrar
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.fir.analysis.extensions.FirAdditionalCheckersExtension
import org.jetbrains.kotlin.fir.extensions.FirExtensionRegistrar
import org.jetbrains.kotlin.fir.extensions.FirExtensionRegistrarAdapter

@OptIn(ExperimentalCompilerApi::class)
abstract class KatRule(
    private val declare: FirRuleDeclaration.() -> Unit
) : CompilerPluginRegistrar() {

    override val supportsK2: Boolean = true

    override fun ExtensionStorage.registerExtensions(configuration: CompilerConfiguration) {
        FirExtensionRegistrarAdapter.registerExtension(Fir(configuration))
    }

    internal class Fir(
        private val configuration: CompilerConfiguration
    ) : FirExtensionRegistrar() {

        override fun ExtensionRegistrarContext.configurePlugin() {
            +FirAdditionalCheckersExtension.Factory { session ->
                KatRuleAdditionalCheckersExtension(session)
            }
        }
    }
}