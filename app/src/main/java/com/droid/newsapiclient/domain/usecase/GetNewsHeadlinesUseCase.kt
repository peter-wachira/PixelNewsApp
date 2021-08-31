package com.droid.newsapiclient.domain.usecase

import com.droid.newsapiclient.domain.repository.NewsRepository

class GetNewsHeadlinesUseCase(private val newsRepository: NewsRepository) {
}