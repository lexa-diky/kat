package io.github.lexadiky.kat.sdk.dsl.property

import org.jetbrains.kotlin.fir.declarations.FirDeclaration

class NodeProperty<E: FirDeclaration, T>(
    val name: String,
    val extractor: (E) -> T
)
