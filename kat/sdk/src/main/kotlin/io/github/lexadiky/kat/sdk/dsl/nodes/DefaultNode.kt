package io.github.lexadiky.kat.sdk.dsl.nodes

import io.github.lexadiky.kat.sdk.dsl.assertion.AssertionResult
import io.github.lexadiky.kat.sdk.dsl.assertion.DefaultNodeAssertion
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

    protected var anyAssertionBlock: Boolean = false
    protected val anyAssertionBuffer: MutableList<NodeAssertion<*>> = ArrayList()

    private val propertyCache: MutableMap<String, NodeProperty<E, *>> = HashMap()

    override fun <T> emit(assertion: NodeAssertion<T>): NodeAssertion<T> {
        if (anyAssertionBlock) {
            anyAssertionBuffer += assertion
        } else {
            assertions += assertion
        }
        return assertion
    }

    private fun remove(assertion: NodeAssertion<*>) {
        assertions -= assertion
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T> property(name: String, extractor: (E) -> T): NodeProperty<E, T> =
        propertyCache.computeIfAbsent(name) { NodeProperty(name, extractor) }
                as NodeProperty<E, T>

    override fun <T> contextProperty(name: String, extractor: (KatExecutionContext<E>) -> T): NodeProperty<E, T> =
        propertyCache.computeIfAbsent(name) { NodeProperty(name) { _ -> extractor(context) } }
                as NodeProperty<E, T>

    fun execute(): Map<NodeAssertion<*>, AssertionResult> {
        return assertions.associateWith { it.execute() }
    }

    fun any(block: () -> Unit) {
        anyAssertionBlock = true
        block()
        anyAssertionBlock = false
        emit(DefaultNodeAssertion(
            element = context.element,
            property = property("*") { context.element },
            description = "any(${anyAssertionBuffer.joinToString { "${it.property.name} ${it.description}" }})",
            check = { anyAssertionBuffer.any { it.check() } },
            actual = { "any(${anyAssertionBuffer.joinToString { it.actual() }})" }
        ))
    }

    infix fun NodeAssertion<*>.or(other: NodeAssertion<*>): NodeAssertion<*> {
        remove(this)
        remove(other)

        return emit(DefaultNodeAssertion(
            element = context.element,
            property = property("*") { context.element },
            description = "or(${this.property.name} ${this.description}, ${other.property.name} ${other.description})",
            check = { this.check() || other.check() },
            actual = { "or(${this.actual()}, ${other.actual()})" }
        ))
    }

    @Suppress("UNCHECKED_CAST")
    protected fun <T : FirDeclaration, N : AbstractFilterNode<T, *>> collectNode(
        type: KClass<T>,
        factory: (KatExecutionContext<T>) -> N,
        configuration: N.() -> Unit
    ) {
        val buffer = ArrayList<T>()

        context.element.acceptChildren(object : FirVisitorVoid() {
            override fun visitElement(element: FirElement) {
                if (type.isInstance(element) && element is FirDeclaration) {
                    buffer.add(element as T)
                }
            }
        })

        buffer.forEach { element ->
            val newContext = (context as KatExecutionContext<T>)
                .copy(element = element as T)

            val result = factory(newContext)
                .apply(configuration)
                .executeFilter()

            newContext.reporterService.reportIfRequired(result)
        }
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