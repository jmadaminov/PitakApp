package com.badcompany.pitak.ui.profile

import android.content.Context
import android.os.Bundle
import androidx.annotation.NavigationRes
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.badcompany.pitak.MainActivity
import com.badcompany.pitak.R
import com.badcompany.pitak.ui.BaseFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
class ProfileFragment @Inject constructor(private val viewModelFactory: ViewModelProvider.Factory) :
    BaseFragment(R.layout.fragment_profile) {

    private val viewModel: ProfileViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Restore state after process death
        savedInstanceState?.let { inState ->
//            (inState[ACCOUNT_VIEW_STATE_BUNDLE_KEY] as AccountViewState?)?.let { viewState ->
//                viewModel.setViewState(viewState)
//            }
        }
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        setHasOptionsMenu(true)
//
////        change_password.setOnClickListener {
////            findNavController().navigate(R.id.action_accountFragment_to_changePasswordFragment)
////        }
////
////        logout_button.setOnClickListener {
////            viewModel.logout()
////        }
////
////        subscribeObservers()
//    }


    override fun onAttach(context: Context) {
        childFragmentManager.fragmentFactory = (activity as MainActivity).profileFragmentFactory
        super.onAttach(context)
    }


}

