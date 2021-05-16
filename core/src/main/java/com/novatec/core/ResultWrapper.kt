package com.novatec.core

sealed class ResultWrapper<out V> {
    data class Success<out V>(val value: V) : ResultWrapper<V>()
    object InProgress : ResultWrapper<Nothing>()
}

sealed class ErrorWrapper : ResultWrapper<Nothing>() {
    data class RespError(val code: Int? = null, val message: String? = null) : ErrorWrapper()
    data class SystemError(val err: Throwable) : ErrorWrapper()
}





