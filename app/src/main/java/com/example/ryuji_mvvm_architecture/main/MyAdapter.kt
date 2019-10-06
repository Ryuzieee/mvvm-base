package com.example.ryuji_mvvm_architecture.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ryuji_mvvm_architecture.R

class MyAdapter(private val versionList: ArrayList<Version>) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bindItems(versionList[position])

    override fun getItemCount() = versionList.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(version: Version) {
            val imageView = itemView.findViewById<ImageView>(R.id.imageView)
            Glide.with(itemView.context).load(version.url).into(imageView)
        }
    }
}