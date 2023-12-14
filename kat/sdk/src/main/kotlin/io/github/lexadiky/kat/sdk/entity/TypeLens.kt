package io.github.lexadiky.kat.sdk.entity

import io.github.lexadiky.kat.sdk.dsl.context.KatExecutionContext
import org.jetbrains.kotlin.fir.analysis.checkers.isSupertypeOf
import org.jetbrains.kotlin.fir.declarations.FirClass
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider
import org.jetbrains.kotlin.fir.resolve.providers.symbolProvider
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol
import org.jetbrains.kotlin.name.ClassId

class TypeLens(
    private val context: KatExecutionContext<FirClass>
) {
    fun isSubclassOf(qualifier: String): Boolean {
        val classId = ClassId.fromString(
            qualifier.replace(".", "/")
        )

        return context.context.session.symbolProvider
            .getRegularClassSymbol(classId)
            .isSupertypeOf(context.element.symbol, context.context.session)
    }

   private fun FirSymbolProvider.getRegularClassSymbol(classId: ClassId): FirRegularClassSymbol {
        return getClassLikeSymbolByClassId(classId) as FirRegularClassSymbol
    }
}