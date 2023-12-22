package io.github.lexadiky.kat.sdk.dsl.property.assertion

import io.github.lexadiky.kat.sdk.dsl.assertion.DefaultNodeAssertion
import io.github.lexadiky.kat.sdk.dsl.assertion.NodeAssertion
import io.github.lexadiky.kat.sdk.dsl.assertion.NodeAssertionCollector
import io.github.lexadiky.kat.sdk.dsl.context.KatExecutionContext
import io.github.lexadiky.kat.sdk.dsl.nodes.AbstractValidateNode

context(NodeAssertionCollector, KatExecutionContext.Owner<*>)
fun AbstractValidateNode<*>.doesNotExists(): NodeAssertion<AbstractValidateNode<*>> {
    return emit(
        DefaultNodeAssertion<AbstractValidateNode<*>>(
            element = context.element,
            property = property("instance") { this@doesNotExists },
            description = "should not exist",
            check = { false },
            actual = { "exists" }
        )
    )
}

