package com.droid.newsapiclient.presentation.di

import com.droid.newsapiclient.domain.repository.NewsRepository
import com.droid.newsapiclient.domain.usecase.*
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

    @Provides
    fun providesGetSavedNewsUseCaseModule(newsRepository: NewsRepository): SaveNewsUseCase {
        return SaveNewsUseCase(newsRepository)
    }

    @Provides
    fun provideGetSavedNewsUsecaseModule(newsRepository: NewsRepository): GetSavedNewsUseCase {
        return GetSavedNewsUseCase(newsRepository)
    }

    @Provides
    fun provideDeleteSavedNewsUseCaseModule(newsRepository: NewsRepository): DeleteSavedNewsUseCase {
        return DeleteSavedNewsUseCase(newsRepository)
    }
}


