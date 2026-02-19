package com.myna.bell.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Alarm::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MynaDatabase : RoomDatabase() {
    abstract fun alarmDao(): AlarmDao
}
