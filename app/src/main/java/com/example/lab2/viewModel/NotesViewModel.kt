package com.example.lab2.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.lab2.entities.Note
import com.example.lab2.repositories.NotesRepository

class NotesViewModel(app: Application): AndroidViewModel(app) {

    var repository:NotesRepository = NotesRepository(app)

    override fun onCleared() {
        super.onCleared()
        Log.i("NotesViewModel", "NotesViewModel destroyed!")
    }

    fun insert(note: Note) {
        repository.insert(note)
    }

    fun delete(note: Note) {
        repository.delete(note)
    }

    fun update(note: Note) {
        repository.update(note)
    }

    fun getAllNotes(): LiveData<List<Note>> {
        return repository.getAllNotes()
    }
}