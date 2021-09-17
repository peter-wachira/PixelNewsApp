package com.droid.newsapiclient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.droid.newsapiclient.databinding.ActivityMainBinding
import com.droid.newsapiclient.presentation.viewmodel.NewsViewModel
import com.droid.newsapiclient.presentation.viewmodel.NewsViewModelFactory

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: NewsViewModel
    lateinit var  factory: NewsViewModelFactory

    private val binding: ActivityMainBinding by lazy {
           ActivityMainBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.bnvNews.setupWithNavController(
                findNavController(R.id.fragmentContainer)
        )

        viewModel = ViewModelProvider(this, factory).get(NewsViewModel::class.java)
    }


}