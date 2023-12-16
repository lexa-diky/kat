package io.github.lexadiky.kat.sdk.dsl.property.assertion

import io.github.lexadiky.kat.sdk.dsl.assertion.DefaultNodeAssertion
import io.github.lexadiky.kat.sdk.dsl.assertion.NodeAssertion
import io.github.lexadiky.kat.sdk.dsl.assertion.NodeAssertionCollector
import io.github.lexadiky.kat.sdk.dsl.context.KatExecutionContext
import io.github.lexadiky.kat.sdk.dsl.property.NodeProperty
import org.jetbrains.kotlin.fir.declarations.FirDeclaration
import org.jetbrains.kotlin.fir.declarations.FirFile
import org.jetbrains.kotlin.fir.declarations.FirImport

typealias ImportsListProperty = NodeProperty<FirFile, List<FirImport>>

context(NodeAssertionCollector, KatExecutionContext.Owner<T>)
infix fun <T : FirDeclaration> AnyNodeProperty<T, List<FirImport>>.contains(import: String): NodeAssertion<List<FirImport>> {
    return emit(
        DefaultNodeAssertion<List<FirImport>>(
            element = context.element,
            property = this@AnyNodeProperty,
            description = "should contain import '$import'",
            check = {
                val imports = extractor(context.element)
                imports.any { it.importedFqName?.asString() == import }
            },
            actual = { "${extractor(context.element)}" }
        )
    )
}
