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
import com.example.photouploadapp.databinding.FragmentProfileBinding
import com.example.photouploadapp.model.db.AppDatabase
import com.example.photouploadapp.model.repository.PhotoUploadRepository
import com.example.photouploadapp.utils.PrefManager
import com.example.photouploadapp.viewmodel.ProfileViewModel

class ProfileFragment : Fragment() {

    private lateinit var bindingProfile: FragmentProfileBinding
    private lateinit var appDatabase: AppDatabase
    private lateinit var repository: PhotoUploadRepository
    private val viewModel by viewModels<ProfileViewModel>()

    var navController: NavController? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bindingProfile = FragmentProfileBinding.inflate(inflater, container, false)
        appDatabase = AppDatabase.invoke(activity!!.applicationContext)
        repository = PhotoUploadRepository(appDatabase)

        return bindingProfile.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        navController = Navigation.findNavController(view)
        //TODO 3. Get userId from Shared Preference and use it to get user data from room db
        val userId = PrefManager.retrieveUserId(requireActivity())
        getLoggedInUserData(userId)

        bindingProfile.logout.setOnClickListener {
            logout()
        }

        initObservers()
    }

    private fun initViewModel() {
        viewModel.init(repository)
    }

    private fun getLoggedInUserData(id: Long) {
        viewModel.getUserData(id = id)
    }

    //TODO 4. To logout delete userId from Shared Preference
    private fun logout() {
        PrefManager.clearUserId(requireActivity())
        navController!!.navigate(R.id.action_profileFragment_to_mainFragment)
    }

    private fun initObservers() {
        viewModel.loggedInUser.observe(viewLifecycleOwner, {
            bindingProfile.fullname.text = it.fullName
        })

        viewModel.error.observe(viewLifecycleOwner, { error ->
            Toast.makeText(context, "Error: $error", Toast.LENGTH_LONG).show()
        })
    }

}
