package hu.bme.caffshare.ui.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.dagger.getViewModelFromFactory
import co.zsmb.rainbowcake.navigation.navigator
import hu.bme.caffshare.R
import hu.bme.caffshare.ui.admin.adapter.UserListAdapter
import hu.bme.caffshare.ui.admin.model.User
import hu.bme.caffshare.ui.caffdetails.CaffDetailsFragment
import hu.bme.caffshare.ui.cafflist.adapter.CaffListAdapter
import hu.bme.caffshare.ui.cafflist.adapter.SpacesItemDecoration
import hu.bme.caffshare.ui.cafflist.model.CaffFile
import hu.bme.caffshare.util.setNavigationOnClickListener
import hu.bme.caffshare.util.setupBackDropMenu
import kotlinx.android.synthetic.main.fragment_caff_list.*
import kotlinx.android.synthetic.main.fragment_caff_list.viewFlipper
import kotlinx.android.synthetic.main.fragment_user_list.*
import kotlinx.android.synthetic.main.layout_user_list.*
import kotlinx.android.synthetic.main.layout_user_list.view.*

class UserListFragment : RainbowCakeFragment<UserListViewState, UserListViewModel>() {

    companion object {
        private const val LIST_ITEM_TOP_MARGIN = 150
    }

    override fun provideViewModel() = getViewModelFromFactory()
    override fun getViewResource() = R.layout.fragment_user_list

    private lateinit var adapter: UserListAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        // Inflate the layout for this fragment with the ProductGrid theme
        val view = inflater.inflate(R.layout.fragment_user_list, container, false)

        with(view) {
            setupBackDropMenu(navigator!!)

            setNavigationOnClickListener(requireActivity(), requireContext())
        }

        view.nested_scroll_view.background =
            ContextCompat.getDrawable(requireContext(), R.drawable.curved_background)
        view.nested_scroll_view.isFillViewport = true

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        adapter = UserListAdapter()

        adapter.listener = object : UserListAdapter.Listener {
            override fun onListItemDeleteButtonClicked(item: User) {
                viewModel.deleteUser(item)
            }
        }
        userFileList.layoutManager = StaggeredGridLayoutManager(1, RecyclerView.VERTICAL)
        userFileList.adapter = adapter
    }

    override fun onStart() {
        super.onStart()

        viewModel.load()
    }

    override fun render(viewState: UserListViewState) {
        when (viewState) {
            is UserListContent -> {
                viewFlipper.displayedChild = 0

                adapter.submitList(viewState.users)
            }
            is Loading -> {
                viewFlipper.displayedChild = 1
            }
            is Error -> {
                viewFlipper.displayedChild = 2
            }
            is Empty -> {
                viewFlipper.displayedChild = 3
            }
        }
    }
}