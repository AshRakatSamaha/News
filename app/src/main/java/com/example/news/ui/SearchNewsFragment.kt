package com.example.news.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.news.R
import com.example.news.adapter.NewsAdapter
import com.example.news.databinding.FragmentSearchNewsBinding
import com.example.news.util.Constant.Companion.SEARCH_NEWS_TIME
import com.example.news.util.Resource
import com.example.news.viewmodel.NewsViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchNewsFragment : Fragment() {
    private lateinit var binding: FragmentSearchNewsBinding
    private lateinit var newsViewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchNewsBinding.inflate(layoutInflater)
        initRecycleView()
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsViewModel = (activity as MainActivity).newsViewModel
        delaySearch()
        callBack()
        showSearch()

    }
    private fun delaySearch(){
        var job:Job?=null
        binding.search.addTextChangedListener {editable->
            job?.cancel()
            job= MainScope().launch {
                delay(SEARCH_NEWS_TIME)
                if (editable.toString().isNotEmpty()){
                    newsViewModel.searchNews(editable.toString())
                }
            }
        }
    }
    private fun callBack(){
        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(
                R.id.action_searchNewsFragment_to_articleFragment,
                bundle
            )
        }
    }

    private fun showSearch(){

        newsViewModel.searchNews.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles)

                    }
                }

                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Log.e("Ashrakat", "error in :$message")

                    }
                }

                is Resource.Loading -> {
                    showProgressBar()


                }

                else -> null

            }

        })
    }

    private fun hideProgressBar() {
        binding.paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
       binding.paginationProgressBar.visibility = View.VISIBLE
    }

    private fun initRecycleView() {
        recyclerView = binding.recycle
        newsAdapter = NewsAdapter()
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = newsAdapter
    }

}