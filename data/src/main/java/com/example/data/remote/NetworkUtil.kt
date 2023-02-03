package com.example.data.remote

import com.example.data.AppException
import retrofit2.Response
import java.io.IOException

object NetworkUtil {

    suspend fun <T>processAPICall(call:suspend ()->Response<T>):T?{
        try {
            val response = call.invoke()

            if (response.isSuccessful) {
                return response.body()
            } else {
                throw AppException.NetworkException
            }
        } catch (t: Throwable) {
            if (t is IOException) {
                throw AppException.NetworkException
            } else {
                throw AppException.UnKnownException(t.message)
            }
        }
    }
}