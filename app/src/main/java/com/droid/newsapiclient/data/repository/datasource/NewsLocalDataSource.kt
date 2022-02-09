package com.droid.newsapiclient.data.repository.datasource

import com.droid.newsapiclient.data.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsLocalDataSource {
    suspend fun saveArticleToDB(article: Article)
    fun getSavedArtices(): Flow<List<Article>>
}