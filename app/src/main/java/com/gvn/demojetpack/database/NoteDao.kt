package com.gvn.demojetpack.database

import androidx.room.*
import com.gvn.demojetpack.models.Note
import io.reactivex.Flowable

@Dao
interface NoteDao {
    @Insert
    fun insert(note: Note): Long

    @Query("DELETE FROM notes_table")
    fun deleteAllNotes()

    @Delete
    fun deleteNote(note: Note): Int

    @Query("SELECT * FROM notes_table ")
    fun getAllNotes(): Flowable<List<Note>>

    @Update
    fun update(note: Note): Int
}