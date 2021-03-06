package hu.bme.caffshare.ui.register

import android.os.Bundle
import android.view.View
import co.zsmb.rainbowcake.base.OneShotEvent
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.dagger.getViewModelFromFactory
import co.zsmb.rainbowcake.navigation.navigator
import hu.bme.caffshare.R
import hu.bme.caffshare.ui.login.LoginFragment
import hu.bme.caffshare.util.isNotEmpty
import hu.bme.caffshare.util.showErrorSnackBar
import hu.bme.caffshare.util.showSuccessSnackBar
import hu.bme.caffshare.util.text
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment : RainbowCakeFragment<RegisterViewState, RegisterViewModel>() {

    override fun provideViewModel() = getViewModelFromFactory()
    override fun getViewResource() = R.layout.fragment_register

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRegisterButton()
        setupLoginButton()
    }

    private fun setupRegisterButton() {
        registerButton.setOnClickListener {
            if (usernameInput.isNotEmpty() &&
                passwordInput.isNotEmpty() &&
                repeatPasswordInput.isNotEmpty() &&
                emailInput.isNotEmpty() &&
                firstNameInput.isNotEmpty() &&
                lastNameInput.isNotEmpty()
            ) {
                if (passwordInput.text != repeatPasswordInput.text) {
                    repeatPasswordInput.error = "Password does not match!"
                } else {
                    viewModel.register(
                        username = usernameInput.text,
                        password = passwordInput.text,
                        email = emailInput.text,
                        firstName = firstNameInput.text,
                        lastName = lastNameInput.text
                    )
                }
            }
        }
    }

    private fun setupLoginButton() {
        loginButton.setOnClickListener {
            navigator?.replace(LoginFragment())
        }
    }

    override fun render(viewState: RegisterViewState) = Unit

    override fun onEvent(event: OneShotEvent) {
        when (event) {
            is RegisterViewModel.RegistrationSuccessful -> {
                showSuccessSnackBar("Successful registration!")
                navigator?.replace(LoginFragment())
            }
            is RegisterViewModel.RegistrationFailed -> {
                showErrorSnackBar("Error while registering!")
            }
        }
    }
}
