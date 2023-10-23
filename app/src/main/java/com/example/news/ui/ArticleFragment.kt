package com.example.news.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.news.databinding.FragmentArticleBinding
import com.example.news.viewmodel.NewsViewModel

class ArticleFragment : Fragment() {
    private lateinit var binding: FragmentArticleBinding
    lateinit var newsViewModel: NewsViewModel
    val args: ArticleFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentArticleBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsViewModel = (activity as MainActivity).newsViewModel

        val article=args.article
        binding.webView.apply {
            webViewClient= WebViewClient()
            loadUrl(article.url)
        }
    }


}