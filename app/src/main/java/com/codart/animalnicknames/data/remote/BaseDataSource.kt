package com.codart.animalnicknames.data.remote

import android.util.Log
import com.codart.animalnicknames.utils.Resource
import retrofit2.Response

abstract class BaseDataSource {
    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                Log.d("Responce", "Success "+response.toString())
                val body = response.body()
                try {
                    if (body != null) return Resource.success(body)
                }
                catch (e: Exception)
                {
                    Log.e("Responce", e.toString())
                }
            }
            Log.e("Remote error", response.toString())
            Log.e("Remote error", response.raw().toString())
            return try {
                error(" ${response.code()} ${response.message()}",response.code())
            } catch (e:Exception) {
                error(e.message ?: e.toString())
            }
        } catch (e: Exception) {
            Log.e("Respoce", e.toString(),e.cause)
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String,code: Int): Resource<T> {
        if(code==403)
        {
            return Resource.error("Неверная електронная почта или пароль")
        }
        if(code==404)
        {
            return Resource.error("К сожалению, мы еще не нашли клички с такой заглавной буквой")
        }
        return Resource.error("Network call has failed for a following reason: $message")
    }
}