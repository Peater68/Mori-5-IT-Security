package hu.bme.caffshare.ui.register

import android.graphics.Color
import android.os.Bundle
import android.view.View
import co.zsmb.rainbowcake.base.OneShotEvent
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.dagger.getViewModelFromFactory
import co.zsmb.rainbowcake.navigation.navigator
import com.google.android.material.snackbar.Snackbar
import hu.bme.caffshare.R
import hu.bme.caffshare.ui.login.LoginFragment
import hu.bme.caffshare.util.isNotEmpty
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
            if (usernameInput.isNotEmpty() && passwordInput.isNotEmpty() && repeatPasswordInput.isNotEmpty()) {
                if (passwordInput.text != repeatPasswordInput.text) {
                    repeatPasswordInput.error = "Password does not match!"
                } else {
                    viewModel.register(usernameInput.text, passwordInput.text)
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
                Snackbar.make(requireView(), "Successful registration!", Snackbar.LENGTH_SHORT)
                    .apply {
                        setBackgroundTint(Color.GREEN)
                        setTextColor(Color.WHITE)
                    }.show()
                navigator?.replace(LoginFragment())
            }
            is RegisterViewModel.RegistrationFailed -> {
                Snackbar.make(requireView(), "Error while registering!", Snackbar.LENGTH_SHORT)
                    .apply {
                        setBackgroundTint(Color.RED)
                        setTextColor(Color.WHITE)
                    }.show()
            }
        }
    }
}
