package com.gvn.demojetpack.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "notes_table")
data class Note(
    var title: String,
    var description: String,
    var date: String
) : Serializable {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

}