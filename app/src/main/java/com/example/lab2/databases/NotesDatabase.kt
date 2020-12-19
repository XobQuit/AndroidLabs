package com.example.lab2.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.lab2.daos.NotesDao
import com.example.lab2.entities.Note

@Database(entities = arrayOf(Note::class),version = 1)
abstract class NotesDatabase: RoomDatabase(){
    abstract fun notesDao(): NotesDao

    companion object{
        var instance:NotesDatabase? = null
        fun getDatabase(context: Context):NotesDatabase?{
            if (instance == null) {
                instance = Room.databaseBuilder(context.applicationContext, NotesDatabase::class.java,
                    "notes_database").build()
            }
            return instance
        }
    }

}