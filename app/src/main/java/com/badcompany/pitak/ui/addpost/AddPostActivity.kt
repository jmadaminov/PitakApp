package com.badcompany.pitak.ui.addpost

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.badcompany.pitak.App
import com.badcompany.pitak.R
import com.badcompany.pitak.di.viewmodels.AddPostViewModelFactory
import com.badcompany.pitak.fragments.AddPostNavHostFragment
import com.badcompany.pitak.ui.BaseActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import splitties.experimental.ExperimentalSplittiesApi
import javax.inject.Inject
import javax.inject.Named


class AddPostActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: AddPostViewModelFactory


    private val viewmodel: AddPostViewModel by viewModels {
        viewModelFactory
    }

    @Inject
    @Named("AddPostFragmentFactory")
    lateinit var fragmentFactory: FragmentFactory


    override fun inject() {
        (application as App).addPostComponent()
            .inject(this)
    }

    @InternalCoroutinesApi
    @ExperimentalCoroutinesApi
    @ExperimentalSplittiesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        inject()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_post)
        setupActionBar()
        onRestoreInstanceState()
        subscribeObservers()
        setupListeners()
    }


    private fun setupListeners() {


    }

    private fun subscribeObservers() {


    }


    var host: Fragment? = null
    lateinit var navHost: Fragment

    private fun onRestoreInstanceState() {
        host = supportFragmentManager.findFragmentById(R.id.add_post_fragments_container)
        host?.let { /*do nothing*/ } ?: createNavHost()
    }

    private fun createNavHost() {
        navHost = AddPostNavHostFragment.create(R.navigation.add_post_nav_graph)
        supportFragmentManager.beginTransaction()
            .replace(R.id.add_post_fragments_container, navHost, getString(R.string.AuthNavHost))
            .setPrimaryNavigationFragment(navHost)
            .commit()
    }


    private fun setupActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }


}
