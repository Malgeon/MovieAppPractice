package com.example.movieapppractice.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapppractice.R
import com.example.movieapppractice.data.model.CommentList
import com.example.movieapppractice.databinding.ItemCommentBinding

class CommentAdapter : RecyclerView.Adapter<CommentAdapter.ViewHolder>() {

    data class CommentItemData(
        val commentId: Int,
        val commentWriter: String,
        val contents: String,
        val recommendCount: Int
    )

    private val commentItems = mutableListOf<CommentItemData>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var binding: ItemCommentBinding = ItemCommentBinding.bind(itemView)

        fun bind(item: CommentItemData) {
            with(binding) {
                writerId.text = item.commentWriter
                recommendCount.text = "${item.recommendCount}"
                reviewText.text = item.contents
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false)
    )

    override fun getItemCount() = commentItems.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(commentItems[position])
    }

    fun addItem(data: ArrayList<CommentList>) {
        data.forEach {
            commentItems.add(CommentItemData(it.id, it.writer, it.contents, it.recommend))
        }
        notifyDataSetChanged()
    }
}