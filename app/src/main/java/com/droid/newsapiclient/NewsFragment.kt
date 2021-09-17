package com.droid.newsapiclient

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.droid.newsapiclient.data.util.extensions.hide
import com.droid.newsapiclient.data.util.extensions.*
import com.droid.newsapiclient.databinding.FragmentNewsBinding
import com.droid.newsapiclient.presentation.adapter.NewsAdapter
import com.droid.newsapiclient.presentation.viewmodel.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class NewsFragment : Fragment() {

    private val page = 3
    private val country = "us"
    private val binding: FragmentNewsBinding by lazy {
        FragmentNewsBinding.inflate(layoutInflater)
    }
    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        initRecyclerView()

    }

    private fun fetchNewsList() {
        viewModel.getNewsHeadlines(country, page)
        viewModel.newsHeadlines.observe(viewLifecycleOwner, { response ->
            when (response) {
                is com.droid.newsapiclient.data.util.Resource.Success -> {
                    binding.progressBar.hide()
                    Timber.e("API Response:${response.data}")
                    response.data.let {
                        if (it != null) {
                            newsAdapter.submitList(it.articles.toMutableList())
                            newsAdapter.notifyDataSetChanged()
                        }
                    }
                }
                is com.droid.newsapiclient.data.util.Resource.Error -> {
                    binding.progressBar.hide()
                    binding.root.showErrorSnackbar("An error occured: ${response.message}",Snackbar.LENGTH_LONG)
                    Timber.e("API Error:${response.message}")

                }
                is com.droid.newsapiclient.data.util.Resource.Loading -> {
                    binding.progressBar.show()

                }
            }
        })
    }

    private fun initRecyclerView() {
        newsAdapter = NewsAdapter()
        binding.rvNews.apply {
            adapter = newsAdapter
        }

        fetchNewsList()
    }


}