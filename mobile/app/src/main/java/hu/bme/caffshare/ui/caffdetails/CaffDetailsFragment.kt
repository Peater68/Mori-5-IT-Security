package hu.bme.caffshare.ui.caffdetails

import android.os.Bundle
import android.view.View
import co.zsmb.rainbowcake.base.OneShotEvent
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.dagger.getViewModelFromFactory
import co.zsmb.rainbowcake.navigation.extensions.applyArgs
import co.zsmb.rainbowcake.navigation.extensions.requireString
import hu.bme.caffshare.R
import hu.bme.caffshare.util.showErrorSnackBar
import hu.bme.caffshare.util.showSuccessSnackBar

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

    private var assetId: String = ""

    private fun initArguments() {
        assetId = requireArguments().requireString(CAFF_FILE_ID)
    }

    //endregion

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initArguments()
    }

    override fun onStart() {
        super.onStart()

        viewModel.load(assetId)
    }

    override fun onEvent(event: OneShotEvent) {
        when (event) {
            is CaffDetailsViewModel.PurchaseSuccessful -> {
                showSuccessSnackBar("Successful purchase!")
            }
            is CaffDetailsViewModel.PurchaseFailed -> {
                showErrorSnackBar("Error while purchasing CAFF file!")
            }
        }
    }

    override fun render(viewState: CaffDetailsViewState) {
        when (viewState) {
            is Loading -> {
            }
            is Error -> {
            }
            is CaffDetailsContent -> {
            }
        }
    }
}
