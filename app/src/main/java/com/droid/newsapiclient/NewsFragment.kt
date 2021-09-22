package com.droid.newsapiclient

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

    private var page = 3
    private val country = "us"
    private val binding: FragmentNewsBinding by lazy {
        FragmentNewsBinding.inflate(layoutInflater)
    }
    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter
    private var isScrolling  = false
    private var isLastPage = false
    private  var isLoading = true
    private  var pages = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        newsAdapter = (activity as MainActivity).newsAdapter
        initRecyclerView()

    }

    private fun fetchNewsList() {
        viewModel.getNewsHeadlines(country, page)
        viewModel.newsHeadlines.observe(viewLifecycleOwner, { response ->
            when (response) {
                is com.droid.newsapiclient.data.util.Resource.Success -> {
                    binding.progressBar.hide()
                    isLoading = false
                    Timber.e("API Response:${response.data}")
                    response.data.let {
                        if (it != null) {
                            newsAdapter.submitList(it.articles.toMutableList())
                            if (it.totalResults%20 ==0){
                                 pages = it.totalResults/20
                            }else {
                                 pages = it.totalResults/20 + 1
                            }
                            isLastPage = page == pages
                            newsAdapter.notifyDataSetChanged()
                        }
                    }
                }
                is com.droid.newsapiclient.data.util.Resource.Error -> {
                    binding.progressBar.hide()
                    isLoading = false
                    binding.root.showErrorSnackbar("An error occured: ${response.message}",Snackbar.LENGTH_LONG)
                    Timber.e("API Error:${response.message}")

                }
                is com.droid.newsapiclient.data.util.Resource.Loading -> {
                    binding.progressBar.show()
                    isLoading = true
                }
            }
        })
    }

    private fun initRecyclerView() {
        newsAdapter = NewsAdapter()
        binding.rvNews.apply {
            adapter = newsAdapter
            addOnScrollListener(this@NewsFragment.onScrollChangeListener)
        }

        fetchNewsList()
    }

    private val onScrollChangeListener = object : RecyclerView.OnScrollListener(){
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager =  binding.rvNews.layoutManager as LinearLayoutManager
            val sizeOfTheCurrentList = layoutManager.itemCount
            val visibleItems = layoutManager.childCount
            val topPosition = layoutManager.findFirstVisibleItemPosition()
            val hasReachedToEnd = topPosition + visibleItems >= sizeOfTheCurrentList
            val shouldPagenate = !isLoading && !isLastPage && hasReachedToEnd &&isScrolling
            if (shouldPagenate){
                page++
                viewModel.getNewsHeadlines(country,page)
                isScrolling = false
            }
        }
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                isScrolling = true
            }
        }
    }



}