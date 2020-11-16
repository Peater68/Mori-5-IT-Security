package hu.bme.caffshare.ui.comments

import android.os.Bundle
import android.view.View
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.dagger.getViewModelFromFactory
import co.zsmb.rainbowcake.navigation.extensions.applyArgs
import co.zsmb.rainbowcake.navigation.extensions.requireString
import hu.bme.caffshare.R
import hu.bme.caffshare.ui.comments.adapter.CommentsAdapter
import kotlinx.android.synthetic.main.fragment_caff_details.viewFlipper
import kotlinx.android.synthetic.main.fragment_comments.*


class CommentsFragment : RainbowCakeFragment<CommentsViewState, CommentsViewModel> {

    override fun provideViewModel() = getViewModelFromFactory()
    override fun getViewResource() = R.layout.fragment_comments

    //region Arguments
    @Suppress("ConvertSecondaryConstructorToPrimary")
    @Deprecated(
        message = "Use newInstance instead",
        replaceWith = ReplaceWith("AssetDetailsFragment.newInstance()")
    )
    constructor()

    companion object {
        private const val CAFF_FILE_ID = "CAFF_FILE_ID"

        @Suppress("DEPRECATION")
        fun newInstance(caffFileId: String): CommentsFragment {
            return CommentsFragment().applyArgs {
                putString(CAFF_FILE_ID, caffFileId)
            }
        }
    }

    private lateinit var adapter: CommentsAdapter

    private var caffFileId: String = ""

    private fun initArguments() {
        caffFileId = requireArguments().requireString(CAFF_FILE_ID)
    }

    //endregion

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initArguments()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        adapter = CommentsAdapter()
        commentList.adapter = adapter
    }

    override fun onStart() {
        super.onStart()

        viewModel.load(caffFileId)
    }

    override fun render(viewState: CommentsViewState) {
        when (viewState) {
            is CommentsContent -> {
                viewFlipper.displayedChild = 0
                contentViewFlipper.displayedChild = 0

                adapter.submitList(viewState.comments)
            }
            is Loading -> {
                viewFlipper.displayedChild = 1
            }
            is Error -> {
                viewFlipper.displayedChild = 2
            }
            is Empty -> {
                contentViewFlipper.displayedChild = 1
            }
        }
    }
}
