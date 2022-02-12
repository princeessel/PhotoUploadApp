package com.example.photouploadapp.model.repository

import com.example.photouploadapp.model.db.AppDatabase
import com.example.photouploadapp.view.entities.User
import javax.inject.Inject

class PhotoUploadRepository @Inject constructor(private val database: AppDatabase) {
    suspend fun createUser(user: User) = database.photoUploadDao().createUser(user = user)

    suspend fun getUser(username: String, password: String) =
        database.photoUploadDao().getUser(username = username, password = password)

    suspend fun getLoggedInUser(id: Long) =
        database.photoUploadDao().getLoggedInUser(id = id)

//    suspend fun logout(id: String) =
//        database.photoUploadDao().logout(id = id)
}
