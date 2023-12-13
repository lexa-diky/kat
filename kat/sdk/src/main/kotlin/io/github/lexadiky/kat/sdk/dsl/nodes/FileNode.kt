package io.github.lexadiky.kat.sdk.dsl.nodes

import io.github.lexadiky.kat.sdk.dsl.ClassNodeCollector
import io.github.lexadiky.kat.sdk.dsl.FileNodeCollector
import io.github.lexadiky.kat.sdk.dsl.context.KatExecutionContext
import org.jetbrains.kotlin.fir.FirElement
import org.jetbrains.kotlin.fir.declarations.FirClass
import org.jetbrains.kotlin.fir.declarations.FirDeclaration
import org.jetbrains.kotlin.fir.declarations.FirFile
import org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
import org.jetbrains.org.objectweb.asm.tree.ClassNode
import kotlin.reflect.KClass

interface FileNode : PropertyOwnerNode<FirFile> {
    val name get() = property("name") { it.name }
    val packageDirective get() = property("package") { it.packageDirective }
    val declarations get() = property("declarations") { it.declarations }
}

open class FileFilterNode(context: KatExecutionContext<FirFile>) :
    AbstractFilterNode<FirFile, FileValidateNode>(context, ::FileValidateNode), FileNode

class FileValidateNode(context: KatExecutionContext<FirFile>) :
    AbstractValidateNode<FirFile>(context),
    FileNode,
    ClassNodeCollector {

    override fun collectClass(
        factory: (KatExecutionContext<FirClass>) -> ClassFilterNode,
        configuration: ClassFilterNode.() -> Unit
    ) {
        collectNode(FirClass::class, factory, configuration)
    }
}

fun FileNodeCollector.file(config: FileFilterNode.() -> Unit) =
    collectFile(::FileFilterNode, config)