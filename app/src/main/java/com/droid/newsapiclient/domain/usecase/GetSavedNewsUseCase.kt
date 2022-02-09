package com.droid.newsapiclient.domain.usecase

import com.droid.newsapiclient.data.model.Article
import com.droid.newsapiclient.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetSavedNewsUseCase (private val newsRepository: NewsRepository) {
     fun execute(): Flow<List<Article>> {
         return newsRepository.getSavedNews()
     }
}
