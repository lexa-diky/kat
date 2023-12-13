package io.github.lexadiky.kat.sdk.dsl.nodes

import io.github.lexadiky.kat.sdk.dsl.ClassNodeCollector
import io.github.lexadiky.kat.sdk.dsl.FileNodeCollector
import io.github.lexadiky.kat.sdk.dsl.context.KatExecutionContext
import org.jetbrains.kotlin.fir.declarations.FirClass
import org.jetbrains.kotlin.fir.declarations.utils.nameOrSpecialName

interface ClassNode : PropertyOwnerNode<FirClass> {
    val name get() = property("name") { it.nameOrSpecialName.asString() }
}

open class ClassFilterNode(context: KatExecutionContext<FirClass>) :
    AbstractFilterNode<FirClass, ClassValidateNode>(context, ::ClassValidateNode), ClassNode

class ClassValidateNode(context: KatExecutionContext<FirClass>) : AbstractValidateNode<FirClass>(context), ClassNode

fun ClassNodeCollector.regularClass(config: ClassFilterNode.() -> Unit) =
    collectClass(::ClassFilterNode, config)
