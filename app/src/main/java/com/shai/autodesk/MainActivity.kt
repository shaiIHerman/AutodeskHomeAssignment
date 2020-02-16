package com.shai.autodesk

import android.os.Bundle
import android.view.MenuItem

class MainActivity : BaseActivity(), IActivityCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (supportActionBar != null) {
            supportActionBar!!.title = getString(R.string.title)
        }
        if (savedInstanceState == null) {
            replaceFragment(
                R.id.containerLay,
                ArticlesListFragment.newInstance(),
                ArticlesListFragment.TAG
            )
                .commit()
        }
    }

    override fun showArticle(url: String) {
        replaceFragment(R.id.containerLay, ArticleFragment.newInstance(url), ArticleFragment.TAG)
            .commit()
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
