package hu.bme.caffshare.ui.profile

import android.os.Bundle
import android.view.View
import co.zsmb.rainbowcake.base.OneShotEvent
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.dagger.getViewModelFromFactory
import co.zsmb.rainbowcake.navigation.navigator
import hu.bme.caffshare.R
import hu.bme.caffshare.ui.login.LoginFragment
import hu.bme.caffshare.ui.profile.adapter.ProfileListsAdapter
import hu.bme.caffshare.ui.profile.dialog.ChangePasswordDialogFragment
import hu.bme.caffshare.ui.profile.dialog.EditProfileDialogFragment
import hu.bme.caffshare.ui.profile.model.ProfileUpdateData
import hu.bme.caffshare.util.showErrorSnackBar
import hu.bme.caffshare.util.showSuccessSnackBar
import hu.bme.caffshare.util.toolbar
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.layout_profile.*

class ProfileFragment : RainbowCakeFragment<ProfileViewState, ProfileViewModel>(),
    ChangePasswordDialogFragment.Listener,
    EditProfileDialogFragment.Listener {

    override fun provideViewModel() = getViewModelFromFactory()
    override fun getViewResource() = R.layout.fragment_profile

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewPager()
        setupToolbar()
    }

    private fun setupViewPager() {
        profileViewPager.adapter = ProfileListsAdapter(childFragmentManager)
    }

    private fun setupToolbar() {
        activity!!.menuInflater.inflate(R.menu.profile_toolbar_menu, toolbar.menu)
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.editUser -> {
                    viewModel.showEditProfileDialog()
                    true
                }
                R.id.changePassword -> {
                    ChangePasswordDialogFragment().show(
                        childFragmentManager,
                        ChangePasswordDialogFragment.TAG
                    )
                    true
                }
                R.id.logout -> {
                    viewModel.logout()
                    true
                }
                else -> false
            }
        }
    }


    override fun onDestroy() {
        toolbar.menu.clear()

        super.onDestroy()
    }

    override fun onStart() {
        super.onStart()

        viewModel.load()
    }

    override fun onEvent(event: OneShotEvent) {
        when (event) {
            is ProfileViewModel.PasswordChangeSuccessful -> {
                showSuccessSnackBar("Successfully changed password!")
            }
            is ProfileViewModel.PasswordChangeError -> {
                showErrorSnackBar("Error while changing password!")
            }
            is ProfileViewModel.LoggedOut -> {
                navigator?.setStack(LoginFragment())
            }
            is ProfileViewModel.ShowEditProfileDialog -> {
                EditProfileDialogFragment.newInstance(event.profile).show(
                    childFragmentManager,
                    EditProfileDialogFragment.TAG
                )
            }
        }
    }

    override fun render(viewState: ProfileViewState) {
        when (viewState) {
            is ProfileContent -> {
                viewFlipper.displayedChild = 0

                profileNameText.text = viewState.profile.name
                profileUsernameText.text = viewState.profile.username
            }
            is Loading -> {
                viewFlipper.displayedChild = 1
            }
            is Error -> {
                viewFlipper.displayedChild = 2
            }
        }
    }

    override fun onChangePasswordOkButtonPressed(oldPassword: String, newPassword: String) {
        viewModel.changePassword(oldPassword, newPassword)
    }

    override fun onEditDialogOkButtonPressed(profile: ProfileUpdateData) {
        viewModel.editProfile(profile)
    }
}
