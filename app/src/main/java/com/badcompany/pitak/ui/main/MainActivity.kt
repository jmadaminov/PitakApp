package com.badcompany.pitak.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CheckedTextView
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigator
import com.badcompany.pitak.R
import com.badcompany.pitak.ui.BaseActivity
import com.badcompany.pitak.ui.addpost.AddPostActivity
import com.badcompany.pitak.ui.auth.AuthActivity
import com.badcompany.pitak.ui.dialogs.DialogAddCarFirst
import com.badcompany.pitak.ui.driver_post.DriverPostActivity
import com.badcompany.pitak.ui.driver_post.EXTRA_POST_ID
import com.badcompany.pitak.ui.intro.IntroActivity
import com.badcompany.pitak.ui.main.mytrips.MyTripsFragment
import com.badcompany.pitak.ui.main.notifications.NotificationsFragment
import com.badcompany.pitak.ui.main.profile.ProfileFragment
import com.badcompany.pitak.ui.main.searchtrip.SearchTripFragment
import com.badcompany.pitak.util.AppPrefs
import com.badcompany.pitak.util.ContextUtils.setLocale
import kotlinx.android.synthetic.main.activity_main.*
import splitties.activities.start
import splitties.experimental.ExperimentalSplittiesApi


class MainActivity : BaseActivity() {

    private var notificationPostId: Long? = null
    private lateinit var navController: NavController

    val viewModel: MainViewModel by viewModels()

    @ExperimentalSplittiesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        setLocale(AppPrefs.language, this)
        checkUserLogin()
        setTheme(R.style.NoActionBar)
        super.onCreate(savedInstanceState)
        if (AppPrefs.isFirstTime) {
            startActivity(Intent(this, IntroActivity::class.java))
            finish()
        }
        notificationPostId = intent.extras?.getLong(EXTRA_POST_ID)

        notificationPostId?.let {
            startActivity(Intent(this, DriverPostActivity::class.java).apply {
                putExtra(EXTRA_POST_ID, notificationPostId)
            })
        }

        setContentView(R.layout.activity_main)
        setupActionBar()
        setupListeners()
        subscribeObservers()

        navController = findNavController(R.id.nav_host_fragment)

        viewModel.getActiveAppVersions()
    }

    private fun setupListeners() {
        addPost.setOnClickListener {
            if (!AppPrefs.defaultCarId.isNullOrBlank() && AppPrefs.defaultCarId != "0") {
                start<AddPostActivity>()
            } else {
                DialogAddCarFirst().show(supportFragmentManager, "")
            }
        }

        navSearch.setOnClickListener {
            if ((navController.currentDestination as FragmentNavigator.Destination).className == ProfileFragment::class.qualifiedName) {
                navController.navigate(R.id.action_nav_menu_profile_to_nav_menu_search)
            } else if ((navController.currentDestination as FragmentNavigator.Destination).className == MyTripsFragment::class.qualifiedName) {
                navController.navigate(R.id.action_nav_menu_my_trips_to_nav_menu_search)
            } else if ((navController.currentDestination as FragmentNavigator.Destination).className == NotificationsFragment::class.qualifiedName) {
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
            finish()
        }
    }

    private fun subscribeObservers() {

        viewModel.isAppVersionDeprecated.observe(this) { isDeprecated ->
            if (isDeprecated) DialogForceUpdate().show(supportFragmentManager, "")
        }
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

