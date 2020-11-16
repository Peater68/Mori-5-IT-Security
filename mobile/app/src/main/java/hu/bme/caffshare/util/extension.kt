package hu.bme.caffshare.util

import android.app.Activity
import android.graphics.Color
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout

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