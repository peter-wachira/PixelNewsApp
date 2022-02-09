package com.droid.newsapiclient.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.droid.newsapiclient.R
import com.droid.newsapiclient.data.util.extensions.showSnackbar
import com.droid.newsapiclient.databinding.FragmentInfoBinding
import com.droid.newsapiclient.presentation.viewmodel.NewsViewModel
import com.google.android.material.snackbar.Snackbar


class InfoFragment : Fragment() {

    private lateinit var binding: FragmentInfoBinding
    private lateinit var viewModel: NewsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentInfoBinding.bind(view)

        getInfoFragmentArgs()
        initlistener()
    }

    private fun initlistener() {

    }

    private fun getInfoFragmentArgs() {
        val args: InfoFragmentArgs by navArgs()
        val article = args.selectedArticle
        viewModel = (activity as MainActivity).viewModel
        //display article in web view
        binding.webViewInfo.apply {
            webViewClient = WebViewClient()
            if (article.url != null) {
                loadUrl(article.url)
            }
        }

        binding.floatingActionButton3.setOnClickListener {
            viewModel.saveArticle(article)
            binding.root.showSnackbar("Saved Successfully", Snackbar.LENGTH_LONG)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info, container, false)
    }

}