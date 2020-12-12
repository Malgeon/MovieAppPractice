package com.example.movieapppractice.ui.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapppractice.R
import com.example.movieapppractice.data.model.MovieData
import com.example.movieapppractice.databinding.ItemHomeBinding
import com.squareup.picasso.Picasso

class HomePagerAdapter(
    private val clicked: (data: PagerItem) -> Unit
) : RecyclerView.Adapter<HomePagerAdapter.ViewHolder>() {

    data class PagerItem(var movieId: Int, var imageUrl: String, var category: Int)
    private val pagerItems = mutableListOf<PagerItem>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var binding: ItemHomeBinding = ItemHomeBinding.bind(itemView)

        fun bind(item: PagerItem) {
            with(binding) {
                root.context?.let {
                    Picasso.get().load(item.imageUrl).into(pagerImage)
                }
                root.setOnClickListener { clicked.invoke(item) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.item_home, parent, false)
    )

    override fun getItemCount() = pagerItems.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(pagerItems[position])
    }

    fun addItem(data: ArrayList<MovieData>) {
        data.forEach {
            pagerItems.add(PagerItem(it.id, it.image,1))
        }
        notifyDataSetChanged()
    }

}