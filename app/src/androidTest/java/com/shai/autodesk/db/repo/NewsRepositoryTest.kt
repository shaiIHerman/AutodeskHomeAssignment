package com.shai.autodesk.db.repo

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.shai.autodesk.db.AppDatabase
import com.shai.autodesk.db.dao.NewsDao
import com.shai.autodesk.db.model.ArticleModel
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.concurrent.TimeUnit


@RunWith(AndroidJUnit4::class)
class NewsRepositoryTest {

    private lateinit var appDatabase: AppDatabase

    private lateinit var newsDao: NewsDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        newsDao = appDatabase.newsDao()
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        appDatabase.close()
    }

    @Test
    fun saveArticles() {
        val article = ArticleModel(
            0L, ArticleModel.Source(
                "bbc-news", "BBC News"
            ), "BBC News", "US issues fresh charges against Huawei",
            "US prosecutors have added racketeering and theft to a list of charges against the embattled telecoms company.",
            "http://www.bbc.co.uk/news/business-51497460",
            "https://ichef.bbci.co.uk/news/1024/branded_news/8C89/production/_105377953_051973079.jpg",
            "2020-02-13T22:03:15Z",
            "Image copyrightReutersImage caption"
        )
        val articles = mutableListOf(article)

        newsDao.saveAllArticles(articles).subscribe()

        newsDao.getArticles().test().awaitDone(5, TimeUnit.SECONDS)
            .assertValueCount(1)
            .assertNotTerminated()
    }
}