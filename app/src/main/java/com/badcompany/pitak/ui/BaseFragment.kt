package com.badcompany.pitak.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.badcompany.pitak.ui.profile.ProfileViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

/**
 * Created by jahon on 13-Apr-20
 */
@FlowPreview
@ExperimentalCoroutinesApi
abstract class BaseFragment constructor(@LayoutRes private val layoutRes: Int) :
    Fragment(layoutRes) {

//    val viewModel: ProfileViewModel by viewModels {
//        viewModelFactory
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        setupActionBarWithNavController(R.id.navigation_profile, activity as AppCompatActivity)
//        setupChannel()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
//        try{
//            uiCommunicationListener = context as UICommunicationListener
//        }catch(e: ClassCastException){
//            Log.e(TAG, "$context must implement UICommunicationListener" )
//        }
    }

}