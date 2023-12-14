package io.github.lexadiky.kat.sdk.dsl.reporter

import io.github.lexadiky.kat.sdk.dsl.assertion.AssertionResult
import io.github.lexadiky.kat.sdk.dsl.assertion.NodeAssertion
import io.github.lexadiky.kat.sdk.dsl.nodes.AbstractFilterNode
import io.github.lexadiky.kat.sdk.impl.KatFirErrors
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter
import org.jetbrains.kotlin.diagnostics.reportOn
import org.jetbrains.kotlin.fir.FirElement
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext
import org.jetbrains.kotlin.fir.declarations.FirConstructor
import org.jetbrains.kotlin.fir.declarations.FirDeclaration
import org.jetbrains.kotlin.fir.declarations.FirFile
import org.jetbrains.kotlin.fir.declarations.FirFunction
import org.jetbrains.kotlin.fir.declarations.FirRegularClass

class ReporterService(
    private val declaration: FirDeclaration,
    private val context: CheckerContext,
    private val reporter: DiagnosticReporter
) {

    fun reportIfRequired(execution: AbstractFilterNode.FilterNodeExecution) {
        val failedValidation = execution.validation
            ?.filter { (_, r) -> r != AssertionResult.OK }
            .orEmpty()

        if (execution.validation != null && failedValidation.isNotEmpty()) {
            report(
                selector = execution.selector,
                validation = failedValidation
            )
        }
    }

    private fun report(
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
        if (result.isEmpty()) {
            return "any"
        }

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

        return "${elementType} ${propertyName} ${description}, actual $actual"
    }

    private fun readableType(element: FirElement): String = when (element) {
        is FirFile -> "file"
        is FirRegularClass -> "class"
        is FirConstructor -> "constructor"
        is FirFunction -> "function"
        else -> element.toString()
    }
}