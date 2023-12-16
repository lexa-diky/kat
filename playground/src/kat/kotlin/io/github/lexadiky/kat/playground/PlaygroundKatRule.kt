package io.github.lexadiky.kat.playground

import io.github.lexadiky.kat.sdk.KatRule
import io.github.lexadiky.kat.sdk.dsl.nodes.file
import io.github.lexadiky.kat.sdk.dsl.nodes.regularClass
import io.github.lexadiky.kat.sdk.dsl.property.assertion.contains
import io.github.lexadiky.kat.sdk.dsl.property.assertion.hasSupertype
import io.github.lexadiky.kat.sdk.dsl.property.assertion.like

class PlaygroundKatRule : KatRule({
    file {
        name like "main.kt"
        validate {
            imports contains "java.io.File"
        }
    }
})

