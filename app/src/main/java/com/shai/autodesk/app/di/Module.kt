package com.shai.autodesk.app.di

import android.app.Application
import androidx.room.Room
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.shai.autodesk.BuildConfig
import com.shai.autodesk.net.NewsApi
import com.shai.autodesk.db.repo.NewsRepository
import com.shai.autodesk.db.AppDatabase
import com.shai.autodesk.db.dao.NewsDao
import com.shai.autodesk.viewmodel.NewsViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val viewModelModule = module {
    viewModel { NewsViewModel(get()) }
}

val apiModule = module {
    fun provideNewsApi(retrofit: Retrofit): NewsApi {
        return retrofit.create(NewsApi::class.java)
    }

    single { provideNewsApi(get()) }
}

val netModule = module {

    fun provideHttpClient(): OkHttpClient {

        val okHttpClientBuilder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG)
            okHttpClientBuilder.addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
        return okHttpClientBuilder.build()
    }

    fun provideGson(): Gson {
        return GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create()
    }


    fun provideRetrofit(factory: Gson, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create(factory))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()
    }

    single { provideHttpClient() }
    single { provideGson() }
    single { provideRetrofit(get(), get()) }

}

val databaseModule = module {

    fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "news_articles")
            .fallbackToDestructiveMigration()
            .build()
    }


    fun provideDao(database: AppDatabase): NewsDao {
        return database.newsDao()
    }

    single { provideDatabase(androidApplication()) }
    single { provideDao(get()) }
}

val repositoryModule = module {
    fun provideNewsRepository(api: NewsApi, dao: NewsDao): NewsRepository {
        return NewsRepository(api, dao)
    }

    single { provideNewsRepository(get(), get()) }
}