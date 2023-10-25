package com.example.news.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.model.Article
import com.example.news.model.NewsResponse
import com.example.news.repository.NewsRepository
import com.example.news.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(
    private val newsRepository: NewsRepository,
) : ViewModel() {

    val breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var breakingNewsResponse:NewsResponse?=null
    var breakingPage = 1


    val searchNew: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var searchNewsResponse:NewsResponse?=null
    var searchNewsPage = 1

    init {
        getBreakingNews("eg")
    }

    fun getBreakingNews(countryCode: String) {
        viewModelScope.launch {
            breakingNews.postValue(Resource.Loading())
            val response = newsRepository.getBreakingNews(countryCode, breakingPage)
            breakingNews.postValue(handleBreakingNewsResponse(response))
        }
    }

    fun searchNews(searchQuery: String) {
        viewModelScope.launch {
            searchNew.postValue(Resource.Loading())
            val response = newsRepository.searchNews(searchQuery, searchNewsPage)
            searchNew.postValue(handleSearchNewsResponse(response))
        }
    }

    private fun handleBreakingNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                breakingPage++
                if (breakingNewsResponse==null){
                    breakingNewsResponse=resultResponse
                }else{
                    val oldArticle=breakingNewsResponse?.articles
                    val newArticle=resultResponse.articles
                    oldArticle?.addAll(newArticle!!)
                }
                return Resource.Success(breakingNewsResponse ?:resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleSearchNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                searchNewsPage++
                if (searchNewsResponse==null){
                    searchNewsResponse=resultResponse
                }else{
                    val oldArticle=searchNewsResponse?.articles
                    val newArticle=resultResponse.articles
                    oldArticle?.addAll(newArticle!!)
                }
                return Resource.Success(searchNewsResponse ?:resultResponse)
            }

        }
        return Resource.Error(response.message())
    }

    fun savedArticle(article: Article) {
        viewModelScope.launch {
            newsRepository.upsert(article)
        }
    }

    fun getSavedArticle() = newsRepository.getSavedArticle()
    fun deleteArticle(article: Article) {
        viewModelScope.launch {
            newsRepository.deleteArticle(article)
        }
    }
}