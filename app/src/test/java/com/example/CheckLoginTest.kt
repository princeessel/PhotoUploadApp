package com.example

import com.example.photouploadapp.view.entities.User
import com.example.photouploadapp.viewmodel.LoginViewModel
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class CheckLoginTest {

    @MockK
    lateinit var viewModel: LoginViewModel



    init {
        MockKAnnotations.init(this)
    }

//    @Test
//    fun `test user login`(){
//        val password = "otis"
//        val user = User("Otis", "otis@xyz.com", password, "Otis Essel")
//
//        Assertions.assertEquals(user, viewModel.loginUser(user, password))
//    }
}