package com.badcompany.pitak.ui.main

import android.animation.LayoutTransition
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.badcompany.pitak.App
import com.badcompany.pitak.R
//import com.badcompany.pitak.di.viewmodels.MainViewModelFactory
//import com.badcompany.pitak.fragments.MainNavHostFragment
import com.badcompany.pitak.ui.BaseActivity
import com.badcompany.pitak.ui.addpost.AddPostActivity
import com.badcompany.pitak.ui.auth.AuthActivity
import com.badcompany.pitak.ui.main.mytrips.MyTripsFragment
import com.badcompany.pitak.ui.main.profile.ProfileFragment
import com.badcompany.pitak.ui.main.searchtrip.SearchTripFragment
import com.badcompany.pitak.util.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import splitties.activities.start
import splitties.experimental.ExperimentalSplittiesApi
import javax.inject.Inject
import javax.inject.Named


class MainActivity : BaseActivity() {

    @ExperimentalSplittiesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        checkUserLogin()
        setTheme(R.style.NoActionBar)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupActionBar()
        setupListeners()
        subscribeObservers()

        nav_view.setupWithNavController(findNavController(R.id.nav_host_fragment))
    }

    private fun setupListeners() {
        addPost.setOnClickListener {
            start<AddPostActivity>()
        }
    }

    @ExperimentalSplittiesApi
    private fun checkUserLogin() {
        if (AppPreferences.token.isBlank()) {
            start<AuthActivity> { }
        }
    }

    private fun subscribeObservers() {
    }

    private fun setupActionBar() {
    }

    fun showTabLayout() {
        tab_layout.visibility = View.VISIBLE
    }

    fun hideTabLayout() {
        tab_layout.visibility = View.GONE
    }


}

