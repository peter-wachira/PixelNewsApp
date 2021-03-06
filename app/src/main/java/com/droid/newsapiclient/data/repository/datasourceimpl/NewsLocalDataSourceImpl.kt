package com.droid.newsapiclient.data.repository.datasourceimpl

import com.droid.newsapiclient.data.db.ArticleDao
import com.droid.newsapiclient.data.model.Article
import com.droid.newsapiclient.data.repository.datasource.NewsLocalDataSource

class NewsLocalDataSourceImpl(
        private val articleDao: ArticleDao
) : NewsLocalDataSource {
    override suspend fun saveArticleToDB(article: Article) {
        articleDao.insert(article)
    }
}