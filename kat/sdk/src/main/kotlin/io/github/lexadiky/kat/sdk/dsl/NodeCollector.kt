package io.github.lexadiky.kat.sdk.dsl

import io.github.lexadiky.kat.sdk.dsl.context.KatExecutionContext
import io.github.lexadiky.kat.sdk.dsl.nodes.FileFilterNode
import org.jetbrains.kotlin.fir.declarations.FirFile

interface FileNodeCollector {
    fun collect(
        factory: (KatExecutionContext<FirFile>) -> FileFilterNode,
        configuration: FileFilterNode.() -> Unit
    )
}