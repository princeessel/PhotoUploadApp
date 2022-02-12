package com.example.photouploadapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.photouploadapp.databinding.FragmentSignUpBinding
import com.example.photouploadapp.model.db.AppDatabase
import com.example.photouploadapp.model.repository.PhotoUploadRepository
import com.example.photouploadapp.viewmodel.SignUpViewModel


class SignUpFragment : Fragment() {
    private lateinit var signUpBinding: FragmentSignUpBinding
    private lateinit var appDatabase: AppDatabase
    private lateinit var repository: PhotoUploadRepository

    private val viewModel by viewModels<SignUpViewModel>()

    var navController: NavController? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        signUpBinding = FragmentSignUpBinding.inflate(inflater, container, false)
        appDatabase = AppDatabase.invoke(activity!!.applicationContext)

        repository = PhotoUploadRepository(appDatabase)

        return signUpBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        navController = Navigation.findNavController(view)
        signUpBinding.completeSignUp.setOnClickListener {
            onSignUp()
        }
        observeViewModel()
    }

    private fun initViewModel() {
        viewModel.init(repository = repository)
    }

    private fun observeViewModel() {
        viewModel.signUpSuccess.observe(this, { isSignUpComplete ->
//            if (isSignUpComplete) {
//                navController!!.navigate(R.id.action_signUpFragment_to_profileFragment)
//            }
//            Toast.makeText(context, "Sign up completed $isSignUpComplete", Toast.LENGTH_LONG).show()
        })

        viewModel.error.observe(viewLifecycleOwner, { error ->
            Toast.makeText(context, "Error: $error", Toast.LENGTH_LONG).show()
        })
    }

    private fun onSignUp() {
        val username = signUpBinding.enterUsername.text.toString().trim()
        val email = signUpBinding.enterEmail.text.toString().trim()
        val password = signUpBinding.enterPassword.text.toString().trim()
        val fullName = signUpBinding.fullname.text.toString().trim()

        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || fullName.isEmpty()) {
            Toast.makeText(context, "Please all fields are required", Toast.LENGTH_LONG).show()
        } else {
            viewModel.signUp(username, email, password, fullName)
            navController!!.navigate(R.id.action_signUpFragment_to_mainFragment)
            Toast.makeText(context, "Account successfully created", Toast.LENGTH_LONG).show()
        }
    }
}
