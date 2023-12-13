package io.github.lexadiky.kat.playground

import io.github.lexadiky.kat.sdk.KatRule
import io.github.lexadiky.kat.sdk.dsl.nodes.file
import io.github.lexadiky.kat.sdk.dsl.nodes.regularClass
import io.github.lexadiky.kat.sdk.dsl.property.assertion.equal
import io.github.lexadiky.kat.sdk.dsl.property.assertion.startsWith

class PlaygroundKatRule : KatRule({
    file {
        name equal "main.kt"

        validate {
            this.regularClass {
                validate {
                    name equal "Liza"
                }
            }
        }
    }
})
