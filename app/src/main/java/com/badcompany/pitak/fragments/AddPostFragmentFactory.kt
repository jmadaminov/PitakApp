package com.badcompany.pitak.fragments

//import com.codingwithmitch.openapi.di.main.MainScope
//import com.codingwithmitch.openapi.ui.main.account.AccountFragment
//import com.codingwithmitch.openapi.ui.main.account.ChangePasswordFragment
//import com.codingwithmitch.openapi.ui.main.account.UpdateAccountFragment
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.ViewModelProvider
import com.badcompany.pitak.di.addPost.AddPostScope
import com.badcompany.pitak.ui.addpost.carandseat.CarAndSeatsFragment
import com.badcompany.pitak.ui.addpost.datetime.DateAndTimeFragment
import com.badcompany.pitak.ui.addpost.destinations.DestinationsFragment
import com.badcompany.pitak.ui.addpost.preview.PreviewFragment
import com.badcompany.pitak.ui.addpost.priceandtext.PriceAndNoteFragment
import javax.inject.Inject

@AddPostScope
class AddPostFragmentFactory @Inject constructor(private val viewModelFactory: ViewModelProvider.Factory) :
    FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String) =
        when (className) {
            DestinationsFragment::class.java.name -> {
                DestinationsFragment(viewModelFactory)
            }
            PriceAndNoteFragment::class.java.name -> {
                PriceAndNoteFragment(viewModelFactory)
            }
            PreviewFragment::class.java.name -> {
                PreviewFragment(viewModelFactory)
            }
            DateAndTimeFragment::class.java.name -> {
                DateAndTimeFragment(viewModelFactory)
            }
            CarAndSeatsFragment::class.java.name -> {
                CarAndSeatsFragment(viewModelFactory)
            }
            else -> {
                DestinationsFragment(viewModelFactory)
            }
        }


}