package com.badcompany.pitak.ui.main.profile

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.badcompany.core.Constants.CODE_ADD_CAR
import com.badcompany.pitak.R
import com.badcompany.pitak.ui.addcar.AddCarActivity
import com.badcompany.pitak.ui.auth.AuthActivity
import com.badcompany.pitak.util.AppPreferences
import kotlinx.android.synthetic.main.fragment_profile.*
import splitties.fragments.start
import splitties.preferences.edit
import javax.inject.Inject

//@FlowPreview
//@ExperimentalCoroutinesApi
class ProfileFragment @Inject constructor(private val viewModelFactory: ViewModelProvider.Factory) :
    Fragment(R.layout.fragment_profile) {

    private val viewModel: ProfileViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.cancelActiveJobs()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

//        change_password.setOnClickListener {
//            findNavController().navigate(R.id.action_accountFragment_to_changePasswordFragment)
//        }
//
//        logout_button.setOnClickListener {
//            viewModel.logout()
//        }
//
//        subscribeObservers()

        carNameAndNumber.setOnClickListener {
//            start<AddCarActivity>()
            val intent = Intent(context, AddCarActivity::class.java)
            startActivityForResult(intent, CODE_ADD_CAR)
        }

        signOut.setOnClickListener {
            requireActivity().finish()
            AppPreferences.edit {
                token = ""
                name = ""
                surname = ""
            }
            start<AuthActivity> {}
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CODE_ADD_CAR && resultCode == RESULT_OK) {

        }

    }
}

