package com.droid.newsapiclient.domain.usecase

import com.droid.newsapiclient.data.model.Article
import com.droid.newsapiclient.data.util.Resource
import com.droid.newsapiclient.domain.repository.NewsRepository
import retrofit2.Response

class SaveNewsUseCase (private val newsRepository: NewsRepository){
    suspend fun execute(article: Article) = newsRepository.saveNews(article)
}