package com.droid.newsapiclient.data.repository.datasourceimpl

import com.droid.newsapiclient.data.api.NewsAPIService
import com.droid.newsapiclient.data.model.APIResponse
import com.droid.newsapiclient.data.repository.datasource.NewsRemoteDataSource
import retrofit2.Response

class NewsRemoteDataSourceImpl(
        private val newsAPIService: NewsAPIService,
        private  val country :String,
        private val page: Int
): NewsRemoteDataSource{
    override suspend fun getTopHeadlines(): Response<APIResponse> {
       return newsAPIService.getTopHeadlines(country,page)
    }
}