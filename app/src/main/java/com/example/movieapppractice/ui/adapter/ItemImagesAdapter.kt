package com.example.movieapppractice.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapppractice.R
import com.example.movieapppractice.databinding.ItemImageBinding
import com.squareup.picasso.Picasso

class ItemImagesAdapter : RecyclerView.Adapter<ItemImagesAdapter.ViewHolder>() {

    private val imageUrls = mutableListOf<String>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var binding: ItemImageBinding = ItemImageBinding.bind(itemView)

        fun bind(url: String) {
            with(binding) {
                root.context?.let {
                    Picasso.get().load(url).into(seriesItemImage)
                }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
    )

    override fun getItemCount() = imageUrls.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(imageUrls[position])
    }

    fun addItem(data: List<String>?) {
        data?.let {
            it.forEach { url ->
                imageUrls.add(url)
            }
            notifyDataSetChanged()
        } ?: run {
            imageUrls.clear()
            notifyDataSetChanged()
        }
    }
}