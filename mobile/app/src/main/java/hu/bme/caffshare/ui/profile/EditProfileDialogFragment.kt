package hu.bme.caffshare.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import hu.bme.caffshare.R
import hu.bme.caffshare.ui.profile.model.ProfilePresenterModel
import kotlinx.android.synthetic.main.layout_dialog_editprofile.*
import kotlinx.android.synthetic.main.layout_dialog_editprofile.view.*

private const val ID = "id"
private const val FIRST_NAME = "first_name"
private const val LAST_NAME = "last_name"
private const val USERNAME = "username"

class EditProfileDialogFragment : DialogFragment() {

    private var id: String? = null
    private var firstName: String? = null
    private var lastName: String? = null
    private var username: String? = null

    lateinit var listener: EditProfileDialogFragmentListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getString(ID)
            firstName = it.getString(FIRST_NAME)
            lastName = it.getString(LAST_NAME)
            username = it.getString(USERNAME)
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.layout_dialog_editprofile, container, false)

        view.layout_dialog_first_name_edit_text.setText(firstName)
        view.layout_dialog_last_name_edit_text.setText(lastName)
        view.layout_dialog_username_edit_text.setText(username)

        view.layout_dialog_profile_ok_button.setOnClickListener {
            listener.onEditDialogOkButtonPressed(
                    profile = ProfilePresenterModel(
                            id = id!!,
                            firstName = layout_dialog_first_name_edit_text.text.toString(),
                            lastName = layout_dialog_last_name_edit_text.text.toString(),
                            username = layout_dialog_username_edit_text.text.toString()
                    )
            )
        }

        view.layout_dialog_profile_cancel_button.setOnClickListener {
            listener.onEditDialogCancelButtonPressed()
        }

        return view
    }

    companion object {
        @JvmStatic
        fun newUpdateDialogInstance(
                profile: ProfilePresenterModel
        ) =
                EditProfileDialogFragment().apply {
                    arguments = Bundle().apply {
                        putString(ID, profile.id)
                        putString(FIRST_NAME, profile.firstName)
                        putString(LAST_NAME, profile.lastName)
                        putString(USERNAME, profile.username)
                    }
                }
    }

    interface EditProfileDialogFragmentListener {
        fun onEditDialogOkButtonPressed(profile: ProfilePresenterModel)
        fun onEditDialogCancelButtonPressed()
    }

}