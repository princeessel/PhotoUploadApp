package com.example.photouploadapp.model.db

import androidx.room.*
import com.example.photouploadapp.view.entities.Payment
import com.example.photouploadapp.view.entities.User
import com.example.photouploadapp.view.relation.UserWithPayments

@Dao
interface PhotoUploadDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createUser(user: User)

    @Query("SELECT * FROM user WHERE username = :username AND password= :password")
    suspend fun getUser( username: String, password: String): User

    @Query("SELECT * FROM user WHERE userId = :id")
    suspend fun getLoggedInUser( id: Long): User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createPayment(payment: Payment)

    @Query("SELECT * FROM payment WHERE paymentId = :id")
    suspend fun getPayment( id: Long): Payment

    @Query("SELECT * FROM payment WHERE userId = :userId")
    suspend fun getPaymentList(userId: Long): List<Payment>

    @Transaction
    @Query("UPDATE user SET loanAmount = :balance")
    suspend fun updateUserLoanAmount(balance: Long)


    @Transaction
    @Query("SELECT * FROM user")
    suspend fun getUserAndPayments(): List<UserWithPayments>
}
