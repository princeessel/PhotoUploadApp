package com.example.photouploadapp.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.photouploadapp.databinding.UserActivitiesListBinding
import com.example.photouploadapp.view.entities.Payment

class UserActivitiesAdapter : RecyclerView.Adapter<UserActivitiesAdapter.ViewHolder>() {
    private var paymentLists = listOf<Payment>()

    @SuppressLint("NotifyDataSetChanged")
    fun setPayments(payments: List<Payment>) {
        paymentLists = payments
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = UserActivitiesListBinding.inflate(inflater, parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val payment = paymentLists[position]
        holder.binding.apply {
            paymentDate.text = payment.date
            accountPaidFrom.text = payment.paidFromAccount
            paidAmount.text = payment.paymentAmount.toString()
        }
    }

    override fun getItemCount(): Int {
        return paymentLists.size
    }

    class ViewHolder(val binding: UserActivitiesListBinding) : RecyclerView.ViewHolder(binding.root)


}
