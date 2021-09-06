package com.droid.newsapiclient.data.repository.datasourceimpl

import com.droid.newsapiclient.data.api.NewsAPIService
import com.droid.newsapiclient.data.model.APIResponse
import com.droid.newsapiclient.data.repository.datasource.NewsRemoteDataSource
import retrofit2.Response

class NewsRemoteDataSourceImpl(
        private val newsAPIService: NewsAPIService,
) : NewsRemoteDataSource {
    override suspend fun getTopHeadlines(country: String, page: Int): Response<APIResponse> {
        return newsAPIService.getTopHeadlines(country, page)
    }
}