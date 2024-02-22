package com.example.notification_otp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notification_otp.databinding.ActivityLogBinding
import com.example.notification_otp.databinding.ItemRulesBinding

class RvAdapter(val list: List<String>) : RecyclerView.Adapter<RvAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemRulesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun view(item: String) {
            binding.apply {
                tvItem.text = item
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemRulesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view(list[position])
    }

}