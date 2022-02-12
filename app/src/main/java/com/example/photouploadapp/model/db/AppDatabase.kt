package com.example.photouploadapp.model.db

import android.content.Context
import androidx.room.RoomDatabase
import androidx.room.Database
import androidx.room.Room
import com.example.photouploadapp.view.entities.User


@Database(
    entities = [User::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun photoUploadDao(): PhotoUploadDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        private val LOCK = Any()

        operator fun invoke(context: Context) = INSTANCE ?: synchronized(LOCK) {
            INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "photo_upload_database"
            ).build().also {
                INSTANCE = it
            }
        }
    }
}
