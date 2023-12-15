package io.github.lexadiky.kat.playground

import io.github.lexadiky.kat.sdk.KatRule
import io.github.lexadiky.kat.sdk.dsl.nodes.file
import io.github.lexadiky.kat.sdk.dsl.nodes.regularClass
import io.github.lexadiky.kat.sdk.dsl.property.assertion.hasSupertype
import io.github.lexadiky.kat.sdk.dsl.property.assertion.like

class PlaygroundKatRule : KatRule({
    file {
        name like "main.kt"
        validate {
            children.regularClass {
                any {
                    type hasSupertype "io.github.lexadiky.myapp.Renderer"
                    name like "Pika.*"
                }

                validate {

                    any {
                        name like ".*Renderer"
                    }
                }
            }
        }
    }
})

