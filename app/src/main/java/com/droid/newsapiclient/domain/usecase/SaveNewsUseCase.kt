package com.droid.newsapiclient.domain.usecase

import com.droid.newsapiclient.data.model.Article
import com.droid.newsapiclient.domain.repository.NewsRepository

class SaveNewsUseCase(private val newsRepository: NewsRepository) {
    suspend fun execute(article: Article) = newsRepository.saveNews(article)
}
