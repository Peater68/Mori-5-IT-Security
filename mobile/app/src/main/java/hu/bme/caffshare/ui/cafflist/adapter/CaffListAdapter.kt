package hu.bme.caffshare.ui.cafflist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hu.bme.caffshare.R
import hu.bme.caffshare.ui.cafflist.model.CaffFile
import hu.bme.caffshare.util.loadCaffPreview
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
            image.loadCaffPreview(caffFile.id)
        }
    }

    inner class CaffFileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val author: TextView = itemView.caffFileAuthorText
        val image: ImageView = itemView.caffFileImage

        var caffFile: CaffFile? = null

        init {
            itemView.setOnClickListener {
                caffFile?.let { listener?.onCaffFileClicked(it) }
            }
        }
    }

    interface Listener {
        fun onCaffFileClicked(file: CaffFile)
    }
}