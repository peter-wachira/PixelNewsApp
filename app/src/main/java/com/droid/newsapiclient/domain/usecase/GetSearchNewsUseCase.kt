package com.droid.newsapiclient.domain.usecase

import com.droid.newsapiclient.domain.repository.NewsRepository

class GetSearchNewsUseCase (private val newsRepository: NewsRepository ) {
}