package com.novatec.pitak.ui

import androidx.fragment.app.Fragment

/**
 * Created by jahon on 13-Apr-20
 */
abstract class BaseFragment : Fragment() {

//    val viewModel: ProfileViewModel by viewModels {
//        viewModelFactory
//    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
////        setupActionBarWithNavController(R.id.navigation_profile, activity as AppCompatActivity)
////        setupChannel()
//    }
//
//    override fun onAttach(context: Context) {
//        super.onAttach(context)
////        try{
////            uiCommunicationListener = context as UICommunicationListener
////        }catch(e: ClassCastException){
////            Log.e(TAG, "$context must implement UICommunicationListener" )
////        }
//    }


    abstract fun cancelActiveJobs()

}