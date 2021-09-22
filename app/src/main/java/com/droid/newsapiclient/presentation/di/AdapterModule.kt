package com.droid.newsapiclient.presentation.di

import com.droid.newsapiclient.presentation.adapter.NewsAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationContext::class)
class AdapterModule {
    @Singleton
    @Provides
    fun providesNewsAdapter():NewsAdapter{
        return  NewsAdapter()
    }
}