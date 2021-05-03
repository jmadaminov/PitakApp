package com.novatec.pitak.ui.auth.register

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.novatec.core.*
import com.novatec.domain.domainmodel.User
import com.novatec.pitak.App
import com.novatec.pitak.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_phone_confirm.*
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_register.errorMessage
import kotlinx.android.synthetic.main.fragment_register.ivBack

@AndroidEntryPoint
class RegisterFragment : Fragment(R.layout.fragment_register) {

    val args: RegisterFragmentArgs by navArgs()

    lateinit var navController: NavController

    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.cancelActiveJobs()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setupObservers()
//        phone.setMaskedText(args.phone.numericOnly().substring(3, args.phone.numericOnly().length))
        phone.setText(args.phone)

        ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        register.isEnabled = true

        navController = findNavController()

        register.setOnClickListener {
            viewModel.register(User(phone.text.toString().numericOnly(),
                                    name.text.toString(),
                                    surname.text.toString(),
                                    App.uuid,
                                    Constants.ROLE_DRIVER))
        }

    }

    override fun onResume() {
        super.onResume()
    }

    private fun setupObservers() {

        viewModel.registerFormState.observe(viewLifecycleOwner, Observer {
            val loginState = it ?: return@Observer
            register.isEnabled = loginState.isDataValid
        })

        viewModel.regResp.observe(viewLifecycleOwner, Observer {
            val response = it ?: return@Observer
            when (response) {
                is ErrorWrapper.RespError -> {
                    register.revertAnimation()
                    if (response.code == Constants.errPhoneFormat) {
                        phone.error = getString(R.string.incorrect_phone_number_format)
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

}


