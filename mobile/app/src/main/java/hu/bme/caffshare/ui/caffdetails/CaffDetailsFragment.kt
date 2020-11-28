package hu.bme.caffshare.ui.caffdetails

import android.os.Bundle
import android.view.View
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

class CaffDetailsFragment : RainbowCakeFragment<CaffDetailsViewState, CaffDetailsViewModel> {

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
        fun newInstance(caffFileId: String): CaffDetailsFragment {
            return CaffDetailsFragment().applyArgs {
                putString(CAFF_FILE_ID, caffFileId)
            }
        }
    }

    private var caffFileId: String = ""

    private fun initArguments() {
        caffFileId = requireArguments().requireString(CAFF_FILE_ID)
    }

    //endregion

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initArguments()
        setupCommentsButton()
        setupPurchaseButton()
        setupDeleteButton()
    }

    private fun setupCommentsButton() {
        commentsButton.setOnClickListener {
            navigator?.add(CommentsFragment.newInstance(caffFileId))
        }
    }

    private fun setupPurchaseButton() {
        mainActionButton.apply {
            text = getString(R.string.purchase)
            setOnClickListener {
                progressBar.visibility = View.VISIBLE

                viewModel.purchaseCaffFile()
            }
        }
    }

    private fun setupDeleteButton() {
        deleteButton.setOnClickListener {
            progressBar.visibility = View.VISIBLE

            viewModel.deleteCaffFile()
        }
    }

    override fun onStart() {
        super.onStart()

        viewModel.load(caffFileId)
    }

    override fun onEvent(event: OneShotEvent) {
        when (event) {
            is CaffDetailsViewModel.PurchaseSuccessful -> {
                showSuccessSnackBar("Successful purchase!")
            }
            is CaffDetailsViewModel.PurchaseFailed -> {
                showErrorSnackBar("An error occurred while while purchasing the file!")
            }
            is CaffDetailsViewModel.DeleteSuccessful -> {
                showSuccessSnackBar("File deleted successfully!")
                navigator?.pop()
            }
            is CaffDetailsViewModel.DeleteFailed -> {
                showErrorSnackBar("An error occurred while while deleting the file!")
            }
        }
        progressBar.visibility = View.GONE
    }

    override fun render(viewState: CaffDetailsViewState) {
        when (viewState) {
            is CaffDetailsContent -> {
                viewFlipper.displayedChild = 0

                setupContentView(viewState.caffDetails)
                if (viewState.isUserAdmin) {
                    deleteButton.visibility = View.VISIBLE
                }
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
