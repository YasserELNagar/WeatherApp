package com.example.data.remote.interceptor

import com.example.data.BuildConfig
import com.example.data.Constns.AUTHORIZATION_HEADER
import com.example.data.Constns.LANG_HEADER
import okhttp3.Interceptor
import okhttp3.Response
import java.util.*
import javax.inject.Inject

class AppInterceptor @Inject constructor() :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val url = chain.request().url.newBuilder()
            .addQueryParameter(LANG_HEADER, getDefaultLanguage())
            .addQueryParameter(AUTHORIZATION_HEADER, BuildConfig.WEATHER_API_KEY?:"")
            .build()

        val request = chain.request().newBuilder().url(url).build()
        return chain.proceed(request)
    }

    private fun getDefaultLanguage():String{
        return Locale.getDefault().language
    }
}
