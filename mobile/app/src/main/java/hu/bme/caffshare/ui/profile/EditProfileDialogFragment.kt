package hu.bme.caffshare.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import hu.bme.caffshare.R
import hu.bme.caffshare.ui.profile.model.ProfileUpdateData
import kotlinx.android.synthetic.main.layout_dialog_editprofile.*

class EditProfileDialogFragment : DialogFragment() {

    companion object {
        const val TAG = "EditProfileDialogFragment"

        private const val FIRST_NAME = "first_name"
        private const val LAST_NAME = "last_name"
        private const val USERNAME = "username"
        private const val EMAIL = "email"

        @JvmStatic
        fun newInstance(
            profile: ProfileUpdateData
        ) =
            EditProfileDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(FIRST_NAME, profile.firstName)
                    putString(LAST_NAME, profile.lastName)
                    putString(USERNAME, profile.username)
                    putString(EMAIL, profile.email)
                }
            }
    }

    private var firstName: String? = null
    private var lastName: String? = null
    private var username: String? = null
    private var email: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            firstName = it.getString(FIRST_NAME)
            lastName = it.getString(LAST_NAME)
            username = it.getString(USERNAME)
            email = it.getString(EMAIL)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_dialog_editprofile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fillTextInputs()
        setupButtons()
    }

    private fun fillTextInputs() {
        firstNameInput.setText(firstName)
        lastNameInput.setText(lastName)
        usernameInput.setText(username)
        emailInput.setText(email)
    }

    private fun setupButtons() {
        okButton.setOnClickListener {
            (parentFragment as Listener).onEditDialogOkButtonPressed(
                profile = ProfileUpdateData(
                    firstName = firstNameInput.text.toString(),
                    lastName = lastNameInput.text.toString(),
                    username = usernameInput.text.toString(),
                    email = emailInput.text.toString()
                )
            )
            dismiss()
        }

        cancelButton.setOnClickListener {
            dismiss()
        }
    }

    interface Listener {
        fun onEditDialogOkButtonPressed(profile: ProfileUpdateData)
    }
}