package com.kraemericaindustries.getitdone

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Task::class], version = 1)
abstract class GetItDoneDatabase : RoomDatabase() {

}