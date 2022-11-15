package com.droid.newsapiclient.domain.usecase

import com.droid.newsapiclient.data.model.APIResponse
import com.droid.newsapiclient.data.util.Resource
import com.droid.newsapiclient.domain.repository.NewsRepository

class GetSearchNewsUseCase(private val newsRepository: NewsRepository) {
    suspend fun execute(country: String, searchQuery: String, page: Int): Resource<APIResponse> {
        return newsRepository.getSearchedNews(country, searchQuery, page)
    }
}
