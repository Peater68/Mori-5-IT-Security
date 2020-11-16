package hu.bme.caffshare.ui.caffdetails

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import co.zsmb.rainbowcake.base.OneShotEvent
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.dagger.getViewModelFromFactory
import co.zsmb.rainbowcake.navigation.extensions.applyArgs
import co.zsmb.rainbowcake.navigation.extensions.requireString
import com.bumptech.glide.Glide
import hu.bme.caffshare.R
import hu.bme.caffshare.ui.caffdetails.model.CaffDetails
import hu.bme.caffshare.util.showSuccessSnackBar
import kotlinx.android.synthetic.main.fragment_caff_details.*


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
        setupPurchaseButton()
    }

    private fun setupPurchaseButton() {
        purchaseButton.setOnClickListener {
            purchaseProgressBar.visibility = View.VISIBLE

            viewModel.purchaseCaffFile()
        }
    }

    override fun onStart() {
        super.onStart()

        viewModel.load(assetId)
    }

    override fun onEvent(event: OneShotEvent) {
        when (event) {
            is CaffDetailsViewModel.PurchaseSuccessful -> {
                showSuccessSnackBar("Successful purchase!")
                purchaseProgressBar.visibility = View.GONE
            }
            is CaffDetailsViewModel.PurchaseFailed -> {
                showSuccessSnackBar("An error occurred while while purchasing the file!")
                purchaseProgressBar.visibility = View.GONE
            }
        }
    }

    override fun render(viewState: CaffDetailsViewState) {
        when (viewState) {
            is CaffDetailsContent -> {
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

    fun drawableToBitmap(drawable: Drawable): Bitmap? {
        if (drawable is BitmapDrawable) {
            return drawable.bitmap
        }
        val bitmap =
            Bitmap.createBitmap(
                drawable.intrinsicWidth,
                drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }
}
