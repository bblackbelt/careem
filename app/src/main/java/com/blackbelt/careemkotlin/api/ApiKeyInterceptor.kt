package com.blackbelt.careemkotlin.api

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiKeyInterceptor @Inject constructor() : Interceptor {

    private var API_KEY = "aa44bddf5e400ed934a919db7eaf14db"

    override fun intercept(chain: Interceptor.Chain?): Response? {
        val original = chain?.request()
        val url = original?.url() ?: return chain?.proceed(chain.request())
        val urlBuilder = url.newBuilder()
        urlBuilder?.addQueryParameter("api_key", API_KEY)
        val requestBuilder = original.newBuilder()
        requestBuilder.url(urlBuilder.build())
        val request = requestBuilder.build()
        return chain.proceed(request)
    }

}