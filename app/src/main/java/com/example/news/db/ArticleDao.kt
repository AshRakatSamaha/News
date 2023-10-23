package com.example.news.db

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy
import com.example.news.model.Article

@Dao
interface ArticleDao {

    @Insert
    suspend fun upsert(article: Article): Long

   @Query("SELECT * FROM articles")
    fun getAllArticles(): LiveData<List<Article>>

    @Delete
    suspend fun deleteArticle(article: Article)
}