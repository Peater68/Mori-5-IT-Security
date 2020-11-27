package hu.bme.caffshare.util

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import co.zsmb.rainbowcake.navigation.Navigator
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import hu.bme.caffshare.R
import hu.bme.caffshare.data.network.GlideApp
import hu.bme.caffshare.data.network.NetworkModule
import hu.bme.caffshare.domain.model.CaffDownloadType
import hu.bme.caffshare.ui.admin.UserListFragment
import hu.bme.caffshare.ui.cafflist.CaffListFragment
import hu.bme.caffshare.ui.profile.ProfileFragment
import kotlinx.android.synthetic.main.backdrop.view.*
import kotlinx.android.synthetic.main.layout_user_list.view.*
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

fun View.setupBackDropMenu(navigator: Navigator) {
    this.admin_menu_button.setOnClickListener {
        navigator.replace(UserListFragment())
    }
    this.cafflist_menu_button.setOnClickListener {
        navigator.replace(CaffListFragment())
    }
    this.account_menu_button.setOnClickListener {
        navigator.replace(ProfileFragment())
    }

}

fun View.setNavigationOnClickListener(activity: FragmentActivity, context: Context) {
    // Set up the toolbar.
    (activity as AppCompatActivity).setSupportActionBar(this.app_bar)

    this.app_bar.setNavigationOnClickListener(
        NavigationIconClickListener(
            activity,
            this.nested_scroll_view,
            openIcon = ContextCompat.getDrawable(context, R.drawable.menu_open_icon),
            closeIcon = ContextCompat.getDrawable(context, R.drawable.menu_close_icon)
        )
    )
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