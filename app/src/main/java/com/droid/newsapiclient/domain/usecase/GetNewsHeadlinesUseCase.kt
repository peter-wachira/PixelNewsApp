package com.droid.newsapiclient.domain.usecase

import com.droid.newsapiclient.data.model.APIResponse
import com.droid.newsapiclient.data.util.Resource
import com.droid.newsapiclient.domain.repository.NewsRepository

class GetNewsHeadlinesUseCase(private val newsRepository: NewsRepository) {

    suspend fun execute(country : String, page : Int): Resource<APIResponse>{
        return newsRepository.getNewsHeadlines(country,page)
    }


}