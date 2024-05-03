package com.hnalovski.readerapp.screens.detail

import androidx.lifecycle.ViewModel
import com.hnalovski.readerapp.data.Resource
import com.hnalovski.readerapp.model.Item
import com.hnalovski.readerapp.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReaderDetailViewModel @Inject constructor(private val repository: BookRepository): ViewModel() {

     suspend fun getBookInfo(bookId: String): Resource<Item> {
        return repository.getBookInfo(bookId)
    }
}