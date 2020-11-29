package hu.bme.caffshare.ui.login

import android.os.Bundle
import android.view.View
import co.zsmb.rainbowcake.base.OneShotEvent
import co.zsmb.rainbowcake.base.RainbowCakeFragment
import co.zsmb.rainbowcake.dagger.getViewModelFromFactory
import co.zsmb.rainbowcake.navigation.navigator
import com.google.android.material.snackbar.Snackbar
import hu.bme.caffshare.R
import hu.bme.caffshare.ui.cafflist.CaffListFragment
import hu.bme.caffshare.ui.register.RegisterFragment
import hu.bme.caffshare.util.bottomNav
import hu.bme.caffshare.util.isNotEmpty
import hu.bme.caffshare.util.text
import hu.bme.caffshare.util.toolbar
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : RainbowCakeFragment<LoginViewState, LoginViewModel>() {

    override fun provideViewModel() = getViewModelFromFactory()
    override fun getViewResource() = R.layout.fragment_login

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
        setupRegisterButton()
        setupLoginButton()
        setupPasswordForgotButton()
    }

    private fun setupView() {
        toolbar.visibility = View.GONE
        bottomNav.visibility = View.GONE
    }

    private fun setupRegisterButton() {
        registerButton.setOnClickListener {
            navigator?.replace(RegisterFragment())
        }
    }

    private fun setupLoginButton() {
        loginButton.setOnClickListener {
            if (usernameInput.isNotEmpty() && passwordInput.isNotEmpty()) {
                viewModel.login(usernameInput.text, passwordInput.text)
            }
        }
    }

    private fun setupPasswordForgotButton() {
        forgotPasswordButton.setOnClickListener {
            Snackbar.make(requireView(), "Not implemented", Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun render(viewState: LoginViewState) = Unit

    override fun onEvent(event: OneShotEvent) {
        when (event) {
            is LoginViewModel.LoginSuccessful -> {
                navigator?.replace(CaffListFragment())
            }
            is LoginViewModel.LoginFailed -> {
                Snackbar.make(loginButton, "Wrong username or password!", Snackbar.LENGTH_SHORT)
                    .show()
            }
        }
    }
}