package io.github.lexadiky.kat.sdk.dsl.context

import org.jetbrains.kotlin.fir.FirElement

class KatExecutionContext<E: FirElement>(
    val element: E
) {

    interface Owner<E: FirElement> {
        val context: KatExecutionContext<E>
    }
}