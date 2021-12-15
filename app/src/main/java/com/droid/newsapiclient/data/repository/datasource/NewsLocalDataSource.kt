package com.droid.newsapiclient.data.repository.datasource

import com.droid.newsapiclient.data.model.Article

interface NewsLocalDataSource {
    suspend fun saveArticleToDB(article: Article)
}