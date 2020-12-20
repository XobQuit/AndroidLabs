package com.example.lab2.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.lab2.entities.Note

@Dao
interface NotesDao {
    @Insert
    fun insert(note: Note)

    @Update
    fun update(note: Note)

    @Delete
    fun delete(note: Note)

    @Query("select * from notes")
    fun getAllNotes(): LiveData<List<Note>>

    @Query("select * from notes where id = :id")
    fun getSingleNote(id: Int): LiveData<Note>
}