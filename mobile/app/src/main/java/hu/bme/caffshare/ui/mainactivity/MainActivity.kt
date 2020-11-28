package hu.bme.caffshare.ui.mainactivity

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import co.zsmb.rainbowcake.base.OneShotEvent
import co.zsmb.rainbowcake.dagger.getViewModelFromFactory
import co.zsmb.rainbowcake.navigation.NavActivity
import hu.bme.caffshare.R
import hu.bme.caffshare.ui.admin.UserListFragment
import hu.bme.caffshare.ui.cafflist.CaffListFragment
import hu.bme.caffshare.ui.login.LoginFragment
import hu.bme.caffshare.ui.profile.ProfileFragment
import hu.bme.caffshare.util.NavigationIconClickListener
import kotlinx.android.synthetic.main.activity_main_caff.*
import kotlinx.android.synthetic.main.backdrop.*

class MainActivity : NavActivity<MainViewState, MainViewModel>() {

    override fun provideViewModel() = getViewModelFromFactory()
    override fun render(viewState: MainViewState) = Unit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_caff)

        setupBackDropMenu()
        setNavigationOnClickListener()
        viewModel.load()
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

    override fun onEvent(event: OneShotEvent) {
        when (event) {
            is MainViewModel.LoginNeeded -> {
                navigator.add(LoginFragment())
            }
            is MainViewModel.UserLoggedIn -> {
                navigator.add(CaffListFragment())
            }
        }
        mainViewFlipper.displayedChild = 1
    }

    fun hideToolbar() {
        app_bar.visibility = View.GONE
    }

    fun showToolbar() {
        app_bar.visibility = View.VISIBLE
    }
}
