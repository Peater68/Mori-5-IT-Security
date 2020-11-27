package hu.bme.caffshare.ui.uploadedcaffdetails

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
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
import hu.bme.caffshare.ui.caffdetails.model.CaffDetails
import hu.bme.caffshare.ui.comments.CommentsFragment
import hu.bme.caffshare.util.loadCaffPreview
import hu.bme.caffshare.util.showErrorSnackBar
import hu.bme.caffshare.util.showSuccessSnackBar
import kotlinx.android.synthetic.main.fragment_caff_details.*
import kotlinx.android.synthetic.main.layout_caff_details.*
import kotlinx.android.synthetic.main.layout_caff_details.view.*


class UploadedCaffDetailsFragment :
    RainbowCakeFragment<UploadedCaffDetailsViewState, UploadedCaffDetailsViewModel> {

    override fun provideViewModel() = getViewModelFromFactory()
    override fun getViewResource() = R.layout.fragment_caff_details

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
        fun newInstance(caffFileId: String): UploadedCaffDetailsFragment {
            return UploadedCaffDetailsFragment().applyArgs {
                putString(CAFF_FILE_ID, caffFileId)
            }
        }
    }

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
        val view = inflater.inflate(R.layout.fragment_caff_details, container, false)

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
        setupCommentsButton()
        setupDownloadButton()
        setupDeleteButton()
        setupTagsTextView()
    }

    private fun setupCommentsButton() {
        commentsButton.setOnClickListener {
            navigator?.add(CommentsFragment.newInstance(caffFileId))
        }
    }

    private fun setupDownloadButton() {
        mainActionButton.apply {
            text = getString(R.string.download)
            setOnClickListener {
                progressBar.visibility = View.VISIBLE

                viewModel.downloadCaffFile()
            }
        }
    }

    private fun setupDeleteButton() {
        deleteButton.apply {
            visibility = View.VISIBLE
            setOnClickListener {
                progressBar.visibility = View.VISIBLE

                viewModel.deleteCaffFile()
            }
        }
    }

    private fun setupTagsTextView() {
        tagsText.movementMethod = ScrollingMovementMethod()
    }

    override fun onStart() {
        super.onStart()

        viewModel.load(caffFileId)
    }

    override fun onEvent(event: OneShotEvent) {
        when (event) {
            is UploadedCaffDetailsViewModel.DownloadSuccessful -> {
                showSuccessSnackBar("Successful download!")
            }
            is UploadedCaffDetailsViewModel.DownloadFailed -> {
                showErrorSnackBar("An error occurred while while downloading the file!")
            }
            is UploadedCaffDetailsViewModel.DeleteSuccessful -> {
                showSuccessSnackBar("File deleted successfully!")
                navigator?.pop()
            }
            is UploadedCaffDetailsViewModel.DeleteFailed -> {
                showErrorSnackBar("An error occurred while while deleting the file!")
            }
        }
        progressBar.visibility = View.GONE
    }

    override fun render(viewState: UploadedCaffDetailsViewState) {
        when (viewState) {
            is UploadedCaffDetailsContent -> {
                viewFlipper.displayedChild = 0

                setupContentView(viewState.caffDetails)
            }
            is Loading -> {
                viewFlipper.displayedChild = 1
            }
            is Error -> {
                viewFlipper.displayedChild = 2
            }
        }
    }

    private fun setupContentView(caffDetails: CaffDetails) {
        caffImage.loadCaffPreview(caffDetails.id)
        authorText.text = caffDetails.author
        captionText.text = caffDetails.caption
        dateText.text = caffDetails.date
        caffDetails.tags.forEachIndexed { index, s ->
            tagsText.append(s)
            if (index != caffDetails.tags.lastIndex) {
                tagsText.append("\n")
            }
        }
    }
}
