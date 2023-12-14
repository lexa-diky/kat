package io.github.lexadiky.kat.sdk.dsl.property.assertion

import io.github.lexadiky.kat.sdk.dsl.assertion.DefaultNodeAssertion
import io.github.lexadiky.kat.sdk.dsl.assertion.NodeAssertionCollector
import io.github.lexadiky.kat.sdk.dsl.context.KatExecutionContext
import io.github.lexadiky.kat.sdk.dsl.property.NodeProperty
import io.github.lexadiky.kat.sdk.entity.TypeLens
import org.jetbrains.kotlin.fir.FirElement
import org.jetbrains.kotlin.fir.FirPackageDirective
import org.jetbrains.kotlin.fir.declarations.FirDeclaration

typealias TypeLensDeclarationsProperty<T> = NodeProperty<T, TypeLens>

context(NodeAssertionCollector, KatExecutionContext.Owner<T>)
infix fun <T : FirDeclaration> TypeLensDeclarationsProperty<T>.hasSupertype(qualifier: String) = emit(
    DefaultNodeAssertion<TypeLens>(
        element = context.element,
        property = this@TypeLensDeclarationsProperty,
        description = "is subtype of '$qualifier'",
        check = { extractor(context.element).isSubclassOf(qualifier) },
        actual = { "" } // TODO add actual
    )
)