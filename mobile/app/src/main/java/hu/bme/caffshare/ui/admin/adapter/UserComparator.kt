package hu.bme.caffshare.ui.admin.adapter

import androidx.recyclerview.widget.DiffUtil
import hu.bme.caffshare.ui.admin.model.User
import hu.bme.caffshare.ui.cafflist.model.CaffFile

object UserComparator : DiffUtil.ItemCallback<User>() {

    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem == newItem
    }
}