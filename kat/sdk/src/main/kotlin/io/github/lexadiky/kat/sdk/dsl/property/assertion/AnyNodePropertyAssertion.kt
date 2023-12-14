package io.github.lexadiky.kat.sdk.dsl.property.assertion

import io.github.lexadiky.kat.sdk.dsl.assertion.DefaultNodeAssertion
import io.github.lexadiky.kat.sdk.dsl.assertion.NodeAssertion
import io.github.lexadiky.kat.sdk.dsl.assertion.NodeAssertionCollector
import io.github.lexadiky.kat.sdk.dsl.context.KatExecutionContext
import io.github.lexadiky.kat.sdk.dsl.property.NodeProperty
import org.jetbrains.kotlin.fir.FirElement
import org.jetbrains.kotlin.fir.declarations.FirDeclaration

typealias AnyNodeProperty<T, V> = NodeProperty<T, V>

context(NodeAssertionCollector, KatExecutionContext.Owner<T>)
infix fun <T : FirDeclaration, V> AnyNodeProperty<T, V>.equal(value: V): NodeAssertion<V> {
    return emit(
        DefaultNodeAssertion<V>(
            element = context.element,
            property = this@AnyNodeProperty,
            description = "should be equal to '$value'",
            check = { extractor(context.element) == value },
            actual = { "${extractor(context.element)}" }
        )
    )
}

context(NodeAssertionCollector, KatExecutionContext.Owner<T>)
infix fun <T : FirDeclaration, V> AnyNodeProperty<T, V>.notEqual(value: V): NodeAssertion<V> {
    return emit(
        DefaultNodeAssertion<V>(
            element = context.element,
            property = this@AnyNodeProperty,
            description = "should be not equal to '$value'",
            check = { extractor(context.element) != value },
            actual = { "${extractor(context.element)}" }
        )
    )
}
