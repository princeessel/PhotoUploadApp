package com.example.photouploadapp.module

import android.app.Application
import androidx.room.Room
import com.example.photouploadapp.model.db.AppDatabase

import dagger.Module
import dagger.Provides

import javax.inject.Singleton

@Module
class RoomDatabaseModule(application: Application) {
    private var appContext = application


    @Singleton
    @Provides
    fun providesRoomDatabase(): AppDatabase {
        return Room.databaseBuilder(
            appContext.applicationContext,
            AppDatabase::class.java,
            "photo_upload_database"
        ).build()
    }

    @Singleton
    @Provides
    fun providesPhotoUploadDao(appDatabase: AppDatabase) = appDatabase.photoUploadDao()
}
