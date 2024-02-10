package com.kraemericaindustries.getitdone

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity  //  a.k.a, a table
data class Task(
    @PrimaryKey(autoGenerate = true) val taskId: Int = 0,
    val title: String,
    val description: String? = null,
    val isStarred: Boolean = false
)
