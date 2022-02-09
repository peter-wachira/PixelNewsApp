package com.droid.newsapiclient.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.droid.newsapiclient.R
import com.droid.newsapiclient.databinding.FragmentSavedBinding
import com.droid.newsapiclient.presentation.adapter.NewsAdapter
import com.droid.newsapiclient.presentation.viewmodel.NewsViewModel


class SavedFragment : Fragment() {

    private lateinit var binding: FragmentSavedBinding
    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSavedBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel
        newsAdapter = (activity as MainActivity).newsAdapter
        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("selected_article", it)
            }
            findNavController().navigate(R.id.action_newsFragment_to_infoFragment, bundle)
        }
        initRecylerView()
        observeSavedNews()
    }

    private fun observeSavedNews() {
        viewModel.getSavedNews().observe(viewLifecycleOwner) {
            newsAdapter.differ.submitList(it)
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_saved, container, false)

    }

    private fun initRecylerView() {
        binding.rvNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }


}