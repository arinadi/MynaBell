package com.myna.bell.di

import android.content.Context
import androidx.room.Room
import com.myna.bell.data.local.AlarmDao
import com.myna.bell.data.local.MynaDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MynaDatabase {
        return Room.databaseBuilder(
            context,
            MynaDatabase::class.java,
            "myna_bell_db"
        ).fallbackToDestructiveMigration() // For dev phase
         .build()
    }

    @Provides
    @Singleton
    fun provideAlarmDao(database: MynaDatabase): AlarmDao {
        return database.alarmDao()
    }
}
