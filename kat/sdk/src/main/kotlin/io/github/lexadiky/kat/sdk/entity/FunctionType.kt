package io.github.lexadiky.kat.sdk.entity

import org.jetbrains.kotlin.fir.declarations.FirFunction
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction
import org.jetbrains.kotlin.fir.declarations.impl.FirPrimaryConstructor

enum class FunctionType {

    CONSTRUCTOR,
    MEMBER;

    companion object {

        fun from(fir: FirFunction): FunctionType = when(fir) {
            is FirPrimaryConstructor -> CONSTRUCTOR
            is FirSimpleFunction -> MEMBER
            else -> error(fir::class.java)
        }
    }
}