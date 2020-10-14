package com.badcompany.pitak.ui.feedback

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import com.badcompany.pitak.App
import com.badcompany.pitak.R
//import com.badcompany.pitak.di.viewmodels.MainViewModelFactory
import com.badcompany.pitak.ui.BaseActivity
//import com.badcompany.pitakpass.App
//import com.badcompany.pitakpass.R
//import com.badcompany.pitakpass.di.viewmodels.AddPostViewModelFactory
//import com.badcompany.pitakpass.di.viewmodels.MainViewModelFactory
//import com.badcompany.pitakpass.ui.BaseActivity
//import com.badcompany.pitakpass.ui.addpost.AddPostViewModel
import javax.inject.Inject

class FeedbackActivity : BaseActivity() {

//    @Inject
//    lateinit var viewModelFactory: MainViewModelFactory

//    private val viewmodel: FeedbackViewModel by viewModels {
//        viewModelFactory
//    }

//    override fun inject() {
//
//        (application as App).mainComponent()
//            .inject(this)
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)
//        setSupportActionBar(tool_bar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

}