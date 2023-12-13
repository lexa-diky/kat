package io.github.lexadiky.kat.sdk.impl

import io.github.lexadiky.kat.sdk.dsl.FirRuleDeclaration
import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirClassChecker
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirFileChecker
import org.jetbrains.kotlin.fir.analysis.extensions.FirAdditionalCheckersExtension

internal class KatRuleAdditionalCheckersExtension(
    session: FirSession,
    declaration: FirRuleDeclaration
) : FirAdditionalCheckersExtension(session) {

    override val declarationCheckers: DeclarationCheckers = object : DeclarationCheckers() {
        override val fileCheckers: Set<FirFileChecker> get() = declaration.firFileCheckers
        override val classCheckers: Set<FirClassChecker> get() = declaration.firClassCheckers
    }
}