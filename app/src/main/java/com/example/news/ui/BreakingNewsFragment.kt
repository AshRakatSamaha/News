package com.example.news.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.news.R
import com.example.news.adapter.NewsAdapter
import com.example.news.databinding.FragmentBreakingNewsBinding
import com.example.news.util.Resource
import com.example.news.viewmodel.NewsViewModel


class BreakingNewsFragment : Fragment() {
private lateinit var binding: FragmentBreakingNewsBinding
private lateinit var newsViewModel: NewsViewModel
lateinit var newsAdapter: NewsAdapter
private lateinit var recyclerView:RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding=FragmentBreakingNewsBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        initRecycleView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsViewModel=(activity as MainActivity).newsViewModel

        newsViewModel.breakingNews.observe(viewLifecycleOwner, Observer {response->
        when(response){
            is Resource.Success->{
                hideProgressBar()
                response.data?.let {newsResponse ->
                newsAdapter.differ.submitList(newsResponse.articles)

                }
            }
            is Resource.Error->{
               hideProgressBar()
                response.message?.let {message->
                    Log.e("Ashrakat","error in :$message")

                }
            }
            is Resource.Loading ->{
                    showProgressBar()


            }
            else-> null

        }

        })
    }

    private fun hideProgressBar() {
       binding.paginationProgressBar.visibility=View.INVISIBLE
    }
    private fun showProgressBar() {
        binding.paginationProgressBar.visibility=View.VISIBLE
    }

    private fun initRecycleView(){
        recyclerView = binding.recycle
        newsAdapter = NewsAdapter()
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = newsAdapter
    }


}