package hu.bme.caffshare.ui.cafflist.adapter

import androidx.recyclerview.widget.DiffUtil
import hu.bme.caffshare.ui.cafflist.model.CaffFile

object CaffFileComparator : DiffUtil.ItemCallback<CaffFile>() {

    override fun areItemsTheSame(oldItem: CaffFile, newItem: CaffFile): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CaffFile, newItem: CaffFile): Boolean {
        return oldItem == newItem
    }
}