package io.github.lexadiky.kat.sdk.dsl

import io.github.lexadiky.kat.sdk.dsl.context.KatExecutionContext
import io.github.lexadiky.kat.sdk.dsl.nodes.AbstractFilterNode
import io.github.lexadiky.kat.sdk.dsl.nodes.ClassFilterNode
import io.github.lexadiky.kat.sdk.dsl.nodes.FileFilterNode
import io.github.lexadiky.kat.sdk.dsl.nodes.FunctionFilterNode
import org.jetbrains.kotlin.fir.FirElement
import org.jetbrains.kotlin.fir.declarations.FirClass
import org.jetbrains.kotlin.fir.declarations.FirFile
import org.jetbrains.kotlin.fir.declarations.FirFunction

interface FileNodeCollector {

    fun collectFile(
        factory: (KatExecutionContext<FirFile>) -> FileFilterNode,
        configuration: FileFilterNode.() -> Unit
    )
}

interface ClassNodeCollector {

    fun collectClass(
        factory: (KatExecutionContext<FirClass>) -> ClassFilterNode,
        configuration: ClassFilterNode.() -> Unit
    )
}

interface FunctionNodeCollector {

    fun collectFunction(
        factory: (KatExecutionContext<FirFunction>) -> FunctionFilterNode,
        configuration: FunctionFilterNode.() -> Unit
    )
}
