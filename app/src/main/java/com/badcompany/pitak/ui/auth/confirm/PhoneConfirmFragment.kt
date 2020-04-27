package com.badcompany.pitak.ui.auth.confirm

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.badcompany.pitak.R
import com.badcompany.pitak.ui.auth.AuthActivity
import kotlinx.android.synthetic.main.fragment_phone_confirm.*
import javax.inject.Inject


//@FlowPreview
//@ExperimentalCoroutinesApi
class PhoneConfirmFragment @Inject constructor(private val viewModelFactory: ViewModelProvider.Factory) :
    Fragment(R.layout.fragment_phone_confirm) {

    private val viewModel: PhoneConfirmViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.cancelActiveJobs()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()


        val navController = findNavController()
        confirm.isEnabled = true



    }

    private fun setupObservers() {

    }
    override fun onResume() {
        super.onResume()
        (activity as AuthActivity).showActionBar()
    }

}




