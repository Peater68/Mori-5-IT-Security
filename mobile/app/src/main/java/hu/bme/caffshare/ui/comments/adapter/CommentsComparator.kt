package hu.bme.caffshare.ui.comments.adapter

import androidx.recyclerview.widget.DiffUtil
import hu.bme.caffshare.ui.comments.model.Comment

object CommentsComparator : DiffUtil.ItemCallback<Comment>() {

    override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean {
        return oldItem == newItem
    }
}