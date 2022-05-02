package com.example.photouploadapp.model.repository

import com.example.photouploadapp.model.db.AppDatabase
import com.example.photouploadapp.view.entities.Payment
import com.example.photouploadapp.view.entities.User
import javax.inject.Inject

class PhotoUploadRepository @Inject constructor(
    private val database: AppDatabase
    ) {
    suspend fun createUser(user: User) = database.photoUploadDao().createUser(user = user)

    suspend fun getUser(username: String, password: String) =
        database.photoUploadDao().getUser(username = username, password = password)

    suspend fun getLoggedInUser(id: Long) =
        database.photoUploadDao().getLoggedInUser(id = id)

    suspend fun createPayment(payment: Payment) = database.photoUploadDao().createPayment(payment)

    suspend fun getPayment(id: Long) = database.photoUploadDao().getPayment(id)

    suspend fun getUserAndPayments() = database.photoUploadDao().getUserAndPayments()

    suspend fun getPaymentList(userId: Long) = database.photoUploadDao().getPaymentList(userId)

    suspend fun getUpdatedUserLoanAmount(balance: Long) = database.photoUploadDao().updateUserLoanAmount(balance)
}
