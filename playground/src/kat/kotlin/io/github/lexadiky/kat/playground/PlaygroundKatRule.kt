package io.github.lexadiky.kat.playground

import io.github.lexadiky.kat.sdk.KatRule
import io.github.lexadiky.kat.sdk.dsl.nodes.file
import io.github.lexadiky.kat.sdk.dsl.nodes.regularClass
import io.github.lexadiky.kat.sdk.dsl.property.assertion.equal

class PlaygroundKatRule : KatRule({
    file {
        validate {
            regularClass {
                validate {
                    name equal "PPPP"
                }
            }
        }
    }
})
