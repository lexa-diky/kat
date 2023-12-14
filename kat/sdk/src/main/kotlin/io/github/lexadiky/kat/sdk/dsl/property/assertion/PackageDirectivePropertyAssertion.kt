package io.github.lexadiky.kat.sdk.dsl.property.assertion

import io.github.lexadiky.kat.sdk.dsl.assertion.DefaultNodeAssertion
import io.github.lexadiky.kat.sdk.dsl.assertion.NodeAssertionCollector
import io.github.lexadiky.kat.sdk.dsl.context.KatExecutionContext
import io.github.lexadiky.kat.sdk.dsl.property.NodeProperty
import org.jetbrains.kotlin.fir.FirElement
import org.jetbrains.kotlin.fir.FirPackageDirective
import org.jetbrains.kotlin.fir.declarations.FirDeclaration

typealias PackageDirectiveProperty<T> = NodeProperty<T, FirPackageDirective>

context(NodeAssertionCollector, KatExecutionContext.Owner<T>)
infix fun <T : FirDeclaration> PackageDirectiveProperty<T>.startsWith(prefix: String) = emit(
    DefaultNodeAssertion<FirPackageDirective>(
        element = context.element,
        property = this@PackageDirectiveProperty,
        description = "should start with '$prefix'",
        check = { extractor(context.element).packageFqName.asString().startsWith(prefix) },
        actual = { extractor(context.element).packageFqName.asString() }
    )
)