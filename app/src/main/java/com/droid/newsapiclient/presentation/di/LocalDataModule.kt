package com.droid.newsapiclient.presentation.di

import com.droid.newsapiclient.data.db.ArticleDao
import com.droid.newsapiclient.data.repository.datasource.NewsLocalDataSource
import com.droid.newsapiclient.data.repository.datasourceimpl.NewsLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDataModule {

    @Provides
    @Singleton
    fun provideLocalDataSource(articleDao: ArticleDao): NewsLocalDataSource {
        return NewsLocalDataSourceImpl(articleDao)
    }
}