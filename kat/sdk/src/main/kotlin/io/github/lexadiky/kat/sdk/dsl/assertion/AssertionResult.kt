package io.github.lexadiky.kat.sdk.dsl.assertion

import org.jetbrains.kotlin.fir.FirElement

sealed interface AssertionResult {
    object OK : AssertionResult
    object Failure : AssertionResult
}
