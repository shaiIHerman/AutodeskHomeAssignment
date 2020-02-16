package com.shai.autodesk.db.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
class ArticleModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Columns.ARTICLE_ID) val articleId: Long,
    @Embedded(prefix = "source_") var source: Source? = null,
    @ColumnInfo(name = Columns.AUTHOR) var author: String?,
    @ColumnInfo(name = Columns.TITLE) var title: String?,
    @ColumnInfo(name = Columns.DESCRIPTION) var description: String?,
    @ColumnInfo(name = Columns.URL) var url: String?,
    @ColumnInfo(name = Columns.URL_TO_IMAGE) var urlToImage: String?,
    @ColumnInfo(name = Columns.PUBLISHED_AT) var publishedAt: String?,
    @ColumnInfo(name = Columns.CONTENT) var content: String?
) {
    constructor(
        sourceId: String,
        sourceName: String,
        author: String,
        title: String,
        description: String,
        url: String,
        urlToImage: String,
        publishedAt: String,
        content: String?
    ) : this(0,
        Source(sourceId, sourceName),
        author,
        title,
        description,
        url,
        urlToImage,
        publishedAt,
        content
    )

    object Columns {
        const val ARTICLE_ID = "articleId"
        const val AUTHOR = "author"
        const val TITLE = "title"
        const val DESCRIPTION = "description"
        const val URL = "url"
        const val URL_TO_IMAGE = "urlToImage"
        const val PUBLISHED_AT = "publishedAt"
        const val CONTENT = "content"
        const val ID = "id"
        const val NAME = "name"
    }

    class Source(
        @ColumnInfo(name = Columns.ID) var sourceId: String,
        @ColumnInfo(name = Columns.NAME) var sourceName: String
    )
}