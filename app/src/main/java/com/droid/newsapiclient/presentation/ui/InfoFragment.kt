package com.droid.newsapiclient.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.droid.newsapiclient.R
import com.droid.newsapiclient.databinding.FragmentInfoBinding


class InfoFragment : Fragment() {

    private lateinit var binding: FragmentInfoBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentInfoBinding.bind(view)

        getInfoFragmentArgs()
    }

    private fun getInfoFragmentArgs() {
        val args : InfoFragmentArgs by  navArgs()
        val article = args.selectedArticle
        //display article in web view
        binding.webViewInfo.apply {
            webViewClient = WebViewClient()
            if (article.url != null){
                loadUrl(article.url)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info, container, false)
    }

}