package com.badcompany.pitak.ui.auth.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.badcompany.core.*
import com.badcompany.pitak.R
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

        login.isEnabled = true
        login.setOnClickListener {
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
                    login.revertAnimation()
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
                    login.revertAnimation()
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
                login.startAnimation()
            } else {
                login.revertAnimation()
            }

        }


    }
}




