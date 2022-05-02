package com.example.photouploadapp.view.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.example.photouploadapp.view.entities.Payment
import com.example.photouploadapp.view.entities.User

data class UserWithPayments(
    @Embedded val user: User,
    @Relation(
        parentColumn = "userId",
        entityColumn = "userId"
    )
    val paymentLists: List<Payment>
)
