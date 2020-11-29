package hu.bme.caffshare.ui.mainactivity

import android.os.Bundle
import co.zsmb.rainbowcake.base.OneShotEvent
import co.zsmb.rainbowcake.dagger.getViewModelFromFactory
import co.zsmb.rainbowcake.navigation.NavActivity
import hu.bme.caffshare.R
import hu.bme.caffshare.domain.model.DomainRole
import hu.bme.caffshare.ui.admin.UserListFragment
import hu.bme.caffshare.ui.cafflist.CaffListFragment
import hu.bme.caffshare.ui.login.LoginFragment
import hu.bme.caffshare.ui.profile.ProfileFragment
import kotlinx.android.synthetic.main.activity_main_caff.*

class MainActivity : NavActivity<MainViewState, MainViewModel>() {

    override fun provideViewModel() = getViewModelFromFactory()
    override fun render(viewState: MainViewState) = Unit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_caff)

        viewModel.load()
        bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.caffList -> {
                    navigator.replace(CaffListFragment())
                    true
                }
                R.id.admin -> {
                    navigator.replace(UserListFragment())
                    true
                }
                R.id.profile -> {
                    navigator.replace(ProfileFragment())
                    true
                }
                else -> false
            }
        }
    }

    override fun onEvent(event: OneShotEvent) {
        when (event) {
            is MainViewModel.LoginNeeded -> {
                navigator.add(LoginFragment())
            }
            is MainViewModel.UserLoggedIn -> {
                if (event.role != DomainRole.ADMIN) {
                    bottomNav.menu.removeItem(R.id.admin)
                }

                navigator.add(CaffListFragment())
            }
        }
        mainViewFlipper.displayedChild = 1
    }
}
