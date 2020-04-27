package com.badcompany.pitak.fragments

import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.ViewModelProvider
import com.badcompany.pitak.di.auth.AuthScope
import com.badcompany.pitak.di.main.MainScope
import com.badcompany.pitak.ui.auth.confirm.PhoneConfirmFragment
import com.badcompany.pitak.ui.auth.login.LoginFragment
import com.badcompany.pitak.ui.auth.register.RegisterFragment
import com.badcompany.pitak.ui.main.mytrips.MyTripsFragment
import com.badcompany.pitak.ui.main.profile.ProfileFragment
import com.badcompany.pitak.ui.main.searchtrip.SearchTripFragment
//import com.codingwithmitch.openapi.di.main.MainScope
//import com.codingwithmitch.openapi.ui.main.account.AccountFragment
//import com.codingwithmitch.openapi.ui.main.account.ChangePasswordFragment
//import com.codingwithmitch.openapi.ui.main.account.UpdateAccountFragment
import javax.inject.Inject

@AuthScope
class AuthFragmentFactory @Inject constructor(private val viewModelFactory: ViewModelProvider.Factory) :
    FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String) =
        when (className) {
            LoginFragment::class.java.name -> {
                LoginFragment(viewModelFactory)
            }
            PhoneConfirmFragment::class.java.name -> {
                PhoneConfirmFragment(viewModelFactory)
            }
            RegisterFragment::class.java.name -> {
                RegisterFragment(viewModelFactory)
            }
            else -> {
                LoginFragment(viewModelFactory)
            }
        }


}