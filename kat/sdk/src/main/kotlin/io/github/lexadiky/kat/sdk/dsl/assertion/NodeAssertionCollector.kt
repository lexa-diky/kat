package io.github.lexadiky.kat.sdk.dsl.assertion

interface NodeAssertionCollector {

    fun <T> emit(assertion: NodeAssertion<T>): NodeAssertion<T>
}
