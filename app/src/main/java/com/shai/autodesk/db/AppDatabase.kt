package com.shai.autodesk.db

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [ArticleModel::class],
    version = 1
)

abstract class AppDatabase : RoomDatabase() {

    abstract fun newsDao(): NewsDao
}