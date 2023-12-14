package io.github.lexadiky.kat.sdk.dsl.context

import io.github.lexadiky.kat.sdk.dsl.reporter.ReporterService
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext
import org.jetbrains.kotlin.fir.declarations.FirDeclaration

data class KatExecutionContext<E: FirDeclaration>(
    val element: E,
    val context: CheckerContext,
    val reporter: DiagnosticReporter
) {

    val reporterService get() = ReporterService(element, context, reporter)

    interface Owner<E: FirDeclaration> {
        val context: KatExecutionContext<E>
    }
}