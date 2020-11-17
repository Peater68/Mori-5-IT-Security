package hu.bme.caffshare.ui.admin.adapter

import android.graphics.Color.rgb
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import hu.bme.caffshare.R
import hu.bme.caffshare.ui.admin.model.User
import hu.bme.caffshare.ui.cafflist.model.CaffFile
import kotlinx.android.synthetic.main.row_caff_file.view.*
import kotlinx.android.synthetic.main.row_user.view.*
import org.w3c.dom.Text

class UserListAdapter :
    ListAdapter<User, UserListAdapter.UserViewHolder>(UserComparator) {

    var listener: Listener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.row_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user: User = getItem(position)
        holder.user = user

        with(holder) {
            firstName.text = user.firstName
            lastName.text = user.lastName
            email.text = user.email
            username.text = user.username

            if (user.isBanned) card.strokeColor = rgb(255,0,0)
        }


    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val firstName: TextView = itemView.user_firstName_textView
        val lastName: TextView = itemView.user_lastName_textView
        val email: TextView = itemView.user_email_textView
        val username: TextView = itemView.user_username_textView

        val card = itemView.userCard

        var user: User? = null

        init {
            itemView.setOnClickListener {
                user?.let { listener?.onListItemClicked(it) }
            }
        }
    }

    interface Listener {
        fun onListItemClicked(item: User)
    }
}