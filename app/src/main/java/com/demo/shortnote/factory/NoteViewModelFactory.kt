package com.demo.shortnote.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.demo.shortnote.repository.MyNoteRepository
import com.demo.shortnote.viewmodel.NoteViewModel
import java.lang.IllegalArgumentException

class NoteViewModelFactory(private val repository: MyNoteRepository):ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteViewModel::class.java)){
            return NoteViewModel(repository) as T
        }
        throw IllegalArgumentException("Not view model class found")
    }
}