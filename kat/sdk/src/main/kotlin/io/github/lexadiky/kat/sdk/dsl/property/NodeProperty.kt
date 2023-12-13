package io.github.lexadiky.kat.sdk.dsl.property

import org.jetbrains.kotlin.fir.FirElement

class NodeProperty<E: FirElement, T>(
    val name: String,
    val extractor: (E) -> T
)
