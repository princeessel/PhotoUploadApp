package com.example.photouploadapp.model.db

import androidx.room.RoomDatabase
import androidx.room.Database
import com.example.photouploadapp.view.entities.Payment
import com.example.photouploadapp.view.entities.User


@Database(
    entities = [User::class, Payment::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun photoUploadDao(): PhotoUploadDao
}
