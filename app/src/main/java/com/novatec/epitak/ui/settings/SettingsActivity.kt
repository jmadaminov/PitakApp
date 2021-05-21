package com.novatec.epitak.ui.settings


import android.os.Bundle
import android.view.MenuItem
import com.novatec.core.Constants
import com.novatec.epitak.R
import com.novatec.epitak.ui.BaseActivity
import com.novatec.epitak.util.AppPrefs
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setTitle(R.string.settings)


        cl_language.setOnClickListener {
            DialogLanguage().show(supportFragmentManager, "")
        }

        when (AppPrefs.language) {
            Constants.EN -> tv_language.text = "English"
            Constants.RU -> tv_language.text = "Русский"
            Constants.UZ -> tv_language.text = "O'zbek tili"
        }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

}