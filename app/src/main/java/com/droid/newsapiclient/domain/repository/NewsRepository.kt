package com.droid.newsapiclient.domain.repository

import com.droid.newsapiclient.data.model.APIResponse
import com.droid.newsapiclient.data.model.Article
import com.droid.newsapiclient.data.util.Resource
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    //get from API
    suspend fun getNewsHeadlines(country: String, page: Int): Resource<APIResponse>
    suspend fun getSearchedNews(country: String, searchQuery: String, page: Int): Resource<APIResponse>

    // get items locally

    suspend fun saveNews(article: Article)
    suspend fun deleteNews(article: Article)
    fun getSavedNews(): Flow<List<Article>>


}
