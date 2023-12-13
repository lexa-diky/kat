package io.github.lexadiky.kat.sdk.dsl.nodes

import io.github.lexadiky.kat.sdk.dsl.assertion.AssertionResult
import io.github.lexadiky.kat.sdk.dsl.assertion.NodeAssertion
import io.github.lexadiky.kat.sdk.dsl.assertion.NodeAssertionCollector
import io.github.lexadiky.kat.sdk.dsl.assertion.execute
import io.github.lexadiky.kat.sdk.dsl.context.KatExecutionContext
import io.github.lexadiky.kat.sdk.dsl.property.NodeProperty
import org.jetbrains.kotlin.fir.FirElement
import org.jetbrains.kotlin.fir.declarations.FirDeclaration
import org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
import kotlin.reflect.KClass

abstract class AbstractValidateNode<E : FirDeclaration>(
    override val context: KatExecutionContext<E>
) : PropertyOwnerNode<E>,
    NodeAssertionCollector,
    KatExecutionContext.Owner<E> {

    protected val assertions: MutableList<NodeAssertion<*>> = ArrayList()
    private val propertyCache: MutableMap<String, NodeProperty<E, *>> = HashMap()

    override fun <T> emit(assertion: NodeAssertion<T>): NodeAssertion<T> {
        assertions += assertion
        return assertion
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T> property(name: String, extractor: (E) -> T): NodeProperty<E, T> =
        propertyCache.computeIfAbsent(name) { NodeProperty(name, extractor) }
                as NodeProperty<E, T>

    fun execute(): Map<NodeAssertion<*>, AssertionResult> {
        return assertions.associateWith { it.execute() }
    }

    @Suppress("UNCHECKED_CAST")
    protected fun <T: FirDeclaration, N: AbstractFilterNode<T, *>> collectNode(
        type: KClass<T>,
        factory: (KatExecutionContext<T>) -> N,
        configuration: N.() -> Unit
    ) {
        context.element.acceptChildren(object : FirVisitorVoid() {
            override fun visitElement(element: FirElement) {
                if (type.isInstance(element) && element is FirDeclaration) {
                    val newContext = (context as KatExecutionContext<T>)
                        .copy(element = element as T)

                    val result = factory(newContext)
                        .apply(configuration)
                        .executeFilter()

                    newContext.reporterService.reportIfRequired(result)
                }
            }
        })
    }
}

abstract class AbstractFilterNode<E : FirDeclaration, V : AbstractValidateNode<E>>(
    context: KatExecutionContext<E>,
    private val pariValidateFactory: (KatExecutionContext<E>) -> V
) : AbstractValidateNode<E>(context) {
    private val validations: MutableSet<() -> Map<NodeAssertion<*>, AssertionResult>> = HashSet()

    fun validate(node: V.() -> Unit) {
        validations += {
            pariValidateFactory(context)
                .apply(node)
                .execute()
        }
    }

    fun executeFilter(): FilterNodeExecution {
        val filterResults = assertions.associateWith { it.execute() }
        if (filterResults.values.any { it != AssertionResult.OK }) {
            return FilterNodeExecution(
                selector = filterResults,
                validation = null
            )
        } else {
            return FilterNodeExecution(
                selector = filterResults,
                validation = validations.map { it() }
                    .fold(emptyMap()) { acc, a -> acc + a }
            )
        }
    }

    data class FilterNodeExecution(
        val selector: Map<NodeAssertion<*>, AssertionResult>,
        val validation: Map<NodeAssertion<*>, AssertionResult>?,
    )
}