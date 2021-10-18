package com.demo.shortnote.repository

import com.demo.shortnote.localdb.MyNoteDAO
import com.demo.shortnote.model.MyNote

class MyNoteRepository(private val dao: MyNoteDAO) {
    val myNotes = dao.getAllMyNotes()

    suspend fun insert(myNote: MyNote): Long {
        return dao.createNote(myNote)
    }

    suspend fun update(myNote: MyNote): Int {
        return dao.updateNote(myNote)
    }

    suspend fun delete(myNote: MyNote): Int {
        return dao.deleteNote(myNote)
    }

    suspend fun deleteAll(): Int {
        return dao.deleteAll()
    }

}