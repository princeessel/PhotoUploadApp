package com.example.photouploadapp.view.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    var username: String? = "",
    @ColumnInfo(name = "email")
    var email: String? = "",
    var password: String? = "",
    var fullName: String? = "",
    var loanAmount: Long?,
    @PrimaryKey(autoGenerate = true)
    var userId: Long = 0L,
)
