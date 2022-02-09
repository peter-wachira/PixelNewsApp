package com.droid.newsapiclient.presentation.di

import com.droid.newsapiclient.domain.repository.NewsRepository
import com.droid.newsapiclient.domain.usecase.GetNewsHeadlinesUseCase
import com.droid.newsapiclient.domain.usecase.GetSavedNewsUseCase
import com.droid.newsapiclient.domain.usecase.GetSearchNewsUseCase
import com.droid.newsapiclient.domain.usecase.SaveNewsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {
    @Singleton
    @Provides
    fun provideGetNewsHeadlinesUseCaseModule(newsRepository: NewsRepository): GetNewsHeadlinesUseCase {
        return GetNewsHeadlinesUseCase(newsRepository)
    }
    @Singleton
    @Provides
    fun provideGetSearchedNewsHeadlinesUseCaseModule(newsRepository: NewsRepository): GetSearchNewsUseCase {
        return GetSearchNewsUseCase(newsRepository)
    }

    @Singleton
    @Provides
    fun providesSavedNewsUseCaseModule(newsRepository: NewsRepository): SaveNewsUseCase {
        return SaveNewsUseCase(newsRepository)
    }

    @Singleton
    @Provides
    fun provideGetSavedNewsUseCaseModule(newsRepository: NewsRepository): GetSavedNewsUseCase {
        return GetSavedNewsUseCase(newsRepository)
    }

}

