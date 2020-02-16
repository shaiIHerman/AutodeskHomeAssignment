package com.shai.autodesk

import android.annotation.SuppressLint
import com.shai.autodesk.db.ArticleModel
import com.shai.autodesk.db.NewsDao
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class NewsRepository(private val newsApi: NewsApi, private val newsDao: NewsDao) {

    val data = newsDao.getArticles()

    @SuppressLint("CheckResult")
    fun refresh() {
        newsApi.getSourceHeadlines(DEFAULT_SOURCE_ID, API_KEY)
            .subscribeOn(Schedulers.io())
            .filter { it.totalResults > 0 }
            .map { castToArticleModel(it) }
            .flatMapCompletable { newsDao.saveAllArticles(it) }
            .subscribe({ Timber.d("Saved all incoming articles") }, { Timber.e(it) })
    }

    private fun castToArticleModel(response: NewsResponse): List<ArticleModel> {
        val articleModels = mutableListOf<ArticleModel>()
        for (article in response.articles) {
            articleModels.add(
                ArticleModel(
                    article.source.id,
                    article.source.id,
                    article.author,
                    article.title,
                    article.description,
                    article.url,
                    article.urlToImage,
                    article.publishedAt,
                    article.content
                )
            )
        }
        return articleModels
    }

    companion object {
        const val API_KEY = "0fbd445510bb49258146c8afdeb6af7e"
        const val DEFAULT_SOURCE_ID = "bbc-news"
    }
}
