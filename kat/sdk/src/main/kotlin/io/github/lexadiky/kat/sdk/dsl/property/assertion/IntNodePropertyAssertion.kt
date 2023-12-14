package io.github.lexadiky.kat.sdk.dsl.property.assertion

import io.github.lexadiky.kat.sdk.dsl.assertion.DefaultNodeAssertion
import io.github.lexadiky.kat.sdk.dsl.assertion.NodeAssertion
import io.github.lexadiky.kat.sdk.dsl.assertion.NodeAssertionCollector
import io.github.lexadiky.kat.sdk.dsl.context.KatExecutionContext
import io.github.lexadiky.kat.sdk.dsl.property.NodeProperty
import org.jetbrains.kotlin.fir.FirElement
import org.jetbrains.kotlin.fir.declarations.FirDeclaration

typealias IntNodeProperty<T> = NodeProperty<T, Int>

context(NodeAssertionCollector, KatExecutionContext.Owner<T>)
infix fun <T : FirDeclaration> AnyNodeProperty<T, Int>.gt(value: Int): NodeAssertion<Int> {
    return emit(
        DefaultNodeAssertion<Int>(
            element = context.element,
            property = this@AnyNodeProperty,
            description = "should be grater than '$value'",
            check = { extractor(context.element) > value },
            actual = { "${extractor(context.element)}" }
        )
    )
}

context(NodeAssertionCollector, KatExecutionContext.Owner<T>)
infix fun <T : FirDeclaration> AnyNodeProperty<T, Int>.lt(value: Int): NodeAssertion<Int> {
    return emit(
        DefaultNodeAssertion<Int>(
            element = context.element,
            property = this@AnyNodeProperty,
            description = "should be less than '$value'",
            check = { extractor(context.element) < value },
            actual = { "${extractor(context.element)}" }
        )
    )
}