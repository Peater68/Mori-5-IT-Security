package hu.bme.caffshare.ui.profile.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import hu.bme.caffshare.R
import kotlinx.android.synthetic.main.layout_dialog_changepassword.*

class ChangePasswordDialogFragment : DialogFragment() {

    companion object {
        const val TAG = "ChangePasswordDialogFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.layout_dialog_changepassword, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        okButton.setOnClickListener {
            (parentFragment as Listener).onChangePasswordOkButtonPressed(
                oldPassword = oldPasswordInput.text.toString(),
                newPassword = newPasswordInput.text.toString()
            )
            dismiss()
        }

        cancelButton.setOnClickListener { dismiss() }
    }

    interface Listener {
        fun onChangePasswordOkButtonPressed(oldPassword: String, newPassword: String)
    }
}