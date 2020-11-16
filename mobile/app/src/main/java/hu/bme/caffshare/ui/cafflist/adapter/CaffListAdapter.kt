package hu.bme.caffshare.ui.cafflist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import hu.bme.caffshare.R
import hu.bme.caffshare.ui.cafflist.model.CaffFile
import kotlinx.android.synthetic.main.row_caff_file.view.*

class CaffListAdapter :
    ListAdapter<CaffFile, CaffListAdapter.CaffFileViewHolder>(CaffFileComparator) {

    var listener: Listener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CaffFileViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.row_caff_file, parent, false)
        return CaffFileViewHolder(view)
    }

    override fun onBindViewHolder(holder: CaffFileViewHolder, position: Int) {
        val caffFile: CaffFile = getItem(position)
        holder.caffFile = caffFile

        with(holder) {
            author.text = caffFile.author
            Glide.with(itemView)
                .load(caffFile.imageUrl)
                .into(image)
        }
    }

    inner class CaffFileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val author: TextView = itemView.caffFileAuthorText
        val image: ImageView = itemView.caffFileImage

        var caffFile: CaffFile? = null

        init {
            itemView.setOnClickListener {
                caffFile?.let { listener?.onAssetClicked(it) }
            }
        }
    }

    interface Listener {
        fun onAssetClicked(file: CaffFile)
    }
}