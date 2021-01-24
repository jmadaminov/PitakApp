package com.badcompany.pitak.ui.auth.confirm

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.badcompany.core.*
import com.badcompany.domain.domainmodel.AuthBody
import com.badcompany.domain.domainmodel.UserCredentials
import com.badcompany.pitak.App
import com.badcompany.pitak.R
import com.badcompany.pitak.ui.main.MainActivity
import com.badcompany.pitak.util.AppPrefs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_phone_confirm.*
import splitties.activities.start
import splitties.experimental.ExperimentalSplittiesApi
import splitties.preferences.edit

@AndroidEntryPoint
class PhoneConfirmFragment : Fragment(R.layout.fragment_phone_confirm) {

    private val viewModel: PhoneConfirmViewModel by viewModels()

    val args: PhoneConfirmFragmentArgs by navArgs()
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.startTimer()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()

        attachListeners()
        setupObservers()
    }

    private fun setupViews() {
        navController = findNavController()
        confirm.isEnabled = true
        code.setText(args.password)
    }

    private fun attachListeners() {
        confirm.setOnClickListener {
            viewModel.confirm(UserCredentials(args.phone.numericOnly(),
                                              code.text.toString(),
                                              App.uuid))
        }
        tvRequestCodeAgain.setOnClickListener {
            viewModel.requestCodeAgain(args.phone)
        }
        ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    @ExperimentalSplittiesApi
    private fun setupObservers() {
        viewModel.confirmResponse.observe(viewLifecycleOwner, Observer {
            val response = it ?: return@Observer

            when (response) {
                is ErrorWrapper.RespError -> {
                    confirm.revertAnimation()
                    if (response.code == Constants.errPhoneFormat) {
                        code.error = getString(R.string.incorrect_phone_number_format)
                    } else {
                        errorMessage.visibility = View.VISIBLE
                        errorMessage.text = response.message
                    }
                }
                is ErrorWrapper.SystemError -> {
                    errorMessage.visibility = View.VISIBLE
                    errorMessage.text = response.err.message
                    confirm.revertAnimation()
                }
                is ResultWrapper.Success -> {
                    confirm.revertAnimation()
                    saveCredentials(response)
                    context?.start<MainActivity> { }
                }
                ResultWrapper.InProgress -> {
                    errorMessage.visibility = View.INVISIBLE
                    confirm.startAnimation()
                }
            }.exhaustive

        })

        viewModel.respRegainCode.observe(viewLifecycleOwner) {
            when (it) {
                is ResponseError -> {
                    tvRequestCodeAgain.isClickable = true
                    tvRequestCodeAgain.text = getString(R.string.request_sms_again)
                }
                is ResponseSuccess -> {
                    tvRequestCodeAgain.isClickable = false
                    viewModel.startTimer()
                }
            }.exhaustive
        }
    }

    @ExperimentalSplittiesApi
    private fun saveCredentials(response: ResultWrapper.Success<AuthBody>) {
        AppPrefs.edit {
            token = response.value.jwt!!
            name = response.value.name!!
            surname = response.value.surname!!
            phone = response.value.phoneNum!!
            rating = response.value.rating
            defaultCarId = response.value.defCarId
        }
    }


    override fun onResume() {
        super.onResume()
    }


}




