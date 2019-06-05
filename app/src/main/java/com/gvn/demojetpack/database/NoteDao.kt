package com.gvn.demojetpack.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.gvn.demojetpack.models.Note

@Dao
interface NoteDao {
    @Insert
    fun insert(note: Note)

    @Query("DELETE FROM notes_table")
    fun deleteAllNotes()

    @Delete
    fun deleteNote(note: Note)

    @Query("SELECT * FROM notes_table ")
    fun getAllNotes(): List<Note>

    @Update
    fun update(note: Note)
}