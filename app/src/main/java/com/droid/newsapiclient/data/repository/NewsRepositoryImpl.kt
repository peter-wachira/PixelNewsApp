package com.droid.newsapiclient.data.repository

import com.droid.newsapiclient.data.model.APIResponse
import com.droid.newsapiclient.data.model.Article
import com.droid.newsapiclient.data.repository.datasource.NewsLocalDataSource
import com.droid.newsapiclient.data.repository.datasource.NewsRemoteDataSource
import com.droid.newsapiclient.data.util.Resource
import com.droid.newsapiclient.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class NewsRepositoryImpl(
    private val newsRemoteDataSource: NewsRemoteDataSource,
    private val newsLocalDataSource: NewsLocalDataSource

) : NewsRepository {
    override suspend fun getNewsHeadlines(
        country: String,
        page: Int
    ): Resource<APIResponse> {
        return responseToResource(newsRemoteDataSource.getTopHeadlines(country, page))
    }

    override suspend fun getSearchedNews(
        country: String,
        searchQuery: String,
        page: Int
    ): Resource<APIResponse> {
        return responseToResource(
            newsRemoteDataSource.getSearchedTopHeadlines(country, searchQuery, page)
        )
    }

    private fun responseToResource(response: Response<APIResponse>): Resource<APIResponse> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(result)
            }
        }

        return Resource.Error(response.message())
    }


    override suspend fun saveNews(article: Article) {
        newsLocalDataSource.saveArticleToDB(article)
    }

    override suspend fun deleteNews(article: Article) {
        TODO("Not yet implemented")
    }

    override fun getSavedNews(): Flow<List<Article>> {
        return newsLocalDataSource.getSavedArticles()
    }
}