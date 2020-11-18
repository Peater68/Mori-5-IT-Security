package hu.bme.caffshare.ui.profile

import android.os.Bundle
import android.view.View
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.dagger.getViewModelFromFactory
import hu.bme.caffshare.R
import hu.bme.caffshare.ui.profile.adapter.ProfileListsAdapter
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : RainbowCakeFragment<ProfileViewState, ProfileViewModel>() {

    override fun provideViewModel() = getViewModelFromFactory()
    override fun getViewResource() = R.layout.fragment_profile

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileViewPager.adapter = ProfileListsAdapter(childFragmentManager)
    }

    override fun onStart() {
        super.onStart()

        viewModel.load()
    }

    override fun render(viewState: ProfileViewState) {
        when (viewState) {
            is ProfileContent -> {
                viewFlipper.displayedChild = 0

                profileNameText.text = viewState.username
            }
            is Loading -> {
                viewFlipper.displayedChild = 1
            }
            is Error -> {
                viewFlipper.displayedChild = 2
            }
        }
    }
}
