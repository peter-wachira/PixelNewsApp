package com.droid.newsapiclient.domain.usecase

import com.droid.newsapiclient.data.model.APIResponse
import com.droid.newsapiclient.data.util.Resource
import com.droid.newsapiclient.domain.repository.NewsRepository

class GetSearchNewsUseCase (private val newsRepository: NewsRepository ) {
    suspend fun execute(searchQuery: String): Resource<APIResponse> {
        return newsRepository.getSearchedNews(searchQuery)
    }

}