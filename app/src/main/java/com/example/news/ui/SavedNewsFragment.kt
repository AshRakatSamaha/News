package com.example.news.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.news.R
import com.example.news.adapter.NewsAdapter
import com.example.news.databinding.FragmentSavedNewsBinding
import com.example.news.viewmodel.NewsViewModel


class SavedNewsFragment : Fragment() {
private lateinit var binding: FragmentSavedNewsBinding
private lateinit var newsViewModel:NewsViewModel
private lateinit var newsAdapter: NewsAdapter
private lateinit var recyclerView:RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentSavedNewsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsViewModel=(activity as MainActivity).newsViewModel
        initRecycleView()
        callBack()

    }
    private fun callBack(){
        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(
                R.id.action_savedNewsFragment_to_articleFragment,
                bundle
            )
        }
    }
    private fun initRecycleView() {
        recyclerView = binding.recycle
        newsAdapter = NewsAdapter()
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = newsAdapter
    }

}