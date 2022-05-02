package com.example.photouploadapp

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.photouploadapp.component.MyApplication
import com.example.photouploadapp.databinding.FragmentUserActivitiesBinding
import com.example.photouploadapp.utils.PrefManager
import com.example.photouploadapp.view.adapter.UserActivitiesAdapter
import com.example.photouploadapp.viewmodel.UserActivitiesViewModel
import javax.inject.Inject

class UserActivitiesFragment : Fragment() {
    private lateinit var binding: FragmentUserActivitiesBinding

    private val userActivitiesAdapter = UserActivitiesAdapter()

    @Inject
    lateinit var viewModel: UserActivitiesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentUserActivitiesBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        injectDagger()
    }

    private fun injectDagger() {
        (requireActivity().application as MyApplication).appComponent
            .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = userActivitiesAdapter
        }

        val userId = PrefManager.retrieveUserId(requireActivity())

        getPaymentList(userId)
        initObservers()
    }

    private fun getPaymentList(userId: Long) {
        viewModel.getPaymentList(userId)
    }

    private fun initObservers() {
        viewModel.payments.observe(viewLifecycleOwner, {
            if(it.isNotEmpty()) {
                binding.noPaymentMade.isVisible = false
                binding.recyclerView.isVisible = true
                userActivitiesAdapter.setPayments(it)
            } else {
                binding.noPaymentMade.isVisible = true
            }
        })
    }
}
