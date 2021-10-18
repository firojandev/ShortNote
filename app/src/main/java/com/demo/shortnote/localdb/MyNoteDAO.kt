package com.demo.shortnote.localdb

import androidx.room.*
import com.demo.shortnote.model.MyNote
import kotlinx.coroutines.flow.Flow

@Dao
interface MyNoteDAO {
    @Insert
    suspend fun createNote(myNote: MyNote):Long

    @Update
    suspend fun updateNote(myNote: MyNote):Int

    @Delete
    suspend fun deleteNote(myNote: MyNote):Int

    @Query("Delete from tbl_my_notes")
    suspend fun deleteAll():Int

    @Query("SELECT * FROM tbl_my_notes")
    fun getAllMyNotes(): Flow<List<MyNote>>

}