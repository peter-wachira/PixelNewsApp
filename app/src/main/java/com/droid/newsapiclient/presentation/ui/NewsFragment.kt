package com.droid.newsapiclient.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.droid.newsapiclient.R
import com.droid.newsapiclient.data.util.Resource
import com.droid.newsapiclient.data.util.extensions.showErrorSnackbar
import com.droid.newsapiclient.databinding.NewsFragmentLayoutBinding
import com.droid.newsapiclient.presentation.adapter.NewsAdapter
import com.droid.newsapiclient.presentation.viewmodel.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber


class NewsFragment : Fragment() {
    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var fragmentNewsBinding: NewsFragmentLayoutBinding
    private var country = "us"
    private var page = 1
    private var isScrolling = false
    private var isLoading = false
    private var isLastPage = false
    private var pages = 0
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.news_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentNewsBinding = NewsFragmentLayoutBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel
        newsAdapter = (activity as MainActivity).newsAdapter

        viewArticleDetails()
        initRecyclerView()
        viewNewsList()
        getBannerNews()
    }

    private fun viewArticleDetails() {
        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("selected_article", it)
            }
            //pass bundle to info fragment
            findNavController().navigate(R.id.action_newsFragment_to_infoFragment, bundle)
        }
    }



    private fun getBannerNews(){
        viewModel.searchNews("us", "covid", page)
        viewModel.searchedNews.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    Timber.e("response:  ${response.data}")
                    hideProgressBar()
                    response.data?.let {
                        if (it.articles.first().title?.isNotEmpty() == true){
                            fragmentNewsBinding.materialTextView2.text =   "Covid -19 News: \n ${it.articles.first().title}"
                            val bundle = Bundle().apply {
                                putSerializable("selected_article", it.articles.first())
                            }
                            //pass bundle to info fragment
                            findNavController().navigate(R.id.action_newsFragment_to_infoFragment, bundle)
                        }
                    }
                }
                is Resource.Error -> {
                    response.message.let {
                        fragmentNewsBinding.root.showErrorSnackbar(
                                "An error occurred : $it",
                                Snackbar.LENGTH_LONG
                        )
                    }
                }
            }
        })

    }


    fun viewSearchedNews() {
        viewModel.searchedNews.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    Timber.e("response:  ${response.data}")
                    hideProgressBar()
                    response.data?.let {
                        newsAdapter.differ.submitList(it.articles.toList())
                        when {
                            it.totalResults % 20 == 0 -> {
                                pages = it.totalResults / 20
                            }
                            else -> {
                                pages = it.totalResults / 20 + 1
                            }
                        }
                        isLastPage = page == pages
                    }
                }
                is Resource.Error -> {
                    response.message.let {
                        fragmentNewsBinding.root.showErrorSnackbar(
                                "An error occurred : $it",
                                Snackbar.LENGTH_LONG
                        )
                    }
                }
            }
        })
    }

    private fun viewNewsList() {
        viewModel.getNewsHeadLines(country, page)
        viewModel.newsHeadLines.observe(viewLifecycleOwner, { response ->
            when (response) {

                is Resource.Success -> {
                    Timber.e("response:  ${response.data}")

                    hideProgressBar()
                    response.data?.let {
                        newsAdapter.differ.submitList(it.articles.toList())
                        when {
                            it.totalResults % 20 == 0 -> {
                                pages = it.totalResults / 20
                            }
                            else -> {
                                pages = it.totalResults / 20 + 1
                            }
                        }
                        isLastPage = page == pages
                    }
                }
                is Error -> {
                    hideProgressBar()
                    response.message?.let {

                        fragmentNewsBinding.root.showErrorSnackbar(
                                "An error occurred : $it",
                                Snackbar.LENGTH_LONG
                        )

                    }

                }


                is Resource.Loading -> {
                    showProgressBar()
                }

            }
        })
    }

    private fun initRecyclerView() {
        // newsAdapter = NewsAdapter()
        fragmentNewsBinding.rvNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@NewsFragment.onScrollListener)
        }

    }

    private fun showProgressBar() {
        isLoading = true
        fragmentNewsBinding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        isLoading = false
        fragmentNewsBinding.progressBar.visibility = View.INVISIBLE
    }

    private val onScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }

        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = fragmentNewsBinding.rvNews.layoutManager as LinearLayoutManager
            val sizeOfTheCurrentList = layoutManager.itemCount
            val visibleItems = layoutManager.childCount
            val topPosition = layoutManager.findFirstVisibleItemPosition()

            val hasReachedToEnd = topPosition + visibleItems >= sizeOfTheCurrentList
            val shouldPaginate = !isLoading && !isLastPage && hasReachedToEnd && isScrolling
            if (shouldPaginate) {
                page++
                viewModel.getNewsHeadLines(country, page)
                isScrolling = false

            }


        }
    }

}


