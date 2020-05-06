package com.badcompany.pitak.ui.auth.register

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.badcompany.core.*
import com.badcompany.domain.domainmodel.User
import com.badcompany.pitak.R
import com.badcompany.pitak.ui.auth.AuthActivity
import kotlinx.android.synthetic.main.fragment_register.*
import javax.inject.Inject

class RegisterFragment @Inject constructor(private val viewModelFactory: ViewModelProvider.Factory) :
    Fragment(R.layout.fragment_register) {

    val args: RegisterFragmentArgs by navArgs()

    lateinit var navController: NavController

    private val viewModel: RegisterViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.cancelActiveJobs()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setupObservers()
        phone.setMaskedText(args.phone.numericOnly().substring(3, args.phone.numericOnly().length))

//        username.afterTextChanged {
//            viewModel.loginDataChanged(
//                username.text.toString(),
//                password.text.toString()
//            )
//        }

//        password.apply {
//            afterTextChanged {
//                viewModel.loginDataChanged(
//                    username.text.toString(),
//                    password.text.toString()
//                )
//            }
//
//            setOnEditorActionListener { _, actionId, _ ->
//                when (actionId) {
//                    EditorInfo.IME_ACTION_DONE ->
//                        viewModel.register(User(
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
        register.isEnabled = true

        navController = findNavController()

        register.setOnClickListener {
            viewModel.register(User(phone.text.toString().numericOnly(),
                name.text.toString(),
                surname.text.toString(),
                "DRIVER"))
//            navController.navigate(R.id.action_navRegisterFragment_to_navPhoneConfirmFragment)
        }

//        register.setOnClickListener {
//            register.startAnimation()
//            viewModel.register(User(
//                phone.text.toString(),
//                name.text.toString(),
//                surname.text.toString(),/*
//                    username.text.toString(),
//                    password.text.toString(),*/
//                false)
//            )
//        }
    }

    override fun onResume() {
        super.onResume()
        (activity as AuthActivity).showActionBar()
    }

    private fun setupObservers() {

        viewModel.registerFormState.observe(viewLifecycleOwner, Observer {
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

        viewModel.response.observe(viewLifecycleOwner, Observer {
            val response = it ?: return@Observer
//
////            loading.visibility = View.GONE
//            if (loginResult.error != null) {
//                showLoginFailed(loginResult.error)
//            }
//            if (loginResult.success != null) {
//                updateUiWithUser(loginResult.success)
//            }

            when (response) {
                is ErrorWrapper.ResponseError -> {
                    register.revertAnimation()
                    /*  if (response.code == -1) {
                          val action =
                              RegisterFragmentDirections.actionNavRegisterFragmentToNavPhoneConfirmFragment(
                                  response., viewModel.phoneNum )
                          findNavController().navigate(action)
                      } else */if (response.code == Constants.errPhoneFormat) {
                        phone.error = getString(R.string.incorrect_phone_number_format)
//                        errorMessage.visibility = View.VISIBLE
//                        errorMessage.text = response.message
                    } else {
                        errorMessage.visibility = View.VISIBLE
                        errorMessage.text = response.message
                    }
                }
                is ErrorWrapper.SystemError -> {
                    errorMessage.visibility = View.VISIBLE
                    errorMessage.text = "SYSTEM ERROR"
                    register.revertAnimation()
                }
                is ResultWrapper.Success -> {
                    register.revertAnimation()
                    val action =
                        RegisterFragmentDirections.actionNavRegisterFragmentToNavPhoneConfirmFragment(
                            response.value,
                            args.phone)
                    findNavController().navigate(action)
                }
                ResultWrapper.InProgress -> {
                    errorMessage.visibility = View.INVISIBLE
                    register.startAnimation()
                }
            }.exhaustive


        })
    }

    private fun updateUiWithUser(model: RegisterUserView) {
        val welcome = getString(R.string.welcome)
        val displayName = model.displayName
        // TODO : initiate successful logged in experience
        Toast.makeText(
            context!!,
            "$welcome $displayName",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(context!!, errorString, Toast.LENGTH_SHORT).show()
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
