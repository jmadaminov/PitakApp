package com.badcompany.pitak.fragments

//import com.codingwithmitch.openapi.di.main.MainScope
//import com.codingwithmitch.openapi.ui.main.account.AccountFragment
//import com.codingwithmitch.openapi.ui.main.account.ChangePasswordFragment
//import com.codingwithmitch.openapi.ui.main.account.UpdateAccountFragment
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.ViewModelProvider
import com.badcompany.pitak.di.addPost.AddPostScope
import com.badcompany.pitak.ui.addpost.choosedestinations.ChooseDestinationsFragment
import javax.inject.Inject

@AddPostScope
class AddPostFragmentFactory @Inject constructor(private val viewModelFactory: ViewModelProvider.Factory) :
    FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String) =
        when (className) {
            ChooseDestinationsFragment::class.java.name -> {
                ChooseDestinationsFragment(viewModelFactory)
            }
//            PhoneConfirmFragment::class.java.name -> {
//                PhoneConfirmFragment(viewModelFactory)
//            }
//            RegisterFragment::class.java.name -> {
//                RegisterFragment(viewModelFactory)
//            }
            else -> {
                ChooseDestinationsFragment(viewModelFactory)
            }
        }


}