package io.github.lexadiky.kat.sdk.dsl.nodes

import io.github.lexadiky.kat.sdk.dsl.FunctionNodeCollector
import io.github.lexadiky.kat.sdk.dsl.KatDslMarker
import io.github.lexadiky.kat.sdk.dsl.context.KatExecutionContext
import io.github.lexadiky.kat.sdk.entity.FunctionType
import org.jetbrains.kotlin.fir.declarations.FirClass
import org.jetbrains.kotlin.fir.declarations.FirFunction
import org.jetbrains.kotlin.fir.declarations.utils.nameOrSpecialName

interface FunctionNode : PropertyOwnerNode<FirFunction> {
    val name get() = property("name") { it.nameOrSpecialName.asString() }
    val type get() = property("type") { FunctionType.from(it) }
}

@KatDslMarker
open class FunctionFilterNode(context: KatExecutionContext<FirFunction>) :
    AbstractFilterNode<FirFunction, FunctionValidateNode>(context, ::FunctionValidateNode), FunctionNode

@KatDslMarker
class FunctionValidateNode(context: KatExecutionContext<FirFunction>) : AbstractValidateNode<FirFunction>(context),
    FunctionNode

fun FunctionNodeCollector.function(config: FunctionFilterNode.() -> Unit) =
    collectFunction(::FunctionFilterNode, config)
