package io.github.lexadiky.kat.playground

import io.github.lexadiky.kat.sdk.KatRule
import io.github.lexadiky.kat.sdk.dsl.nodes.file
import io.github.lexadiky.kat.sdk.dsl.property.assertion.like
import io.github.lexadiky.kat.sdk.dsl.property.assertion.startsWith

class PlaygroundKatRule : KatRule({
    file {
        name like "main.kt"

        validate {
            packageDirective startsWith "com.github"
        }
    }
})
