package com.shai.autodesk

import com.shai.autodesk.db.AppDatabase
import com.shai.autodesk.db.ArticleModel
import com.shai.autodesk.db.NewsDao
import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Retrofit
import java.util.concurrent.Callable
import java.util.concurrent.TimeUnit


class NewsRepositoryTest {

    @Mock
    private lateinit var appDatabase: AppDatabase

    @Mock
    private lateinit var newsDao: NewsDao

    @Mock
    private lateinit var newsApi: NewsApi

    @Mock
    private lateinit var retrofit: Retrofit

    private lateinit var newsRepository: NewsRepository

    /*@BeforeClass
    fun setupClass() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { __: Callable<Scheduler?>? -> Schedulers.trampoline() }
    }*/
    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        newsRepository = NewsRepository(newsApi, newsDao)
    }

    @Test
    fun `save all articles without errors`() {
        val article = mutableListOf(
            ArticleModel(
                "bbc-news", "BBC News", "BBC News", "US issues fresh charges against Huawei",
                "US prosecutors have added racketeering and theft to a list of charges against the embattled telecoms company.",
                "http://www.bbc.co.uk/news/business-51497460",
                "https://ichef.bbci.co.uk/news/1024/branded_news/8C89/production/_105377953_051973079.jpg",
                "2020-02-13T22:03:15Z",
                "Image copyrightReutersImage caption"
            )
        )
        Mockito.`when`(appDatabase.newsDao()).thenReturn(newsDao)
        Mockito.`when`(newsDao.saveAllArticles(article)).thenReturn(Completable.complete())
        newsDao.saveAllArticles(article).test().awaitDone(5, TimeUnit.SECONDS)
            .assertComplete()
    }

    @Test
    fun refresh() {
        val article = mutableListOf(
            ArticleModel(
                "bbc-news", "BBC News", "BBC News", "US issues fresh charges against Huawei",
                "US prosecutors have added racketeering and theft to a list of charges against the embattled telecoms company.",
                "http://www.bbc.co.uk/news/business-51497460",
                "https://ichef.bbci.co.uk/news/1024/branded_news/8C89/production/_105377953_051973079.jpg",
                "2020-02-13T22:03:15Z",
                "Image copyrightReutersImage caption"
            )
        )

        val response = NewsResponse(
            "ok", 1, mutableListOf(
                ArticleInfo(
                    Source("bbc-news", "BBC News"),
                    "BBC News",
                    "US issues fresh charges against Huawei",
                    "US prosecutors have added racketeering and theft to a list of charges against the embattled telecoms company.",
                    "http://www.bbc.co.uk/news/business-51497460",
                    "https://ichef.bbci.co.uk/news/1024/branded_news/8C89/production/_105377953_051973079.jpg",
                    "2020-02-13T22:03:15Z",
                    "Image copyrightReutersImage caption"
                )
            )
        )
        Mockito.`when`(appDatabase.newsDao()).thenReturn(newsDao)
        Mockito.`when`(newsDao.saveAllArticles(article)).thenReturn(Completable.complete())
        Mockito.`when`(retrofit.create(NewsApi::class.java)).thenReturn(newsApi)
        Mockito.`when`(newsApi.getSourceHeadlines("1", "1").subscribeOn(Schedulers.io())).thenReturn(Single.just(response))
        newsRepository.refresh()
    }
}