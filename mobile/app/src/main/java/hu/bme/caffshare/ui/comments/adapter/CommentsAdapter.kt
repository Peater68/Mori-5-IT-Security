package hu.bme.caffshare.ui.comments.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hu.bme.caffshare.R
import hu.bme.caffshare.ui.comments.model.Comment
import kotlinx.android.synthetic.main.row_comment.view.*

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

        with(holder) {
            text.text = comment.text
            author.text = comment.author
            if (comment.isDeletable) {
                deleteButton.visibility = View.VISIBLE
            } else {
                deleteButton.visibility = View.GONE
            }
        }
    }

    inner class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val author: TextView = itemView.commentAuthorText
        val text: TextView = itemView.commentText
        val deleteButton: ImageView = itemView.deleteCommentButton
        // TODO: add date field for view

        var comment: Comment? = null

        init {
            deleteButton.setOnClickListener {
                comment?.let { listener?.deleteComment(it) }
            }
        }
    }

    interface Listener {
        fun deleteComment(comment: Comment)
    }
}