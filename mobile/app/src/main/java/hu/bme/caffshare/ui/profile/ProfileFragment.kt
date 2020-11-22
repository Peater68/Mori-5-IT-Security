package hu.bme.caffshare.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.dagger.getViewModelFromFactory
import co.zsmb.rainbowcake.navigation.navigator
import hu.bme.caffshare.R
import hu.bme.caffshare.ui.profile.adapter.ProfileListsAdapter
import hu.bme.caffshare.util.setNavigationOnClickListener
import hu.bme.caffshare.util.setupBackDropMenu
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.layout_profile.*
import kotlinx.android.synthetic.main.layout_profile.view.*

class ProfileFragment : RainbowCakeFragment<ProfileViewState, ProfileViewModel>() {

    override fun provideViewModel() = getViewModelFromFactory()
    override fun getViewResource() = R.layout.fragment_profile

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
