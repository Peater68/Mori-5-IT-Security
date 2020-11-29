package hu.bme.caffshare.util

import android.app.Activity
import android.graphics.Color
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import hu.bme.caffshare.data.network.GlideApp
import hu.bme.caffshare.data.network.NetworkModule
import hu.bme.caffshare.domain.model.CaffDownloadType
import hu.bme.caffshare.ui.mainactivity.MainActivity
import kotlinx.android.synthetic.main.activity_main_caff.*
import java.io.File
import java.io.InputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

val TextInputLayout.text: String
    get() {
        return editText?.text.toString()
    }

fun TextInputLayout.isNotEmpty(): Boolean {
    val isNotEmpty = text.isNotEmpty()
    if (!isNotEmpty) {
        error = "This field must not be empty!"
    }
    return isNotEmpty
}

fun Fragment.showErrorSnackBar(text: String) {
    Snackbar.make(requireView(), text, Snackbar.LENGTH_SHORT).apply {
        setBackgroundTint(Color.RED)
        setTextColor(Color.WHITE)
    }.show()
}

fun Fragment.showSuccessSnackBar(text: String) {
    Snackbar.make(requireView(), text, Snackbar.LENGTH_SHORT).apply {
        setBackgroundTint(Color.parseColor("#2bae66"))
        setTextColor(Color.WHITE)
    }.show()
}

fun Fragment.hideKeyboard() {
    val imm = requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager

    var view = activity?.currentFocus
    //If no view currently has focus, create a new one, just so we can grab a window token from it
    if (view == null) {
        view = View(activity)
    }
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun String.toLocalDateTime(): LocalDateTime {
    val localDateTimeFormat = if (contains('.')) {
        substringBeforeLast('.')
    } else {
        substringBeforeLast('Z')
    }
    return LocalDateTime.parse(localDateTimeFormat, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
}

fun ImageView.loadCaffPreview(caffId: String) {
    GlideApp.with(context)
        .load("${NetworkModule.BASE_URL}/api/caffs/$caffId/download?type=${CaffDownloadType.PREVIEW}")
        .into(this)
}

val Fragment.toolbar: androidx.appcompat.widget.Toolbar
    get() = (activity as MainActivity).appBar

val Fragment.bottomNav: BottomNavigationView
    get() = (activity as MainActivity).bottomNav

fun File.copyInputStreamToFile(inputStream: InputStream) {
    this.outputStream().use { fileOut ->
        inputStream.copyTo(fileOut)
    }
}