package com.badcompany.pitak.ui.auth

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.badcompany.pitak.App
import com.badcompany.pitak.R
import com.badcompany.pitak.di.viewmodels.AuthViewModelFactory
import com.badcompany.pitak.fragments.AuthNavHostFragment
import com.badcompany.pitak.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_auth.*
import javax.inject.Inject
import javax.inject.Named

class AuthActivity : BaseActivity() {


    @Inject
    lateinit var viewModelFactory: AuthViewModelFactory

    @Inject
    @Named("AuthFragmentFactory")
    lateinit var fragmentFactory: FragmentFactory

    private val viewModel: AuthViewModel by viewModels {
        viewModelFactory
    }

//    lateinit var navController: NavController

    override fun inject() {
        (application as App).authComponent()
            .inject(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        inject()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
//        navController = findNavController(R.id.auth_fragments_container)

        subscribeObservers()
        onRestoreInstanceState()
        setSupportActionBar(tool_bar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        hideActionBar()

    }

    private fun subscribeObservers() {

    }

    var host: Fragment? = null
    lateinit var navHost: Fragment

    private fun onRestoreInstanceState() {
        host = supportFragmentManager.findFragmentById(R.id.auth_fragments_container)
        host?.let { /*do nothing*/ } ?: createNavHost()
    }

    private fun createNavHost() {
        navHost = AuthNavHostFragment.create(R.navigation.nav_auth_graph)
        supportFragmentManager.beginTransaction().replace(R.id.auth_fragments_container,
                                                          navHost,
                                                          getString(R.string.AuthNavHost))
            .setPrimaryNavigationFragment(navHost)
            .commit()
    }


    fun showActionBar() {
        tool_bar?.visibility = View.VISIBLE

    }

    fun hideActionBar() {
        tool_bar?.visibility = View.INVISIBLE
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }


}

