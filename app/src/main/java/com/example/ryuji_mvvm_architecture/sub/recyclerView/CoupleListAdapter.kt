package com.example.ryuji_mvvm_architecture.sub.recyclerView

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.ryuji_mvvm_architecture.R

class CoupleListAdapter(private val mContext: Context, private val itemsList: List<String>) :
    RecyclerView.Adapter<CoupleListAdapter.ItemRowHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ItemRowHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_couple_card, null)
        return ItemRowHolder(view)
    }

    override fun onBindViewHolder(holder: ItemRowHolder, i: Int) {
        val url = itemsList[i]
        /* Glide.with(mContext)
                .load(feedItem.getImageURL())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .error(R.drawable.bg)
                .into(feedListRowHolder.thumbView);*/
    }

    override fun getItemCount() = itemsList.size

    inner class ItemRowHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {
            val coupleImage = view.findViewById<View>(R.id.couple_image) as ImageView
            view.setOnClickListener { v -> Toast.makeText(v.context, "", Toast.LENGTH_SHORT).show() }
        }
    }
}