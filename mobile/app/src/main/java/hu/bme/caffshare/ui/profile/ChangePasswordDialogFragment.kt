package hu.bme.caffshare.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import hu.bme.caffshare.R
import kotlinx.android.synthetic.main.layout_dialog_changepassword.*
import kotlinx.android.synthetic.main.layout_dialog_changepassword.view.*

class ChangePasswordDialogFragment : DialogFragment() {

    lateinit var listener: ChangePasswordDialogFragmentListener

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.layout_dialog_changepassword, container, false)

        view.changePassword_okButton.setOnClickListener {
            listener.onChangePasswordOkButtonPressed(
                    newPassword = NewPasswordWrapper(
                            oldPassword = changePassword_oldPasswordTextInputEditText.text.toString(),
                            newPassword = changePassword_textInputEditText.text.toString()
                    )
            )
        }

        view.changePassword_cancelButton.setOnClickListener {
            listener.onChangePasswordCancelButtonPressed()
        }

        return view
    }

    interface ChangePasswordDialogFragmentListener {
        fun onChangePasswordOkButtonPressed(newPassword: NewPasswordWrapper)
        fun onChangePasswordCancelButtonPressed()
    }

    data class NewPasswordWrapper(
            var oldPassword: String,
            var newPassword: String
    )

}