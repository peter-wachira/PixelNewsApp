package com.droid.newsapiclient.presentation.ui


import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.droid.newsapiclient.R
import com.droid.newsapiclient.data.util.Resource
import com.droid.newsapiclient.data.util.extensions.convertToHoursMins
import com.droid.newsapiclient.data.util.extensions.showErrorSnackbar
import com.droid.newsapiclient.databinding.FragmentSearchNewsBinding
import com.droid.newsapiclient.presentation.adapter.NewsAdapter
import com.droid.newsapiclient.presentation.viewmodel.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

class SearchNewsFragment : Fragment() {

    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var fragmentSearchNewsBinding: FragmentSearchNewsBinding
    private var country = "us"
    private var page = 1
    private var isScrolling = false
    private var isLoading = false
    private var isLastPage = false
    private var pages = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentSearchNewsBinding = FragmentSearchNewsBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel
        newsAdapter = (activity as MainActivity).newsAdapter
        requireActivity().window.statusBarColor = Color.WHITE
        initRecyclerView()
        setSearchView()
        viewArticleDetails()
        viewNewsList()
        getBannerNews()
    }

    private fun getBannerNews() {
        viewModel.searchNews("us", "covid", page)
        viewModel.searchedNews.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    Timber.e("response:  ${response.data}")
                    hideProgressBar()
                    response.data?.let {
                        Glide.with(fragmentSearchNewsBinding.root.context)
                            .load(it.articles.first().urlToImage)
                            .into(fragmentSearchNewsBinding.imageView2)

                        fragmentSearchNewsBinding.textView6.text =
                            it.articles.first().publishedAt?.let { it1 -> convertToHoursMins(it1) }
                        fragmentSearchNewsBinding.textView5.text =
                            "${it.articles.first().title}"
                        fragmentSearchNewsBinding.textView7.text =
                            "${it.articles.first().source?.name}"

                    }
                }
                is Resource.Error -> {
                    response.message.let {
                        fragmentSearchNewsBinding.root.showErrorSnackbar(
                            "An error occurred : $it",
                            Snackbar.LENGTH_LONG
                        )
                    }
                }
                else -> {}
            }
        })

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_news, container, false)
    }


    //    search implementation
    private fun setSearchView() {
        fragmentSearchNewsBinding.searchNews.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.searchNews("us", query.toString(), page)
                viewSearchedNews()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                MainScope().launch {
                    delay(3000)
                }
                viewModel.searchNews("us", newText.toString(), page)
                viewSearchedNews()
                return false
            }
        })


        //reset search after close
        fragmentSearchNewsBinding.searchNews.setOnCloseListener {
            initRecyclerView()
            viewNewsList()
            false
        }
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
                        fragmentSearchNewsBinding.root.showErrorSnackbar(
                            "An error occurred : $it",
                            Snackbar.LENGTH_LONG
                        )
                    }
                }
                else -> {}
            }
        })
    }


    private fun viewArticleDetails() {
        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("selected_article", it)
            }
            //pass bundle to info fragment
            findNavController().navigate(R.id.action_searchFragment_to_infoFragment, bundle)
        }
    }


    private fun initRecyclerView() {
        // newsAdapter = NewsAdapter()
        fragmentSearchNewsBinding.rvNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@SearchNewsFragment.onScrollListener)
        }

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


                else -> {
                    hideProgressBar()
                    response.message?.let {

                        fragmentSearchNewsBinding.root.showErrorSnackbar(
                            "An error occurred : $it",
                            Snackbar.LENGTH_LONG
                        )

                    }

                }
            }
        })
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
            val layoutManager =
                fragmentSearchNewsBinding.rvNews.layoutManager as LinearLayoutManager
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


    private fun showProgressBar() {
        isLoading = true
        fragmentSearchNewsBinding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        isLoading = false
        fragmentSearchNewsBinding.progressBar.visibility = View.INVISIBLE
    }

}