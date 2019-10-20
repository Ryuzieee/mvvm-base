package com.example.ryuji_mvvm_architecture.sub.recyclerView

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ryuji_mvvm_architecture.databinding.ItemCoupleCardBinding

class CoupleListAdapter
constructor(
    private val mContext: Context,
    private val itemsList: List<String>
) : RecyclerView.Adapter<CoupleListAdapter.BindingHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemCoupleCardBinding.inflate(layoutInflater, parent, false)
        return BindingHolder(binding)
    }

    override fun onBindViewHolder(holder: BindingHolder, position: Int) {
        val urlModel = UrlModel(itemsList[position])
        holder.binding.apply {
            model = urlModel
            originalLinearLayout.setOnClickListener {}
        }
    }

    override fun getItemCount(): Int = itemsList.size

    class BindingHolder(var binding: ItemCoupleCardBinding) : RecyclerView.ViewHolder(binding.root)
}

data class UrlModel(val name: String)