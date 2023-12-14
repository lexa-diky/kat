package io.github.lexadiky.kat.sdk.dsl.property.assertion

import io.github.lexadiky.kat.sdk.dsl.assertion.DefaultNodeAssertion
import io.github.lexadiky.kat.sdk.dsl.assertion.NodeAssertionCollector
import io.github.lexadiky.kat.sdk.dsl.context.KatExecutionContext
import io.github.lexadiky.kat.sdk.dsl.property.NodeProperty
import org.jetbrains.kotlin.fir.FirElement
import org.jetbrains.kotlin.fir.declarations.FirDeclaration

typealias StringNodeProperty<T> = NodeProperty<T, String>

context(NodeAssertionCollector, KatExecutionContext.Owner<T>)
infix fun <T : FirDeclaration> StringNodeProperty<T>.like(pattern: String) = emit(
    DefaultNodeAssertion<String>(
        element = context.element,
        property = this@StringNodeProperty,
        description = "should match '$pattern'",
        check = { extractor(context.element).matches(pattern.toRegex()) },
        actual = { extractor(context.element) }
    )
)

context(NodeAssertionCollector, KatExecutionContext.Owner<T>)
infix fun <T : FirDeclaration> StringNodeProperty<T>.startsWith(pattern: String) = emit(
    DefaultNodeAssertion<String>(
        element = context.element,
        property = this@StringNodeProperty,
        description = "should start with '$pattern'",
        check = { extractor(context.element).startsWith(pattern) },
        actual = { extractor(context.element) }
    )
)

