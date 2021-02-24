package com.badcompany.pitak.ui.feedback


import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.widget.doOnTextChanged
import com.badcompany.pitak.R
import com.badcompany.pitak.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_feedback.*

class FeedbackActivity : BaseActivity() {
    private val viewModel: FeedbackViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback)

        setupActionbar()
        attachListeners()
        subscribeObservers()
    }

    private fun subscribeObservers() {

        viewModel.isLoading.observe(this, {
            if (it) btnSend.startAnimation() else btnSend.revertAnimation()
        })

        viewModel.feedbackResponse.observe(this, {
            finish()
        })

    }

    private fun attachListeners() {
        btnSend.setOnClickListener {
            viewModel.sendFeedback(noteInput.text.toString())
        }

        noteInput.doOnTextChanged { text, start, before, count ->
            btnSend.isEnabled = !text.isNullOrBlank()
        }

    }

    private fun setupActionbar() {
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