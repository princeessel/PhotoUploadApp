package com.example.photouploadapp.view.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class User(
    var username: String? = "",
    @ColumnInfo(name = "email")
    var email: String? = "",
    var password: String? = "",
    var fullName: String? = ""
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L
}
