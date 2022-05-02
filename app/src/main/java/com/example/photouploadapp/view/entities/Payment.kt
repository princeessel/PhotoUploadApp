package com.example.photouploadapp.view.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "payment")
data class Payment(
    var userId: Long,
    val paymentAmount: Long,
    val date: String,
    val paidFromAccount: String,
    @PrimaryKey(autoGenerate = true) var paymentId: Long = 0L
)
