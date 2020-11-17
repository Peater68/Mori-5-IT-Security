package hu.bme.caffshare.ui.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.dagger.getViewModelFromFactory
import hu.bme.caffshare.R
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : RainbowCakeFragment<ProfileViewState, ProfileViewModel>() {

    override fun provideViewModel() = getViewModelFromFactory()
    override fun getViewResource() = R.layout.fragment_profile

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

class ProfileListsAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(
    fragmentManager,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {
    private val fragments = listOf<Fragment>()

    override fun getCount() = fragments.size

    override fun getItem(position: Int) = fragments[position]
}