package io.github.lexadiky.myapp

fun main() {
    println("hello world")
}

class StringRenderer(
    private val text: String
) : Renderer<String> {

    override fun render(): String = "str($text)"
}

interface Renderer<T> {

    fun render(): T
}

class PikaChu {

}
