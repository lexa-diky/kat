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

interface FileNode : PropertyOwnerNode<FirFile> {
    val name get() = property("name") { it.name }
    val packageDirective get() = property("package") { it.packageDirective }
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
        context.element.acceptChildren(object : FirVisitorVoid() {
            override fun visitElement(element: FirElement) {
                if (element is FirClass) {
                    val newContext = (context as KatExecutionContext<FirDeclaration>)
                        .copy(element = element)
                    val result = factory(newContext as KatExecutionContext<FirClass>)
                        .apply(configuration)
                        .executeFilter()

                    newContext.reporterService.reportIfRequired(result)
                }
            }
        })
    }
}

fun FileNodeCollector.file(config: FileFilterNode.() -> Unit) =
    collectFile(::FileFilterNode, config)