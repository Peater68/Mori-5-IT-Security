package hu.bme.caffshare.ui.boughtcaffdetails

import android.os.Bundle
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
import com.bumptech.glide.Glide
import hu.bme.caffshare.R
import hu.bme.caffshare.ui.caffdetails.model.CaffDetails
import hu.bme.caffshare.ui.comments.CommentsFragment
import hu.bme.caffshare.util.showSuccessSnackBar
import kotlinx.android.synthetic.main.fragment_caff_details.*
import kotlinx.android.synthetic.main.layout_caff_details.*
import kotlinx.android.synthetic.main.layout_caff_details.progressBar
import kotlinx.android.synthetic.main.layout_caff_details.view.*

class BoughtCaffDetailsFragment :
    RainbowCakeFragment<BoughtCaffDetailsViewState, BoughtCaffDetailsViewModel> {

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
        fun newInstance(caffFileId: String): BoughtCaffDetailsFragment {
            return BoughtCaffDetailsFragment().applyArgs {
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
                // TODO
                viewModel.downloadCaffFile()
                progressBar.visibility = View.VISIBLE
            }
        }
    }

    override fun onStart() {
        super.onStart()

        viewModel.load(caffFileId)
    }

    override fun onEvent(event: OneShotEvent) {
        when (event) {
            is BoughtCaffDetailsViewModel.DownloadSuccessful -> {
                showSuccessSnackBar("Successful download!")
                progressBar.visibility = View.GONE
            }
            is BoughtCaffDetailsViewModel.DownloadFailed -> {
                showSuccessSnackBar("An error occurred while while downloading the file!")
                progressBar.visibility = View.GONE
            }
        }
    }

    override fun render(viewState: BoughtCaffDetailsViewState) {
        when (viewState) {
            is BoughtCaffDetailsContent -> {
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
        Glide.with(requireContext())
            .load(caffDetails.imageUrl)
            .into(caffImage)
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
