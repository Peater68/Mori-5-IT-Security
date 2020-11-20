package hu.bme.caffshare.ui.cafflist

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
import hu.bme.caffshare.ui.caffdetails.CaffDetailsFragment
import hu.bme.caffshare.ui.cafflist.adapter.CaffListAdapter
import hu.bme.caffshare.ui.cafflist.adapter.SpacesItemDecoration
import hu.bme.caffshare.ui.cafflist.model.CaffFile
import hu.bme.caffshare.util.setNavigationOnClickListener
import hu.bme.caffshare.util.setupBackDropMenu
import kotlinx.android.synthetic.main.fragment_caff_list.*
import kotlinx.android.synthetic.main.layout_caff_list.*
import kotlinx.android.synthetic.main.layout_caff_list.view.*

class CaffListFragment : RainbowCakeFragment<CaffListViewState, CaffListViewModel>() {

    companion object {
        private const val LIST_ITEM_TOP_MARGIN = 150
    }

    override fun provideViewModel() = getViewModelFromFactory()
    override fun getViewResource() = R.layout.fragment_caff_list

    private lateinit var adapter: CaffListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        // Inflate the layout for this fragment with the ProductGrid theme
        val view = inflater.inflate(R.layout.fragment_caff_list, container, false)

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

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        adapter = CaffListAdapter()

        adapter.listener = object : CaffListAdapter.Listener {
            override fun onCaffFileClicked(file: CaffFile) {
                navigator?.add(CaffDetailsFragment.newInstance(file.id))
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

    override fun render(viewState: CaffListViewState) {
        when (viewState) {
            is CaffListContent -> {
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