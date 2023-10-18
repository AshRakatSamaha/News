package com.example.news.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.model.NewsResponse
import com.example.news.repository.NewsRepository
import com.example.news.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.util.Locale.IsoCountryCode

class NewsViewModel(
    private val newsRepository: NewsRepository
):ViewModel() {

    val breakingNews:MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var breakingPage=1

    init {
        getBreakingNews("us")
    }
    fun getBreakingNews(countryCode: String){
        viewModelScope.launch {
            breakingNews.postValue(Resource.Loading())
            val response=newsRepository.getBreakingNews(countryCode,breakingPage)
            breakingNews.postValue(handleBreakingNewsResponse(response))
        }
    }

    private fun handleBreakingNewsResponse(response: Response<NewsResponse>):Resource<NewsResponse>{
        if (response.isSuccessful){
            response.body()?.let { resultResponse->
                return Resource.Success(resultResponse)
            }

        }
        return Resource.Error(response.message())
    }
}