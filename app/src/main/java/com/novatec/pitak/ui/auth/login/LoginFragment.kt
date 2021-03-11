package com.novatec.pitak.ui.auth.login

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.novatec.core.*
import com.novatec.pitak.R
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.credentials.Credential
import com.google.android.gms.auth.api.credentials.HintRequest
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_login.*


@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private val viewModel: LoginViewModel by viewModels()

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.cancelActiveJobs()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        setupObservers()

        navController = findNavController()

        tvGo.isEnabled = true
        tvGo.setOnClickListener {
            viewModel.login(phone.text.toString())
        }
    }

    override fun onResume() {
        super.onResume()
    }

    private fun setupObservers() {
        viewModel.loginResponse.observe(viewLifecycleOwner) {
            val response = it ?: return@observe

            when (response) {
                is ResponseError -> {
                    progress.isVisible = false
                    if (response.code == -1) {
                        val action =
                            LoginFragmentDirections.actionNavLoginFragmentToNavRegisterFragment(
                                viewModel.phoneNum)
                        findNavController().navigate(action)
                    } else if (response.code == Constants.errPhoneFormat) {
                        phone.error = getString(R.string.incorrect_phone_number_format)
                    } else {
                        errorMessage.visibility = View.VISIBLE
                        errorMessage.text = response.message
                    }
                }
                is ResponseSuccess -> {
                    progress.isVisible = false
                    val action =
                        LoginFragmentDirections.actionNavLoginFragmentToNavPhoneConfirmFragment(
                            password = response.value?.password,
                            phone = viewModel.phoneNum)
                    findNavController().navigate(action)
                }
            }.exhaustive
        }
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading ?: return@observe) {
                errorMessage.visibility = View.INVISIBLE
                progress.isVisible = true
            } else {
                progress.isVisible = false
            }

        }


    }
}




