package com.nk.searchimages.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.nk.searchimages.R
import com.nk.searchimages.databinding.ImageItemBinding

class ImagesAdapter(
    private val context: Context,
    private val imagesList: ArrayList<String>
): RecyclerView.Adapter<ImagesAdapter.ImagesAdapterViewHolder>() {
    inner class ImagesAdapterViewHolder(val binding: ImageItemBinding): ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesAdapterViewHolder {
        return ImagesAdapterViewHolder(
            ImageItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int {

        return imagesList.size
    }

    override fun onBindViewHolder(holder: ImagesAdapterViewHolder, position: Int) {
        val imageUrl = imagesList[position]

        Glide.with(context)
            .load(imageUrl)
            .placeholder(R.drawable.ic_launcher_background)
            .into(holder.binding.imageView)
    }
}