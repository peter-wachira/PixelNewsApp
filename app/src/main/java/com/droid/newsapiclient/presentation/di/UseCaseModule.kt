package com.droid.newsapiclient.presentation.di

import com.droid.newsapiclient.domain.repository.NewsRepository
import com.droid.newsapiclient.domain.usecase.GetNewsHeadlinesUseCase
import com.droid.newsapiclient.domain.usecase.GetSearchNewsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class UseCaseModule {
    @Singleton
    @Provides
    fun provideGetNewsHeadlinesUseCaseModule(newsRepository: NewsRepository): GetNewsHeadlinesUseCase {
        return GetNewsHeadlinesUseCase(newsRepository)
    }
    @Singleton
    @Provides
    fun provideGetSearchedNewsHeadlinesUseCaseModule(newsRepository: NewsRepository): GetSearchNewsUseCase{
        return GetSearchNewsUseCase(newsRepository)
    }

}

