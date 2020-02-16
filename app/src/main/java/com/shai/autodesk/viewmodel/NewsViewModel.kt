package com.shai.autodesk.viewmodel

import androidx.lifecycle.ViewModel
import com.shai.autodesk.db.repo.NewsRepository
import com.shai.autodesk.db.model.ArticleModel
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers

class NewsViewModel(private val newsRepository: NewsRepository) : ViewModel() {

    val data: Flowable<List<ArticleModel>> = newsRepository.data.observeOn(AndroidSchedulers.mainThread())

    fun refreshData() {
        newsRepository.refresh()
    }
}