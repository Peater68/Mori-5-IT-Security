package hu.bme.caffshare

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import co.zsmb.rainbowcake.navigation.SimpleNavActivity
import hu.bme.caffshare.ui.admin.UserListFragment
import hu.bme.caffshare.ui.cafflist.CaffListFragment
import hu.bme.caffshare.ui.login.LoginFragment
import hu.bme.caffshare.ui.profile.ProfileFragment
import hu.bme.caffshare.util.NavigationIconClickListener
import kotlinx.android.synthetic.main.activity_main_caff.*
import kotlinx.android.synthetic.main.backdrop.*

class MainActivity : SimpleNavActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_caff)

        setupBackDropMenu()
        setNavigationOnClickListener()

        if (savedInstanceState == null) {
            navigator.add(LoginFragment())
        }
    }

    private fun setupBackDropMenu() {
        admin_menu_button.setOnClickListener {
            navigator.replace(UserListFragment())
        }
        cafflist_menu_button.setOnClickListener {
            navigator.replace(CaffListFragment())
        }
        account_menu_button.setOnClickListener {
            navigator.replace(ProfileFragment())
        }

    }

    private fun setNavigationOnClickListener() {
        setSupportActionBar(app_bar)

        app_bar.setNavigationOnClickListener(
            NavigationIconClickListener(
                this,
                nested_scroll_view,
                openIcon = ContextCompat.getDrawable(this, R.drawable.menu_open_icon),
                closeIcon = ContextCompat.getDrawable(this, R.drawable.menu_close_icon)
            )
        )
    }

    fun hideToolbar() {
        app_bar.visibility = View.GONE
    }

    fun showToolbar() {
        app_bar.visibility = View.VISIBLE
    }
}
