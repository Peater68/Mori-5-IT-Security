package hu.bme.caffshare.ui.profile

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import co.zsmb.rainbowcake.base.OneShotEvent
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.dagger.getViewModelFromFactory
import co.zsmb.rainbowcake.navigation.navigator
import hu.bme.caffshare.R
import hu.bme.caffshare.ui.login.LoginFragment
import hu.bme.caffshare.ui.profile.adapter.ProfileListsAdapter
import hu.bme.caffshare.ui.profile.model.ProfileUpdateData
import hu.bme.caffshare.util.showErrorSnackBar
import hu.bme.caffshare.util.showSuccessSnackBar
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
        setupNestedScrollView()
        setupButtons()
    }

    private fun setupViewPager() {
        profileViewPager.adapter = ProfileListsAdapter(childFragmentManager)
    }

    private fun setupNestedScrollView() {
        nested_scroll_view.background =
            ContextCompat.getDrawable(requireContext(), R.drawable.curved_background)
        nested_scroll_view.isFillViewport = true
    }

    private fun setupButtons() {
        logoutButton.setOnClickListener {
            viewModel.logout()
        }

        changePasswordButton.setOnClickListener {
            ChangePasswordDialogFragment().show(
                childFragmentManager,
                ChangePasswordDialogFragment.TAG
            )
        }

        editUserButton.setOnClickListener {
            viewModel.showEditProfileDialog()
        }
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

                profileFirstNameText.text = viewState.profile.firstName
                profileLastNameText.text = viewState.profile.lastName
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
