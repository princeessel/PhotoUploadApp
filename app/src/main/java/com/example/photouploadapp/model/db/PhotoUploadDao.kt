package com.example.photouploadapp.model.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.photouploadapp.view.entities.User

@Dao
interface PhotoUploadDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createUser(user: User)

    @Query("SELECT * FROM user WHERE username = :username AND password= :password")
    suspend fun getUser( username: String, password: String): User

    @Query("SELECT * FROM user WHERE id = :id")
    suspend fun getLoggedInUser( id: Long): User

//    @Query("SELECT * FROM user WHERE id = :id")
//    suspend fun logout(id: String): User
}
