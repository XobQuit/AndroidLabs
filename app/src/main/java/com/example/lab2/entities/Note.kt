package com.example.lab2.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "notes")
class Note(@PrimaryKey(autoGenerate = true)
           val id: Int,
           val title: String,
           val description: String,
           val color: String,
           val tag: String,
           val creationDate: Date,
           val editionDate: Date
) {

}