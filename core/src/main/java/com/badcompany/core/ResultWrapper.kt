package com.badcompany.core

sealed class ResultWrapper<out E, out V> {

    data class Success<out V>(val value: V) : ResultWrapper<Nothing, V>()



//    data class Error<out E>(val error: E) : ResultWrapper<E, Nothing>()
//    data class NetworkError<out E>(val error: E) : ResultWrapper<E, Nothing>()

//    companion object Factory {
//        inline fun <V> build(function: () -> V): ResultWrapper<Exception, V> = try {
//            Success(function.invoke())
//        } catch (e: Exception) {
//            Error(e)
//        }
//    }






  /*  return withContext(dispatcher) {
        try {
            ResultWrapper.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> ResultWrapper.NetworkError
                is HttpException -> {
                    val code = throwable.code()
                    val errorResponse = convertErrorBody(throwable)
                    ResultWrapper.GenericError(code, errorResponse)
                }
                else -> {
                    ResultWrapper.GenericError(null, null)
                }
            }
        }
    }*/

}

sealed class ErrorWrapper : ResultWrapper<ErrorWrapper, Nothing>(){
    data class ResponseError(val code: Int?=null, val message: String?=null) : ErrorWrapper()
    data class SystemError(val err: Exception) : ErrorWrapper()
}


