package hu.bme.caffshare.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import co.zsmb.rainbowcake.base.OneShotEvent
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.dagger.getViewModelFromFactory
import co.zsmb.rainbowcake.navigation.navigator
import hu.bme.caffshare.R
import hu.bme.caffshare.ui.login.LoginFragment
import hu.bme.caffshare.ui.profile.adapter.ProfileListsAdapter
import hu.bme.caffshare.ui.profile.model.ProfilePresenterModel
import hu.bme.caffshare.util.setNavigationOnClickListener
import hu.bme.caffshare.util.setupBackDropMenu
import hu.bme.caffshare.util.showErrorSnackBar
import hu.bme.caffshare.util.showSuccessSnackBar
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.layout_profile.*
import kotlinx.android.synthetic.main.layout_profile.view.*

class ProfileFragment : RainbowCakeFragment<ProfileViewState, ProfileViewModel>(),
    ChangePasswordDialogFragment.Listener,
    EditProfileDialogFragment.EditProfileDialogFragmentListener {

    override fun provideViewModel() = getViewModelFromFactory()
    override fun getViewResource() = R.layout.fragment_profile

    private lateinit var changePasswordDialogFragment: ChangePasswordDialogFragment
    private lateinit var editProfileDialogFragment: EditProfileDialogFragment

    private lateinit var profile: ProfilePresenterModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        // Inflate the layout for this fragment with the ProductGrid theme
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        with(view) {
            setupBackDropMenu(navigator!!)

            setNavigationOnClickListener(requireActivity(), requireContext())
        }

        view.nested_scroll_view.background =
                ContextCompat.getDrawable(requireContext(), R.drawable.curved_background)
        view.nested_scroll_view.isFillViewport = true

        view.changePasswordButton.setOnClickListener {
            changePasswordDialogFragment = ChangePasswordDialogFragment()
            changePasswordDialogFragment.show(requireActivity().supportFragmentManager, "ChangePassword")
        }

        view.logoutButton.setOnClickListener {
            viewModel.logout()
        }

        view.editUserButton.setOnClickListener {
            editProfileDialogFragment = EditProfileDialogFragment.newUpdateDialogInstance(profile = profile)
            editProfileDialogFragment.listener = this
            editProfileDialogFragment.show(requireActivity().supportFragmentManager, "ChangePassword")
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileViewPager.adapter = ProfileListsAdapter(childFragmentManager)
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
        }
    }

    override fun render(viewState: ProfileViewState) {
        when (viewState) {
            is ProfileContent -> {
                viewFlipper.displayedChild = 0

                profile = viewState.profile
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

    override fun onEditDialogOkButtonPressed(profile: ProfilePresenterModel) {
        viewModel.editProfile(profile)
        editProfileDialogFragment.dismiss()
    }

    override fun onEditDialogCancelButtonPressed() {
        editProfileDialogFragment.dismiss()
    }
}
