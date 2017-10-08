package com.blackbelt.careemkotlin.api

import com.blackbelt.careemkotlin.api.model.*
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiManager(baseUrl: String, gson: Gson, apiKeyInterceptor: ApiKeyInterceptor) : IApiManager {

    private var mApiService = getApiService(baseUrl, gson, apiKeyInterceptor)

    private fun getApiService(baseUrl: String, gson: Gson, apiKeyInterceptor: ApiKeyInterceptor): ApiService {
        val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(getHttpClient(apiKeyInterceptor))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build()
        return retrofit.create<ApiService>(ApiService::class.java)
    }

    private fun getHttpClient(apiKeyInterceptor: ApiKeyInterceptor): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor();
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC;
        return OkHttpClient.Builder()
                .addInterceptor(apiKeyInterceptor)
                .addInterceptor(httpLoggingInterceptor)
                .build()
    }

    override fun discoverTvShows(query: Map<String, String>): Observable<PaginatedResponse<TvShow>>
            = mApiService.discoverTvShows(query)

    override fun discoverMovies(query: Map<String, String>): Observable<PaginatedResponse<Movie>>
            = mApiService.discoverMovies(query)

    override fun getConfiguration(): Observable<Configuration> = mApiService.getConfiguration()

    override fun getMovieDetails(id: Int, append: String): Observable<MovieDetails> = mApiService.getMovieDetail(id, append)

    override fun getTvShowDetails(id: Int, append: String): Observable<TvShowDetails> = mApiService.getTvShowDetails(id, append)
}