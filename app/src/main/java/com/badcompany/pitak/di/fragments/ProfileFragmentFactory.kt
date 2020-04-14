package com.badcompany.pitak.di.fragments

import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.ViewModelProvider
import com.badcompany.pitak.di.main.MainScope
import com.badcompany.pitak.ui.profile.ProfileFragment
//import com.codingwithmitch.openapi.di.main.MainScope
//import com.codingwithmitch.openapi.ui.main.account.AccountFragment
//import com.codingwithmitch.openapi.ui.main.account.ChangePasswordFragment
//import com.codingwithmitch.openapi.ui.main.account.UpdateAccountFragment
import javax.inject.Inject

@MainScope
class ProfileFragmentFactory @Inject constructor(private val viewModelFactory: ViewModelProvider.Factory) :
    FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String) =
        when (className) {
            ProfileFragment::class.java.name -> {
                ProfileFragment(viewModelFactory)
            }
//            ChangePasswordFragment::class.java.name -> {
//                ChangePasswordFragment(viewModelFactory)
//            }
//
//            UpdateAccountFragment::class.java.name -> {
//                UpdateAccountFragment(viewModelFactory)
//            }
//
            else -> {
                ProfileFragment(viewModelFactory)
            }
        }


}