package io.github.lexadiky.kat.sdk.dsl.nodes

import io.github.lexadiky.kat.sdk.dsl.context.KatExecutionContext
import io.github.lexadiky.kat.sdk.dsl.property.NodeProperty
import org.jetbrains.kotlin.fir.FirElement
import org.jetbrains.kotlin.fir.declarations.FirDeclaration

interface PropertyOwnerNode<E : FirDeclaration> {

    fun <T> property(name: String, extractor: (E) -> T): NodeProperty<E, T>

    fun <T> contextProperty(name: String, extractor: (KatExecutionContext<E>) -> T): NodeProperty<E, T>
}
