package hu.bme.caffshare.ui.admin

import android.os.Bundle
import android.view.View
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
import kotlinx.android.synthetic.main.fragment_caff_list.*
import kotlinx.android.synthetic.main.fragment_caff_list.caffFileList
import kotlinx.android.synthetic.main.fragment_caff_list.viewFlipper
import kotlinx.android.synthetic.main.fragment_user_list.*

class UserListFragment : RainbowCakeFragment<UserListViewState, UserListViewModel>() {

    companion object {
        private const val LIST_ITEM_TOP_MARGIN = 150
    }

    override fun provideViewModel() = getViewModelFromFactory()
    override fun getViewResource() = R.layout.fragment_user_list

    private lateinit var adapter: UserListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        adapter = UserListAdapter()

        adapter.listener = object : UserListAdapter.Listener {
            override fun onListItemClicked(item: User) {
                TODO()
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