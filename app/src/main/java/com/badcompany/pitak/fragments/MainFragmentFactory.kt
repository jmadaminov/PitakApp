package com.badcompany.pitak.fragments

import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.ViewModelProvider
import com.badcompany.pitak.di.main.MainScope
import com.badcompany.pitak.ui.main.mytrips.MyTripsFragment
import com.badcompany.pitak.ui.main.profile.ProfileFragment
import com.badcompany.pitak.ui.main.searchtrip.SearchTripFragment
//import com.codingwithmitch.openapi.di.main.MainScope
//import com.codingwithmitch.openapi.ui.main.account.AccountFragment
//import com.codingwithmitch.openapi.ui.main.account.ChangePasswordFragment
//import com.codingwithmitch.openapi.ui.main.account.UpdateAccountFragment
import javax.inject.Inject

@MainScope
class MainFragmentFactory @Inject constructor(private val viewModelFactory: ViewModelProvider.Factory) :
    FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String) =
        when (className) {
            SearchTripFragment::class.java.name -> {
                SearchTripFragment(viewModelFactory)
            }
            MyTripsFragment::class.java.name -> {
                MyTripsFragment(viewModelFactory)
            }
            ProfileFragment::class.java.name -> {
                ProfileFragment(viewModelFactory)
            }
            else -> {
                SearchTripFragment(viewModelFactory)
            }
        }


}