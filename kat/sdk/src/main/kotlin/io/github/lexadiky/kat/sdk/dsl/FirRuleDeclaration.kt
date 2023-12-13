package io.github.lexadiky.kat.sdk.dsl

import io.github.lexadiky.kat.sdk.dsl.assertion.AssertionResult
import io.github.lexadiky.kat.sdk.dsl.assertion.NodeAssertion
import io.github.lexadiky.kat.sdk.dsl.context.KatExecutionContext
import io.github.lexadiky.kat.sdk.dsl.nodes.AbstractFilterNode
import io.github.lexadiky.kat.sdk.dsl.nodes.ClassFilterNode
import io.github.lexadiky.kat.sdk.dsl.nodes.FileFilterNode
import io.github.lexadiky.kat.sdk.impl.KatFirErrors
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter
import org.jetbrains.kotlin.diagnostics.reportOn
import org.jetbrains.kotlin.fir.FirElement
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirClassChecker
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirFileChecker
import org.jetbrains.kotlin.fir.declarations.FirClass
import org.jetbrains.kotlin.fir.declarations.FirDeclaration
import org.jetbrains.kotlin.fir.declarations.FirFile
import org.jetbrains.kotlin.fir.declarations.FirRegularClass

class FirRuleDeclaration : FileNodeCollector, ClassNodeCollector {
    internal var firFileCheckers: MutableSet<FirFileChecker> = HashSet()
    internal var firClassCheckers: MutableSet<FirClassChecker> = HashSet()

    override fun collectFile(
        factory: (KatExecutionContext<FirFile>) -> FileFilterNode,
        configuration: FileFilterNode.() -> Unit
    ) {
        firFileCheckers += wrap(factory, configuration)
    }

    override fun collectClass(
        factory: (KatExecutionContext<FirClass>) -> ClassFilterNode,
        configuration: ClassFilterNode.() -> Unit
    ) {
        firClassCheckers += wrap(factory, configuration)
    }

    private fun <E : FirDeclaration, N : AbstractFilterNode<E, *>> wrap(
        factory: (KatExecutionContext<E>) -> N,
        configuration: N.() -> Unit
    ): FirDeclarationChecker<E> {
        return object : FirDeclarationChecker<E>() {
            override fun check(declaration: E, context: CheckerContext, reporter: DiagnosticReporter) {
                val context = KatExecutionContext(declaration, context, reporter)
                val executionResult = factory(context)
                    .apply(configuration)
                    .executeFilter()

                context.reporterService.reportIfRequired(executionResult)
            }
        }
    }


}