package io.github.lexadiky.kat.sdk.dsl.nodes

import io.github.lexadiky.kat.sdk.dsl.FileNodeCollector
import io.github.lexadiky.kat.sdk.dsl.context.KatExecutionContext
import org.jetbrains.kotlin.fir.declarations.FirFile

interface FileNode : PropertyOwnerNode<FirFile> {
    val name get() = property("name") { it.name }
    val packageDirective get() = property("package") { it.packageDirective }
}

open class FileFilterNode(context: KatExecutionContext<FirFile>) :
    DefaultFilterNode<FirFile, FileValidateNode>(context, ::FileValidateNode), FileNode {

}

class FileValidateNode(context: KatExecutionContext<FirFile>) : DefaultValidateNode<FirFile>(context), FileNode {

}

fun FileNodeCollector.file(config: FileFilterNode.() -> Unit) =
    collect(::FileFilterNode, config)