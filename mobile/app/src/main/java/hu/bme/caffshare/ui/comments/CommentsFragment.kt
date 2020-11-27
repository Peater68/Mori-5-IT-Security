package hu.bme.caffshare.ui.comments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import co.zsmb.rainbowcake.base.OneShotEvent
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.dagger.getViewModelFromFactory
import co.zsmb.rainbowcake.navigation.extensions.applyArgs
import co.zsmb.rainbowcake.navigation.extensions.requireString
import co.zsmb.rainbowcake.navigation.navigator
import hu.bme.caffshare.R
import hu.bme.caffshare.ui.comments.adapter.CommentsAdapter
import hu.bme.caffshare.ui.comments.model.Comment
import hu.bme.caffshare.util.hideKeyboard
import hu.bme.caffshare.util.showErrorSnackBar
import kotlinx.android.synthetic.main.fragment_caff_details.viewFlipper
import kotlinx.android.synthetic.main.layout_comments.*
import kotlinx.android.synthetic.main.layout_comments.view.*


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

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        // Inflate the layout for this fragment with the ProductGrid theme
        val view = inflater.inflate(R.layout.fragment_comments, container, false)

        with(view) {
            // Set up the toolbar.
            (activity as AppCompatActivity).setSupportActionBar(this.app_bar)

            this.app_bar.setNavigationOnClickListener {
                navigator!!.pop()
            }
        }

        view.nested_scroll_view.background =
                ContextCompat.getDrawable(requireContext(), R.drawable.curved_background)
        view.nested_scroll_view.isFillViewport = true

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initArguments()
        setupRecyclerView()
        setupCommentInput()
        setupSendCommentButton()
    }

    private fun setupRecyclerView() {
        adapter = CommentsAdapter()
        adapter.listener = object : CommentsAdapter.Listener {
            override fun deleteComment(comment: Comment) {
                commentHandlingProgressBar.visibility = View.VISIBLE
                viewModel.deleteComment(comment.id)
            }
        }
        commentList.adapter = adapter
    }

    private fun setupCommentInput() {
        commentInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
            override fun afterTextChanged(p0: Editable?) = Unit

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                sendCommentButton.isEnabled = text.isNullOrBlank().not()
            }
        })
    }

    private fun setupSendCommentButton() {
        sendCommentButton.isEnabled = false
        sendCommentButton.setOnClickListener {
            commentHandlingProgressBar.visibility = View.VISIBLE
            viewModel.addComment(commentInput.text.toString())
        }
    }

    override fun onStart() {
        super.onStart()

        viewModel.load(caffFileId)
    }

    override fun onEvent(event: OneShotEvent) {
        when (event) {
            is CommentsViewModel.CommentSentSuccessfully -> {
                viewModel.load(caffFileId)
                hideKeyboard()
                commentInput.setText("")
                commentInput.clearFocus()
            }
            is CommentsViewModel.CommentSendingError -> {
                showErrorSnackBar("Error while posting comment!")
            }
            is CommentsViewModel.CommentDeletedSuccessfully -> {
                viewModel.load(caffFileId)
            }
            is CommentsViewModel.CommentDeletingError -> {
                showErrorSnackBar("Error while deleting comment!")
            }
        }
        commentHandlingProgressBar.visibility = View.GONE
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
