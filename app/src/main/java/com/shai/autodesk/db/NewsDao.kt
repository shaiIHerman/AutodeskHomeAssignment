package com.shai.autodesk.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticle(articleModel: ArticleModel): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAllArticles(articleModels: List<ArticleModel>): Completable

    @Query("SELECT * FROM articles")
    fun getArticles(): Flowable<List<ArticleModel>>

    @Query("SELECT publishedAt FROM articles ORDER BY articleId LIMIT 1")
    fun getLatestArticle() : Single<String>
}