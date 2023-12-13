package io.github.lexadiky.kat.sdk.dsl.nodes

import io.github.lexadiky.kat.sdk.dsl.property.NodeProperty
import org.jetbrains.kotlin.fir.FirElement

interface PropertyOwnerNode<E : FirElement> {

    fun <T> property(name: String, extractor: (E) -> T): NodeProperty<E, T>
}
