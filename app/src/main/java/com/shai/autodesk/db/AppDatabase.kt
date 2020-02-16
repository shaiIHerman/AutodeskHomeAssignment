package com.shai.autodesk.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shai.autodesk.db.dao.NewsDao
import com.shai.autodesk.db.model.ArticleModel


@Database(
    entities = [ArticleModel::class],
    version = 1
)

abstract class AppDatabase : RoomDatabase() {

    abstract fun newsDao(): NewsDao
}