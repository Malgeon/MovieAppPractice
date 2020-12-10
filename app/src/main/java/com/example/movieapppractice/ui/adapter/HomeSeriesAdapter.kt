package com.example.movieapppractice.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapppractice.R
import com.example.movieapppractice.data.model.MovieHome
import com.example.movieapppractice.databinding.ItemSeriesBinding
import com.squareup.picasso.Picasso

class HomeSeriesAdapter : RecyclerView.Adapter<HomeSeriesAdapter.ViewHolder>() {

    data class SeriesItem(var thumbUrl: String, var title: String)
    private val seriesItems = mutableListOf<SeriesItem>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var binding: ItemSeriesBinding = ItemSeriesBinding.bind(itemView)

        fun bind(item: SeriesItem) {
            with(binding) {
                root.context?.let {
                    Picasso.get().load(item.thumbUrl).into(seriesItemImage)
                }
            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.item_series, parent, false)
    )

    override fun getItemCount() = seriesItems.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(seriesItems[position])
    }

    fun addItem(data: ArrayList<MovieHome.MovieData>) {
        data.forEach {
            seriesItems.add(SeriesItem(it.thumb, it.title))
        }
        notifyDataSetChanged()
    }
}