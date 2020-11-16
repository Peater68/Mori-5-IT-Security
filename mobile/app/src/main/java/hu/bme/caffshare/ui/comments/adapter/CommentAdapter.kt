package hu.bme.caffshare.ui.comments.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hu.bme.caffshare.R
import hu.bme.caffshare.ui.comments.model.Comment

class CommentsAdapter :
    ListAdapter<Comment, CommentsAdapter.CommentViewHolder>(CommentsComparator) {

    var listener: Listener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.row_comment, parent, false)
        return CommentViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment: Comment = getItem(position)
        holder.comment = comment

    }

    inner class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var comment: Comment? = null

        init {
            itemView.setOnClickListener {
                comment?.let { listener?.onAssetClicked(it) }
            }
        }
    }

    interface Listener {
        fun onAssetClicked(comment: Comment)
    }
}