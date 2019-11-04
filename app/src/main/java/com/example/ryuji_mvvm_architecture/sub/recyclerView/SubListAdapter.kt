package com.example.ryuji_mvvm_architecture.sub.recyclerView

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ryuji_mvvm_architecture.databinding.ItemCoupleCardBinding

class SubListAdapter
constructor(
    private val mContext: Context,
    private val mItemsList: List<String>
) : RecyclerView.Adapter<SubListAdapter.BindingHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemCoupleCardBinding.inflate(layoutInflater, parent, false)
        return BindingHolder(binding)
    }

    override fun onBindViewHolder(holder: BindingHolder, position: Int) {
        val urlModel = UrlModel(mItemsList[position])
        holder.binding.apply {
            model = urlModel
            originalLinearLayout.setOnClickListener {}
        }
    }

    override fun getItemCount(): Int = mItemsList.size

    class BindingHolder(var binding: ItemCoupleCardBinding) : RecyclerView.ViewHolder(binding.root)
}

data class UrlModel(val url: String)