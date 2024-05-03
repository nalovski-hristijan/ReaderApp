package com.hnalovski.readerapp.repository

import com.hnalovski.readerapp.data.DataOrException
import com.hnalovski.readerapp.model.Item
import com.hnalovski.readerapp.network.BooksAPI
import javax.inject.Inject

class BookRepository @Inject constructor(private val api: BooksAPI) {
    private val dataOrException = DataOrException<List<Item>, Boolean, Exception>()

    private val bookInfoDataException = DataOrException<Item, Boolean, Exception>()

    suspend fun getBooks(searchQuery: String): DataOrException<List<Item>, Boolean, Exception> {
        try {
            dataOrException.loading = true
            dataOrException.data = api.getAllBooks(searchQuery).items
            if (dataOrException.data!!.isNotEmpty()) {
                dataOrException.loading = false
            }
        } catch (e: Exception) {
            dataOrException.e = e
        }
        return dataOrException
    }

    suspend fun getBookInfo(bookId: String): DataOrException<Item, Boolean, Exception> {
        val response = try {
            bookInfoDataException.loading = true
            bookInfoDataException.data = api.getBookInfo(bookId)
            if (bookInfoDataException.data.toString().isNotEmpty()) {
                bookInfoDataException.loading = false
            } else {
            }
        } catch (e: Exception) {
            bookInfoDataException.e = e
        }
        return bookInfoDataException
    }
}