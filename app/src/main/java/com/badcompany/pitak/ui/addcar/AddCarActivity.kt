package com.badcompany.pitak.ui.addcar

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import com.badcompany.pitak.App
import com.badcompany.pitak.R
import com.badcompany.pitak.di.viewmodels.AddCarViewModelFactory
import com.badcompany.pitak.ui.BaseActivity
import javax.inject.Inject

class AddCarActivity : BaseActivity() {


    @Inject
    lateinit var viewModelFactory: AddCarViewModelFactory


    private val viewmodel: AddCarViewModel by viewModels {
        viewModelFactory
    }

    override fun inject() {
        (application as App).addCarComponent()
            .inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        inject()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_car)
        subscribeObservers()
        setupActionBar()

    }

    private fun setupActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }


    private fun subscribeObservers() {

    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

}
