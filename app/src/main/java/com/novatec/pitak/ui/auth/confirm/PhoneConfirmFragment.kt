package com.novatec.pitak.ui.auth.confirm

import android.content.*
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status
import com.google.android.gms.tasks.Task
import com.novatec.core.*
import com.novatec.domain.domainmodel.AuthBody
import com.novatec.domain.domainmodel.UserCredentials
import com.novatec.pitak.App
import com.novatec.pitak.R
import com.novatec.pitak.ui.main.MainActivity
import com.novatec.pitak.util.AppPrefs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_phone_confirm.*
import splitties.activities.start
import splitties.experimental.ExperimentalSplittiesApi
import splitties.preferences.edit


const val SMS_CONSENT_REQUEST = 2  // Set to an unused request code

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
        startSmsBroadcastReceiver()
        val intentFilter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
        requireActivity().registerReceiver(receiver,
                                           intentFilter,
                                           SmsRetriever.SEND_PERMISSION,
                                           null)
    }

    private fun startSmsBroadcastReceiver() {
        // Get an instance of SmsRetrieverClient, used to start listening for a matching
        // SMS message.
        val client = SmsRetriever.getClient(requireContext())
        // Starts SmsRetriever, which waits for ONE matching SMS message until timeout
        // (5 minutes). The matching SMS message will be sent via a Broadcast Intent with
        // action SmsRetriever#SMS_RETRIEVED_ACTION.
        val task: Task<Void> = client.startSmsRetriever()
        // Listen for success/failure of the start Task. If in a background thread, this
        // can be made blocking using Tasks.await(task, [timeout]);
        task.addOnSuccessListener {
            // Successfully started retriever, expect broadcast intent
            Log.d("SMSSSS", "")
        }

        task.addOnFailureListener {
            // Failed to start retriever, inspect Exception for more details
            Log.d("SMSSSS", "")

        }

    }


    private fun setupViews() {
        navController = findNavController()
        confirm.isEnabled = true
        edtCode.setText(args.password)
    }

    private fun attachListeners() {
        confirm.setOnClickListener {
            viewModel.confirm(UserCredentials(args.phone.numericOnly(),
                                              edtCode.text.toString(),
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
                        edtCode.error = getString(R.string.incorrect_phone_number_format)
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
                    requireActivity().finish()
                }
                ResultWrapper.InProgress -> {
                    errorMessage.visibility = View.INVISIBLE
                    confirm.startAnimation()
                }
            }.exhaustive

        })

        viewModel.requestSmsCountDown.observe(viewLifecycleOwner) { timeLeft ->

            if (timeLeft > 0) {
                tvRequestCodeAgain.isClickable = false
                tvRequestCodeAgain.text =
                    getString(R.string.request_sms_again_in, timeLeft.toString())
            } else {
                tvRequestCodeAgain.isClickable = true
                tvRequestCodeAgain.text = getString(R.string.request_sms_again)
            }


        }
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
    fun smsCodeReceived(code: String) {
        edtCode.setText(code)
        confirm.performClick()
    }

    @ExperimentalSplittiesApi
    private fun saveCredentials(response: ResultWrapper.Success<AuthBody>) {
        AppPrefs.edit {
            token = response.value.jwt!!
            userId = response.value.id!!
            name = response.value.name!!
            surname = response.value.surname!!
            phone = response.value.phoneNum!!
            rating = response.value.rating
            defaultCarId = response.value.defCarId
            response.value.image?.let{
                avatar = it.link!!
            }
        }
    }
    val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {

            if (SmsRetriever.SMS_RETRIEVED_ACTION == intent!!.action) {
                val extras = intent.extras
                val status: Status = extras!![SmsRetriever.EXTRA_STATUS] as Status
                when (status.statusCode) {
                    CommonStatusCodes.SUCCESS -> {    // Get SMS message contents
                        val message = extras[SmsRetriever.EXTRA_SMS_MESSAGE] as String?
                        message?.let {
                            smsCodeReceived(it.replace(" ", "").split(":")[1].substring(0, 5))
                        }
                    }
                    CommonStatusCodes.TIMEOUT -> {
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        requireActivity().unregisterReceiver(receiver)
        super.onDestroyView()
    }
}




