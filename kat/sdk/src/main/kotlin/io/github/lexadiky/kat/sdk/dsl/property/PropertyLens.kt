package io.github.lexadiky.kat.sdk.dsl.property

import org.jetbrains.kotlin.fir.declarations.FirDeclaration

fun <E: FirDeclaration, T, V> NodeProperty<E, T>.lens(name: String, fn: (T) -> V): NodeProperty<E, V> {
    return NodeProperty(
        name = "${this.name}.$name",
        extractor = { fn(extractor(it)) }
    )
}
