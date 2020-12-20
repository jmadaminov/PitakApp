package com.badcompany.pitak.ui.auth

import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.badcompany.pitak.App
import com.badcompany.pitak.R
//import com.badcompany.pitak.di.viewmodels.AuthViewModelFactory
//import com.badcompany.pitak.fragments.AuthNavHostFragment
import com.badcompany.pitak.ui.BaseActivity
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

}

