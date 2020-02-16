package com.shai.autodesk

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.article_frag.*

class ArticleFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.article_frag, container, false)

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        articleWebView!!.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                if (url != null) { // if back clicked before load
                    articleWebView.loadUrl(url)
                }
                return false
            }
        }
        articleWebView.settings.javaScriptEnabled = true
        articleWebView.loadUrl(arguments!!.getString(URL))
    }

    companion object {
        val TAG: String = ArticleFragment::class.java.simpleName
        const val URL = "url"

        fun newInstance(url: String) = ArticleFragment().apply {
            arguments = bundleOf(URL to url)
        }
    }
}
