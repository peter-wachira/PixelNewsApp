package com.droid.newsapiclient.presentation.di

import android.app.Application
import com.droid.newsapiclient.domain.usecase.*
import com.droid.newsapiclient.presentation.viewmodel.NewsViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FactoryModule {
    @Singleton
    @Provides
    fun provideNewsViewModelFactory(
            application: Application,
            getNewsHeadlinesUseCase: GetNewsHeadlinesUseCase,
            getSearchNewsUseCase: GetSearchNewsUseCase,
            saveNewsUseCase: SaveNewsUseCase,
            getSavedNewsUseCase: GetSavedNewsUseCase,
            deleteSavedNewsUseCase: DeleteSavedNewsUseCase
    ): NewsViewModelFactory {
        return NewsViewModelFactory(
                application,
                getNewsHeadlinesUseCase,
                getSearchNewsUseCase,
                saveNewsUseCase,
                getSavedNewsUseCase,
                deleteSavedNewsUseCase
        )

    }


}