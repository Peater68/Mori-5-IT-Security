package hu.bme.caffshare.ui.admin

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import co.zsmb.rainbowcake.base.OneShotEvent
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.dagger.getViewModelFromFactory
import hu.bme.caffshare.R
import hu.bme.caffshare.ui.admin.adapter.UserListAdapter
import hu.bme.caffshare.ui.admin.model.User
import hu.bme.caffshare.util.showErrorSnackBar
import kotlinx.android.synthetic.main.fragment_caff_list.*
import kotlinx.android.synthetic.main.layout_user_list.*

class UserListFragment : RainbowCakeFragment<UserListViewState, UserListViewModel>() {

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
            override fun onListItemDeleteButtonClicked(item: User) {
                viewModel.banUser(item.id)
            }
        }
        userFileList.layoutManager = StaggeredGridLayoutManager(1, RecyclerView.VERTICAL)
        userFileList.adapter = adapter
    }

    override fun onStart() {
        super.onStart()

        viewModel.load()
    }

    override fun onEvent(event: OneShotEvent) {
        when (event) {
            is UserListViewModel.BanError -> {
                showErrorSnackBar("Error while banning user!")
            }
            is UserListViewModel.MakeUserAdminError -> {
                showErrorSnackBar("Error while making user admin user!")
            }
        }
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

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.title) {
            "Adminná teszem" -> {
                viewModel.makeUserAdmin(adapter.getUserAt(item.groupId).id)
            }

            "Letiltom" -> {
                viewModel.banUser(adapter.getUserAt(item.groupId).id)
            }
        }
        return super.onContextItemSelected(item)
    }
}