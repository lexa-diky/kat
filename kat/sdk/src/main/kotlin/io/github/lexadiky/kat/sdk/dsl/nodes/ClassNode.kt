package io.github.lexadiky.kat.sdk.dsl.nodes

import io.github.lexadiky.kat.sdk.dsl.ClassNodeCollector
import io.github.lexadiky.kat.sdk.dsl.FunctionNodeCollector
import io.github.lexadiky.kat.sdk.dsl.KatDslMarker
import io.github.lexadiky.kat.sdk.dsl.context.KatExecutionContext
import io.github.lexadiky.kat.sdk.entity.TypeLens
import org.jetbrains.kotlin.fir.declarations.FirClass
import org.jetbrains.kotlin.fir.declarations.FirFunction
import org.jetbrains.kotlin.fir.declarations.utils.classId
import org.jetbrains.kotlin.fir.declarations.utils.nameOrSpecialName

interface ClassNode : PropertyOwnerNode<FirClass> {
    val name get() = property("name") { it.nameOrSpecialName.asString() }
    val qualifier get() = property("qualifier") { it.classId.asFqNameString() }
    val packageQualifier get() = property("package") { it.classId.packageFqName.asString() }
    val type get() = contextProperty("type") { TypeLens(it) }
}

@KatDslMarker
open class ClassFilterNode(context: KatExecutionContext<FirClass>) :
    AbstractFilterNode<FirClass, ClassValidateNode>(context, ::ClassValidateNode), ClassNode

@KatDslMarker
class ClassValidateNode(context: KatExecutionContext<FirClass>) : AbstractValidateNode<FirClass>(context), ClassNode {
    val children = object : FunctionNodeCollector {
        override fun collectFunction(
            factory: (KatExecutionContext<FirFunction>) -> FunctionFilterNode,
            configuration: FunctionFilterNode.() -> Unit
        ) = collectNode(FirFunction::class, factory, configuration)
    }
}

fun ClassNodeCollector.regularClass(config: ClassFilterNode.() -> Unit) =
    collectClass(::ClassFilterNode, config)
