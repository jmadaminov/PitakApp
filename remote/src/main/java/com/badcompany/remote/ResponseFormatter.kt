package com.badcompany.remote

import com.badcompany.core.ResponseError
import com.badcompany.core.ResponseSuccess
import com.badcompany.core.ResponseWrapper
import com.badcompany.remote.model.RespFormatter

object ResponseFormatter {

    suspend fun <T> getFormattedResponse(action: suspend () -> RespFormatter<T>): ResponseWrapper<T> {
        return try {
            val resp = action()
            when {
                resp.code == 1 && resp.data != null -> ResponseSuccess(resp.data)
                else -> ResponseError(resp.message, resp.code)
            }
        } catch (e: Exception) {
            ResponseError(message = e.localizedMessage)
        }
    }


    suspend fun <T> getFormattedResponseNullable(action: suspend () -> RespFormatter<T>): ResponseWrapper<T?> {
        return try {
            val resp = action()
            when {
                resp.code == 1 && resp.data != null -> ResponseSuccess(resp.data)
                resp.code == 1 && resp.data == null -> ResponseSuccess(resp.data)
                else -> ResponseError(resp.message, resp.code)
            }
        } catch (e: Exception) {
            ResponseError(message = e.localizedMessage)
        }
    }

}