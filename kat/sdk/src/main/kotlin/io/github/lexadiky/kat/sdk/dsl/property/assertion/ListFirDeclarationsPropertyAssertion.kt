package io.github.lexadiky.kat.sdk.dsl.property.assertion

import io.github.lexadiky.kat.sdk.dsl.assertion.DefaultNodeAssertion
import io.github.lexadiky.kat.sdk.dsl.assertion.NodeAssertionCollector
import io.github.lexadiky.kat.sdk.dsl.context.KatExecutionContext
import io.github.lexadiky.kat.sdk.dsl.property.NodeProperty
import org.jetbrains.kotlin.fir.FirElement
import org.jetbrains.kotlin.fir.FirPackageDirective
import org.jetbrains.kotlin.fir.declarations.FirDeclaration

typealias ListFirDeclarationsProperty<T> = NodeProperty<T, List<FirDeclaration>>

context(NodeAssertionCollector, KatExecutionContext.Owner<T>)
infix fun <T : FirDeclaration> ListFirDeclarationsProperty<T>.size(value: Int) = emit(
    DefaultNodeAssertion<List<FirDeclaration>>(
        element = context.element,
        property = this@ListFirDeclarationsProperty,
        description = "has size `$value`",
        check = { extractor(context.element).size == value },
        actual = { "actual '${extractor(context.element).size}'" }
    )
)