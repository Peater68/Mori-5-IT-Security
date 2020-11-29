package hu.bme.caffshare.ui.profile.uploadedcafflist

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import co.zsmb.rainbowcake.base.OneShotEvent
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.base.ViewModelScope
import co.zsmb.rainbowcake.dagger.getViewModelFromFactory
import co.zsmb.rainbowcake.navigation.navigator
import hu.bme.caffshare.R
import hu.bme.caffshare.ui.cafflist.adapter.CaffListAdapter
import hu.bme.caffshare.ui.cafflist.adapter.SpacesItemDecoration
import hu.bme.caffshare.ui.cafflist.model.CaffFile
import hu.bme.caffshare.ui.uploadedcaffdetails.UploadedCaffDetailsFragment
import hu.bme.caffshare.util.showErrorSnackBar
import hu.bme.caffshare.util.showSuccessSnackBar
import kotlinx.android.synthetic.main.fragment_uploaded_caff_list.*
import kotlinx.android.synthetic.main.layout_uploaded_caff_list.*
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.RuntimePermissions

@RuntimePermissions
class UploadedCaffListFragment :
    RainbowCakeFragment<UploadedCaffListViewState, UploadedCaffListViewModel>() {

    companion object {
        private const val LIST_ITEM_TOP_MARGIN = 150

        private const val PICKER_REQUEST_CODE = 120
    }

    override fun provideViewModel() = getViewModelFromFactory(scope = ViewModelScope.ParentFragment)
    override fun getViewResource() = R.layout.fragment_uploaded_caff_list

    private lateinit var adapter: CaffListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupUploadButton()
    }

    private fun setupUploadButton() {
        uploadButton.setOnClickListener {
            selectFileWithPermissionCheck()
        }
    }

    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    fun selectFile() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/*"
        }
        startActivityForResult(intent, PICKER_REQUEST_CODE)
    }

    private fun setupRecyclerView() {
        adapter = CaffListAdapter()

        adapter.listener = object : CaffListAdapter.Listener {
            override fun onCaffFileClicked(file: CaffFile) {
                navigator?.add(UploadedCaffDetailsFragment.newInstance(file.id))
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

    override fun onEvent(event: OneShotEvent) {
        when (event) {
            is UploadedCaffListViewModel.InvalidFile -> {
                showErrorSnackBar("Invalid file")
            }
            is UploadedCaffListViewModel.BadFileExtension -> {
                showErrorSnackBar("Wrong file extension")
            }
            is UploadedCaffListViewModel.FileSelected -> {
                AlertDialog.Builder(requireContext())
                    .create()
                    .apply {
                        setTitle("Upload CAFF file")
                        setMessage("Are you sure you want to upload ${event.fileName}?")
                        setButton(AlertDialog.BUTTON_POSITIVE, "Yes") { dialog, _ ->
                            viewModel.uploadSelectedFile()
                            dialog.dismiss()
                        }
                        setButton(AlertDialog.BUTTON_NEGATIVE, "No") { dialog, _ ->
                            viewModel.deselectFile()
                            dialog.dismiss()
                        }
                        show()
                    }
            }
            is UploadedCaffListViewModel.UploadSuccessful -> {
                showSuccessSnackBar("File uploaded successfully!")
            }
            is UploadedCaffListViewModel.UploadFailed -> {
                showErrorSnackBar("Error while uploading file!")
            }
        }
    }

    override fun render(viewState: UploadedCaffListViewState) {
        when (viewState) {
            is UploadedCaffListContent -> {
                val caffFiles = viewState.caffFiles

                if (caffFiles.isEmpty()) {
                    viewFlipper.displayedChild = 0
                    contentViewFlipper.displayedChild = 1
                } else {
                    viewFlipper.displayedChild = 0
                    contentViewFlipper.displayedChild = 0

                    adapter.submitList(viewState.caffFiles)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PICKER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.data?.let {
                viewModel.selectFile(it)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // NOTE: delegate the permission handling to generated function
        onRequestPermissionsResult(requestCode, grantResults)
    }
}
