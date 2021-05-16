package com.novatec.epitak.ui.auth

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.novatec.epitak.App
import com.novatec.epitak.R
//import com.novatec.epitak.di.viewmodels.AuthViewModelFactory
//import com.novatec.epitak.fragments.AuthNavHostFragment
import com.novatec.epitak.ui.BaseActivity
import com.novatec.epitak.ui.auth.confirm.SMS_CONSENT_REQUEST
import com.google.android.gms.auth.api.phone.SmsRetriever
import kotlinx.android.synthetic.main.activity_auth.*
import javax.inject.Inject
import javax.inject.Named

class AuthActivity : BaseActivity() {


    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        subscribeObservers()
    }

    private fun subscribeObservers() {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            // ...
            SMS_CONSENT_REQUEST ->
                // Obtain the phone number from the result
                if (resultCode == Activity.RESULT_OK && data != null) {
                    // Get SMS message content
                    val message = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE)
                    // Extract one-time code from the message and complete verification
                    // `message` contains the entire text of the SMS message, so you will need
                    // to parse the string.


                    // send one time code to the server
                } else {
                    // Consent denied. User can type OTC manually.
                }
        }
    }


}

