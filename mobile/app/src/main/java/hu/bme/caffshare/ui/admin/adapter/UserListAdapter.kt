package hu.bme.caffshare.ui.admin.adapter

import android.graphics.Color.rgb
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import hu.bme.caffshare.R
import hu.bme.caffshare.ui.admin.model.User
import kotlinx.android.synthetic.main.row_user.view.*

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

    fun getUserAt(position: Int): User = getItem(position)

    inner class UserViewHolder(itemView: View) :
            RecyclerView.ViewHolder(itemView),
            View.OnCreateContextMenuListener
    {

        val firstName: TextView = itemView.firstNameText
        val lastName: TextView = itemView.lastNameText
        val email: TextView = itemView.emailText
        val username: TextView = itemView.usernameText

        val card: MaterialCardView = itemView.userCard

        var user: User? = null

        init {
            itemView.banUserButton.setOnClickListener {
                user?.let { listener?.onListItemDeleteButtonClicked(it) }
            }

            itemView.setOnCreateContextMenuListener(this)
        }

        override fun onCreateContextMenu(
                menu: ContextMenu?,
                v: View?,
                menuInfo: ContextMenu.ContextMenuInfo?
        )  {
            menu?.setHeaderTitle("Mit szeretnél tenni?");
            menu?.add(adapterPosition, v?.id!!, 0, "Adminná teszem")
            menu?.add(adapterPosition, v?.id!!, 0, "Letiltom")
        }
    }

    interface Listener {
        fun onListItemDeleteButtonClicked(item: User)
    }
}