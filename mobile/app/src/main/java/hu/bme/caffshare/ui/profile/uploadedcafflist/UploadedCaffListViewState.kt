package hu.bme.caffshare.ui.profile.uploadedcafflist

import android.net.Uri
import hu.bme.caffshare.ui.cafflist.model.CaffFile

sealed class UploadedCaffListViewState

object Loading : UploadedCaffListViewState()

object Error : UploadedCaffListViewState()

object Empty : UploadedCaffListViewState()

data class UploadedCaffListContent(
    val caffFiles: List<CaffFile> = emptyList(),
    val selectedFileUri: Uri? = null
) :
    UploadedCaffListViewState()
