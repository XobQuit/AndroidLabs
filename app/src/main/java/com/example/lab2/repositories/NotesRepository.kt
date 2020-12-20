package com.example.lab2.repositories

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.example.lab2.daos.NotesDao
import com.example.lab2.databases.NotesDatabase
import com.example.lab2.entities.Note

class NotesRepository(app: Application) {

    var notesDao: NotesDao? = NotesDatabase.getDatabase(app)?.notesDao()

    fun insert(note: Note) {
        InsertAsync(notesDao).execute(note)
    }

    fun delete(note:Note) {
        DeleteAsync(notesDao).execute(note)
    }

    fun update(note:Note) {
        UpdateAsync(notesDao).execute(note)
    }

    fun getAllNotes(): LiveData<List<Note>> {
        return GetAllNotesAsync(notesDao).execute().get()
    }

    fun getSingleNote(id: Int): LiveData<Note> {
        return GetSingleNoteAsync(notesDao).execute(id).get()
    }

    class InsertAsync(notesDao: NotesDao?): AsyncTask<Note, Void, Unit>() {
        var notesDao = notesDao
        override fun doInBackground(vararg p0: Note) {
            notesDao?.insert(p0[0])
        }
    }

    class DeleteAsync(notesDao: NotesDao?): AsyncTask<Note, Void, Unit>(){
        var notesDao = notesDao
        override fun doInBackground(vararg p0: Note) {
            notesDao?.delete(p0[0])
        }
    }

    class UpdateAsync(notesDao: NotesDao?): AsyncTask<Note, Void, Unit>(){
        var notesDao = notesDao
        override fun doInBackground(vararg p0: Note) {
            notesDao?.update(p0[0])
        }
    }

    class GetAllNotesAsync(notesDao: NotesDao?): AsyncTask<Unit, Void, LiveData<List<Note>>>(){
        var notesDao = notesDao
        override fun doInBackground(vararg p0: Unit?): LiveData<List<Note>>? {
            return notesDao?.getAllNotes()
        }
    }

    class GetSingleNoteAsync(notesDao: NotesDao?): AsyncTask<Int, Void, LiveData<Note>>(){
        var notesDao = notesDao
        override fun doInBackground(vararg p0: Int?): LiveData<Note>? {
            return notesDao?.getSingleNote(p0[0]!!)
        }
    }
}