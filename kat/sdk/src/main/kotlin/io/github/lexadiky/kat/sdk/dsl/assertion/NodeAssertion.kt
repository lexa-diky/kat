package io.github.lexadiky.kat.sdk.dsl.assertion

import io.github.lexadiky.kat.sdk.dsl.property.NodeProperty
import org.jetbrains.kotlin.fir.FirElement

interface NodeAssertion<T> {
    val element: FirElement
    val property: NodeProperty<*, T>
    val description: String
    val check: () -> Boolean
    val actual: () -> String
}

internal data class DefaultNodeAssertion<T>(
    override val element: FirElement,
    override val property: NodeProperty<*, T>,
    override val description: String,
    override val check: () -> Boolean,
    override val actual: () -> String
) : NodeAssertion<T>

fun NodeAssertion<*>.execute(): AssertionResult =
    if (check()) AssertionResult.OK else AssertionResult.Failure