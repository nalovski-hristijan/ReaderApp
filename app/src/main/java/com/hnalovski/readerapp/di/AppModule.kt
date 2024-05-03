package com.hnalovski.readerapp.di

import com.hnalovski.readerapp.network.BooksAPI
import com.hnalovski.readerapp.repository.BookRepository
import com.hnalovski.readerapp.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideBookRepository(api: BooksAPI) = BookRepository(api)

    @Singleton
    @Provides
    fun provideBookApi(): BooksAPI {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BooksAPI::class.java)
    }
}