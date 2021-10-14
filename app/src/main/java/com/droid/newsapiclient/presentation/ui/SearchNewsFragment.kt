package com.droid.newsapiclient.presentation.ui

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.droid.newsapiclient.R
import android.view.*

class SearchNewsFragment : Fragment() {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().window.statusBarColor = Color.WHITE

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_news, container, false)
    }

}