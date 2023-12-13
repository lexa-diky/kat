package io.github.lexadiky.kat.sdk.impl

import org.jetbrains.kotlin.com.intellij.psi.PsiElement
import org.jetbrains.kotlin.diagnostics.KtDiagnosticFactoryToRendererMap
import org.jetbrains.kotlin.diagnostics.KtDiagnosticRenderers
import org.jetbrains.kotlin.diagnostics.error2
import org.jetbrains.kotlin.diagnostics.rendering.BaseDiagnosticRendererFactory
import org.jetbrains.kotlin.diagnostics.rendering.RootDiagnosticRendererFactory

object KatFirErrors {

    val CAUSE_VIOLATION_DEFAULT by error2<PsiElement, String, String>()

    object Messages : BaseDiagnosticRendererFactory() {

        override val MAP: KtDiagnosticFactoryToRendererMap = KtDiagnosticFactoryToRendererMap("Kat").also { map ->
            map.put(
                CAUSE_VIOLATION_DEFAULT,
                "\nselector: {0}\nviolates: {1}",
                KtDiagnosticRenderers.TO_STRING,
                KtDiagnosticRenderers.TO_STRING
            )
        }
    }

    init {
        RootDiagnosticRendererFactory.registerFactory(Messages)
    }
}