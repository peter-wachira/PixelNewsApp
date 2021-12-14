package com.droid.newsapiclient.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.droid.newsapiclient.domain.usecase.GetNewsHeadlinesUseCase
import com.droid.newsapiclient.domain.usecase.GetSearchNewsUseCase

class NewsViewModelFactory(
        private val app:Application,
        private val getNewsHeadlinesUseCase: GetNewsHeadlinesUseCase,
        private val getSearchNewsUseCase: GetSearchNewsUseCase
):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(
                app,
                getNewsHeadlinesUseCase,
                getSearchNewsUseCase
        ) as T
    }
}