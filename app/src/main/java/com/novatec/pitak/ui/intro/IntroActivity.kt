package com.novatec.pitak.ui.intro

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.novatec.core.Constants
import com.novatec.pitak.R
import com.novatec.pitak.ui.main.MainActivity
import com.novatec.pitak.util.AppPrefs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_intro.*
import splitties.preferences.edit


@AndroidEntryPoint
class IntroActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)


        btn_russian.setOnClickListener {
            enterApp(Constants.RU)
            finish()
        }

        btn_english.setOnClickListener {
            enterApp(Constants.EN)
            finish()
        }

        btn_uzbek.setOnClickListener {
            enterApp(Constants.UZ)
            finish()
        }

    }

    private fun enterApp(lang: String) {
        AppPrefs.edit {
            isFirstTime = false
            language = lang
        }

        startActivity(Intent(this, MainActivity::class.java))
    }
}