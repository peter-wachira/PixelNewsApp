package com.droid.newsapiclient.presentation.di

import com.droid.newsapiclient.data.repository.NewsRepositoryImpl
import com.droid.newsapiclient.data.repository.datasource.NewsRemoteDataSource
import com.droid.newsapiclient.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideNewsRepository(
            newsRemoteDataSource: NewsRemoteDataSource
    ): NewsRepository{
        return  NewsRepositoryImpl(newsRemoteDataSource)
    }
}