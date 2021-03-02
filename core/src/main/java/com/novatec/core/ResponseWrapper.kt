package com.novatec.core

sealed class ResponseWrapper<out V>
data class ResponseError(val message: String? = null, val code: Int? = null) :
    ResponseWrapper<Nothing>()

data class ResponseSuccess<out V>(val value: V) : ResponseWrapper<V>()

