package hu.bme.caffshare.ui.profile.boughtcafflist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.base.ViewModelScope
import co.zsmb.rainbowcake.dagger.getViewModelFromFactory
import co.zsmb.rainbowcake.navigation.navigator
import hu.bme.caffshare.R
import hu.bme.caffshare.ui.boughtcaffdetails.BoughtCaffDetailsFragment
import hu.bme.caffshare.ui.cafflist.adapter.CaffListAdapter
import hu.bme.caffshare.ui.cafflist.adapter.SpacesItemDecoration
import hu.bme.caffshare.ui.cafflist.model.CaffFile
import kotlinx.android.synthetic.main.fragment_caff_details.*
import kotlinx.android.synthetic.main.layout_bought_caff_list.*

class BoughtCaffListFragment :
    RainbowCakeFragment<BoughtCaffListViewState, BoughtCaffListViewModel>() {

    companion object {
        private const val LIST_ITEM_TOP_MARGIN = 150
    }

    override fun provideViewModel() = getViewModelFromFactory(scope = ViewModelScope.ParentFragment)
    override fun getViewResource() = R.layout.fragment_bought_caff_list

    private lateinit var adapter: CaffListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        adapter = CaffListAdapter()

        adapter.listener = object : CaffListAdapter.Listener {
            override fun onCaffFileClicked(file: CaffFile) {
                navigator?.add(BoughtCaffDetailsFragment.newInstance(file.id))
            }
        }
        caffFileList.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        caffFileList.adapter = adapter
        caffFileList.addItemDecoration(SpacesItemDecoration(LIST_ITEM_TOP_MARGIN))
    }

    override fun onStart() {
        super.onStart()

        viewModel.load()
    }

    override fun render(viewState: BoughtCaffListViewState) {
        when (viewState) {
            is BoughtCaffListContent -> {
                viewFlipper.displayedChild = 0

                adapter.submitList(viewState.caffFiles)
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