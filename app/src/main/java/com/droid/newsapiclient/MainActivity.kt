package com.droid.newsapiclient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.droid.newsapiclient.databinding.ActivityMainBinding
import com.droid.newsapiclient.presentation.viewmodel.NewsViewModel
import com.droid.newsapiclient.presentation.viewmodel.NewsViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var  factory: NewsViewModelFactory
    lateinit var viewModel: NewsViewModel
    private lateinit var navController: NavController

    private val binding: ActivityMainBinding by lazy {
           ActivityMainBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)



        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        navController = navHostFragment.navController


        viewModel = ViewModelProvider(this,factory)
                .get(NewsViewModel::class.java)    }


}