package hu.bme.caffshare.ui.profile.uploadedcafflist

import android.net.Uri
import co.zsmb.rainbowcake.base.OneShotEvent
import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import javax.inject.Inject

class UploadedCaffListViewModel @Inject constructor(
    private val uploadedCaffListPresenter: UploadedCaffListPresenter
) : RainbowCakeViewModel<UploadedCaffListViewState>(Loading) {

    companion object {
        private const val CAFF_FILE_EXTENSION = "caff"
    }

    object InvalidFile : OneShotEvent
    object BadFileExtension : OneShotEvent
    data class FileSelected(val fileName: String) : OneShotEvent

    object UploadSuccessful : OneShotEvent
    object UploadFailed : OneShotEvent

    fun load() = execute {
        val caffList = uploadedCaffListPresenter.getCaffFiles()

        val state = viewState
        if (state !is UploadedCaffListContent) {
            viewState =
                when (caffList) {
                    null -> Error
                    else -> UploadedCaffListContent(caffList)
                }
        }
    }

    fun selectFile(fileUri: Uri) = execute {
        val state = viewState
        (state as? UploadedCaffListContent)?.let {
            when {
                fileUri.path == null -> {
                    postEvent(InvalidFile)
                }
                fileUri.path!!.endsWith(CAFF_FILE_EXTENSION).not() -> {
                    postEvent(BadFileExtension)
                }
                else -> {
                    val fileName = fileUri.path!!.split('/').last()
                    postEvent(FileSelected(fileName))
                    viewState = state.copy(selectedFileUri = fileUri)
                }
            }
        }
    }

    fun uploadSelectedFile() = execute {
        (viewState as? UploadedCaffListContent)?.let { state ->
            state.selectedFileUri?.let { uri ->
                val uploadResult = uploadedCaffListPresenter.uploadFile(uri)
                if (uploadResult) {
                    postEvent(UploadSuccessful)
                    viewState = Loading
                    load()
                } else {
                    postEvent(UploadFailed)
                }
            }
        }
    }

    fun deselectFile() = execute {
        val state = viewState
        (state as? UploadedCaffListContent)?.let {
            viewState = state.copy(selectedFileUri = null)
        }
    }
}
