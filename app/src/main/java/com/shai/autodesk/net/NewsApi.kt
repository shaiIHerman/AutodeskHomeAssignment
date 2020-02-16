package com.shai.autodesk.net

import com.shai.autodesk.net.model.NewsResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("top-headlines")
    fun getSourceHeadlines(@Query("sources") source: String, @Query("apiKey") key: String): Single<NewsResponse>
}