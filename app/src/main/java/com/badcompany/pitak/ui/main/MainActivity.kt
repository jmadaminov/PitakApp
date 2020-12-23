package com.badcompany.pitak.ui.main

import android.os.Bundle
import android.view.View
import android.widget.CheckedTextView
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigator
import com.badcompany.pitak.R
//import com.badcompany.pitak.di.viewmodels.MainViewModelFactory
//import com.badcompany.pitak.fragments.MainNavHostFragment
import com.badcompany.pitak.ui.BaseActivity
import com.badcompany.pitak.ui.addpost.AddPostActivity
import com.badcompany.pitak.ui.auth.AuthActivity
import com.badcompany.pitak.ui.main.mytrips.MyTripsFragment
import com.badcompany.pitak.ui.main.notifications.NotificationsFragment
import com.badcompany.pitak.ui.main.profile.ProfileFragment
import com.badcompany.pitak.ui.main.searchtrip.SearchTripFragment
import com.badcompany.pitak.util.*
import kotlinx.android.synthetic.main.activity_main.*
import splitties.activities.start
import splitties.experimental.ExperimentalSplittiesApi


class MainActivity : BaseActivity() {

    private lateinit var navController: NavController

    @ExperimentalSplittiesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        checkUserLogin()
        setTheme(R.style.NoActionBar)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupActionBar()
        setupListeners()
        subscribeObservers()

        navController = findNavController(R.id.nav_host_fragment)
    }

    private fun setupListeners() {
        addPost.setOnClickListener {
            start<AddPostActivity>()
        }

        navSearch.setOnClickListener {
            if ((navController.currentDestination as FragmentNavigator.Destination).className == ProfileFragment::class.qualifiedName) {
                navController.navigate(R.id.action_nav_menu_profile_to_nav_menu_search)
            } else if ((navController.currentDestination as FragmentNavigator.Destination).className == MyTripsFragment::class.qualifiedName) {
                navController.navigate(R.id.action_nav_menu_my_trips_to_nav_menu_search)
            }else if ((navController.currentDestination as FragmentNavigator.Destination).className == NotificationsFragment::class.qualifiedName) {
                navController.navigate(R.id.action_nav_menu_notifications_to_nav_menu_search)
            }
            uncheckAllButMe(navSearch)
        }

        navMyTrips.setOnClickListener {
            if ((navController.currentDestination as FragmentNavigator.Destination).className == SearchTripFragment::class.qualifiedName) {
                navController.navigate(R.id.action_nav_menu_search_to_nav_menu_my_trips)
            } else if ((navController.currentDestination as FragmentNavigator.Destination).className == ProfileFragment::class.qualifiedName) {
                navController.navigate(R.id.action_nav_menu_profile_to_nav_menu_my_trips)
            } else if ((navController.currentDestination as FragmentNavigator.Destination).className == NotificationsFragment::class.qualifiedName) {
                navController.navigate(R.id.action_nav_menu_notifications_to_nav_menu_my_trips)
            }
            uncheckAllButMe(navMyTrips)
        }
        navProfile.setOnClickListener {
            if ((navController.currentDestination as FragmentNavigator.Destination).className == SearchTripFragment::class.qualifiedName) {
                navController.navigate(R.id.action_nav_menu_search_to_nav_menu_profile)
            } else if ((navController.currentDestination as FragmentNavigator.Destination).className == MyTripsFragment::class.qualifiedName) {
                navController.navigate(R.id.action_nav_menu_my_trips_to_nav_menu_profile)
            } else if ((navController.currentDestination as FragmentNavigator.Destination).className == NotificationsFragment::class.qualifiedName) {
                navController.navigate(R.id.action_nav_menu_notifications_to_nav_menu_profile)
            }
            uncheckAllButMe(navProfile)
        }
        navNotifications.setOnClickListener {
            if ((navController.currentDestination as FragmentNavigator.Destination).className == SearchTripFragment::class.qualifiedName) {
                navController.navigate(R.id.action_nav_menu_search_to_nav_menu_notifications)
            } else if ((navController.currentDestination as FragmentNavigator.Destination).className == MyTripsFragment::class.qualifiedName) {
                navController.navigate(R.id.action_nav_menu_my_trips_to_nav_menu_notifications)
            } else if ((navController.currentDestination as FragmentNavigator.Destination).className == ProfileFragment::class.qualifiedName) {
                navController.navigate(R.id.action_nav_menu_profile_to_nav_menu_notifications)
            }
            uncheckAllButMe(navNotifications)
        }
    }

    private fun uncheckAllButMe(target: CheckedTextView?) {
        navSearch?.isChecked = false
        navMyTrips?.isChecked = false
        navProfile?.isChecked = false
        navNotifications?.isChecked = false
        target?.isChecked = true
    }

    @ExperimentalSplittiesApi
    private fun checkUserLogin() {
        if (AppPrefs.token.isBlank()) {
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

