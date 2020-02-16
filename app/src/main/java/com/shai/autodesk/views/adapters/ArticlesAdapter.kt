package com.shai.autodesk.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shai.autodesk.R
import com.shai.autodesk.db.model.ArticleModel
import com.shai.autodesk.utils.DateUtils.Companion.convertToNewFormat

class ArticlesAdapter(
    private var data: List<ArticleModel>,
    private var context: Context,
    private val listener: ArticleListener
) :
    RecyclerView.Adapter<ArticlesAdapter.LocationsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationsViewHolder {
        return LocationsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.article_card, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: LocationsViewHolder, position: Int) {
        holder.apply {
            titleTxt!!.text = data[position].title
            dateTxt!!.text = convertToNewFormat(data[position].publishedAt)
            shortDescTxt!!.text = data[position].description!!
            Glide.with(context)
                .load(data[position].urlToImage)
                .into(mediaImg!!)
        }
    }

    fun refreshList(articles: List<ArticleModel>) {
        this.data = articles
        notifyDataSetChanged()
    }

    inner class LocationsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var titleTxt: TextView? = itemView.findViewById(R.id.titleTxt)
        var shortDescTxt: TextView? = itemView.findViewById(R.id.shortDescTxt)
        var dateTxt: TextView? = itemView.findViewById(R.id.dateTxt)
        var mediaImg: ImageView? = itemView.findViewById(R.id.mediaImg)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            listener.onArticleClick(data[adapterPosition].url)
        }
    }

    interface ArticleListener {
        fun onArticleClick(url: String?)
    }
}