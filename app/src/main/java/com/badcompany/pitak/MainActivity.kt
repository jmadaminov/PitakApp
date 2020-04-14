package com.badcompany.pitak

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.badcompany.pitak.di.viewmodels.MainViewModelFactory
import com.badcompany.pitak.ui.BaseActivity
import com.badcompany.pitak.ui.register.RegisterActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import javax.inject.Inject
import javax.inject.Named

class MainActivity : BaseActivity() {

    private lateinit var mainViewModel: MainViewModel

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory

    @Inject
    @Named("ProfileFragmentFactory")
    lateinit var profileFragmentFactory: FragmentFactory

    override fun inject() {
        supportFragmentManager.fragmentFactory = profileFragmentFactory
        (application as App).mainComponent()
            .inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        inject()
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = ViewModelProviders.of(this, mainViewModelFactory)
            .get(MainViewModel::class.java)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_profile
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

//        startActivity(Intent(this, RegisterActivity::class.java))
    }

}
