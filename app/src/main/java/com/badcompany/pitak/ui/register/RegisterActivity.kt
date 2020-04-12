package com.badcompany.pitak.ui.register

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.badcompany.domain.domainmodel.User
import com.badcompany.pitak.App
import com.badcompany.pitak.R
import com.badcompany.pitak.di.viewmodels.RegisterViewModelFactory
import com.badcompany.pitak.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_register.*
import javax.inject.Inject

class RegisterActivity : BaseActivity() {

    override fun inject() {
        (application as App).registerComponent()
            .inject(this)
    }


    private lateinit var registerViewModel: RegisterViewModel

    @Inject
    lateinit var registerViewModelFactory: RegisterViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        inject()
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_register)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        registerViewModel = ViewModelProviders.of(this, registerViewModelFactory)
            .get(RegisterViewModel::class.java)

        setupObservers()

//        username.afterTextChanged {
//            registerViewModel.loginDataChanged(
//                username.text.toString(),
//                password.text.toString()
//            )
//        }

//        password.apply {
//            afterTextChanged {
//                registerViewModel.loginDataChanged(
//                    username.text.toString(),
//                    password.text.toString()
//                )
//            }
//
//            setOnEditorActionListener { _, actionId, _ ->
//                when (actionId) {
//                    EditorInfo.IME_ACTION_DONE ->
//                        registerViewModel.register(User(
//                            phone.text.toString(),
//                            name.text.toString(),
//                            surname.text.toString(),
//                            username.text.toString(),
//                            password.text.toString(),
//                            false)
//                        )
//                }
//                false
//            }
//        }

            register.setOnClickListener {
                register.startAnimation()
                registerViewModel.register(User(
                    phone.text.toString(),
                    name.text.toString(),
                    surname.text.toString(),/*
                    username.text.toString(),
                    password.text.toString(),*/
                    false)
                )
            }
    }

    private fun setupObservers() {

        registerViewModel.registerFormState.observe(this, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            register.isEnabled = loginState.isDataValid

//            if (loginState.usernameError != null) {
//                username.error = getString(loginState.usernameError)
//            }
//            if (loginState.passwordError != null) {
//                password.error = getString(loginState.passwordError)
//            }
        })

        registerViewModel.registerResult.observe(this, Observer {
            val registerResult = it ?: return@Observer

            if (registerResult.error != null) {
                showLoginFailed(registerResult.error)
            }
            if (registerResult.success != null) {
                updateUiWithUser(registerResult.success)
            }
            setResult(Activity.RESULT_OK)

            //Complete and destroy login activity once successful
            finish()
        })
    }

    private fun updateUiWithUser(model: RegisterUserView) {
        val welcome = getString(R.string.welcome)
        val displayName = model.displayName
        // TODO : initiate successful logged in experience
        Toast.makeText(
            applicationContext,
            "$welcome $displayName",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}
