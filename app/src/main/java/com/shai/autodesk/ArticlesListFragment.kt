package com.shai.autodesk

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shai.autodesk.db.ArticleModel
import com.shai.autodesk.viewmodel.NewsViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.articles_list_frag.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class ArticlesListFragment : Fragment() {
    private val newsViewModel by viewModel<NewsViewModel>()
    private val activityCallback by lazy { context as IActivityCallback }
    private lateinit var adapter: ArticlesAdapter
    private var compositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.articles_list_frag, container, false)

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        compositeDisposable.add(newsViewModel.data.subscribe({ initArticlesList(it) }, { Timber.e(it) }))
        newsViewModel.refreshData()
    }

    override fun onPause() {
        super.onPause()
        compositeDisposable.clear()
    }

    private fun initArticlesList(articles: List<ArticleModel>) {
        if (::adapter.isInitialized) {
            adapter.refreshList(articles)
        }
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = RecyclerView.VERTICAL
        recyclerView.layoutManager = layoutManager
        adapter = ArticlesAdapter(articles, context!!, object : ArticlesAdapter.ArticleListener {
            override fun onArticleClick(url: String?) {
                if (url != null) {
                    activityCallback.showArticle(url)
                }
            }
        })
        recyclerView.adapter = adapter
    }

    companion object {
        val TAG: String = ArticlesListFragment::class.java.simpleName

        fun newInstance() = ArticlesListFragment()
    }
}
