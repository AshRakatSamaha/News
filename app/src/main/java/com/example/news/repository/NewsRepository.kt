package com.example.news.repository

import com.example.news.api.RetrofitInstance
import com.example.news.db.ArticleDatabase
import com.example.news.model.Article

class NewsRepository(
    private val db: ArticleDatabase,
) {
    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getBreakingNews(countryCode, pageNumber)

    suspend fun searchNews(searchQuery: String, pageNumber: Int) =
        RetrofitInstance.api.searchForNews(searchQuery, pageNumber)

    suspend fun upsert(article: Article)=db.getArticleDao().upsert(article)


    suspend fun deleteArticle(article: Article)=db.getArticleDao().deleteArticle(article)

    fun getSavedArticle()=db.getArticleDao().getAllArticles()



}