package io.github.lexadiky.kat.sdk.dsl

import io.github.lexadiky.kat.sdk.dsl.assertion.AssertionResult
import io.github.lexadiky.kat.sdk.dsl.assertion.NodeAssertion
import io.github.lexadiky.kat.sdk.dsl.context.KatExecutionContext
import io.github.lexadiky.kat.sdk.dsl.nodes.DefaultFilterNode
import io.github.lexadiky.kat.sdk.dsl.nodes.FileFilterNode
import io.github.lexadiky.kat.sdk.impl.KatFirErrors
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter
import org.jetbrains.kotlin.diagnostics.reportOn
import org.jetbrains.kotlin.fir.FirElement
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirDeclarationChecker
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirFileChecker
import org.jetbrains.kotlin.fir.declarations.FirDeclaration
import org.jetbrains.kotlin.fir.declarations.FirFile

class FirRuleDeclaration : FileNodeCollector {
    internal var firFileCheckers: MutableSet<FirFileChecker> = HashSet()

    override fun collect(
        factory: (KatExecutionContext<FirFile>) -> FileFilterNode,
        configuration: FileFilterNode.() -> Unit
    ) {
        firFileCheckers += wrap(factory, configuration)
    }

    private fun <E : FirDeclaration, N : DefaultFilterNode<E, *>> wrap(
        factory: (KatExecutionContext<E>) -> N,
        configuration: N.() -> Unit
    ): FirDeclarationChecker<E> {
        return object : FirDeclarationChecker<E>() {
            override fun check(declaration: E, context: CheckerContext, reporter: DiagnosticReporter) {
                val executionResult = factory(KatExecutionContext(declaration))
                    .apply(configuration)
                    .executeFilter()

                if (executionResult.validation != null) {
                    report(
                        reporter = reporter,
                        declaration = declaration,
                        context = context,
                        selector = executionResult.selector,
                        validation = executionResult.validation
                    )
                }
            }
        }
    }

    private fun report(
        reporter: DiagnosticReporter,
        declaration: FirDeclaration,
        context: CheckerContext,
        selector: Map<NodeAssertion<*>, AssertionResult>,
        validation: Map<NodeAssertion<*>, AssertionResult>
    ) {
        reporter.reportOn(
            declaration.source,
            KatFirErrors.CAUSE_VIOLATION_DEFAULT,
            render(selector),
            render(validation),
            context
        )
    }

    private fun render(result: Map<NodeAssertion<*>, AssertionResult>): String {
        return result.entries.joinToString(separator = " & ") { (assertion, result) ->
            "(${render(assertion, result)})"
        }
    }

    private fun render(assertion: NodeAssertion<*>, result: AssertionResult): String {
        return when (result) {
            is AssertionResult.Failure -> renderFailure(assertion)
            AssertionResult.OK -> renderFailure(assertion)
        }
    }

    private fun renderFailure(assertion: NodeAssertion<*>): String {
        val elementType = readableType(assertion.element)
        val propertyName = assertion.property.name
        val description = assertion.description
        val actual = assertion.actual()

        return "${elementType} ${propertyName} ${description}, $actual"
    }

    private fun readableType(element: FirElement): String = when (element) {
        is FirFile -> "file"
        else -> element.toString()
    }
}