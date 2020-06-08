package com.badcompany.pitak.ui.main.profile

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.badcompany.core.Constants.CODE_ADD_CAR
import com.badcompany.core.ErrorWrapper
import com.badcompany.core.ResultWrapper
import com.badcompany.core.exhaustive
import com.badcompany.domain.domainmodel.Car
import com.badcompany.domain.domainmodel.CarDetails
import com.badcompany.pitak.App
import com.badcompany.pitak.R
import com.badcompany.pitak.ui.addcar.AddCarActivity
import com.badcompany.pitak.ui.auth.AuthActivity
import com.badcompany.pitak.ui.viewgroups.CarItemView
import com.badcompany.pitak.ui.viewgroups.LoadingItem
import com.badcompany.pitak.util.AppPreferences
import com.google.android.material.snackbar.Snackbar
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.OnItemClickListener
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_profile.*
import splitties.experimental.ExperimentalSplittiesApi
import splitties.fragments.start
import splitties.preferences.edit
import javax.inject.Inject

//@FlowPreview
//@ExperimentalCoroutinesApi
class ProfileFragment @Inject constructor(private val viewModelFactory: ViewModelProvider.Factory) :
    Fragment(R.layout.fragment_profile) {

    private val adapter = GroupAdapter<GroupieViewHolder>()

    private val viewModel: ProfileViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.cancelActiveJobs()
    }

    @ExperimentalSplittiesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setupCarsRecyclerView()
        setupListeners()
        subscribeObservers()
        viewModel.getCarList(AppPreferences.token)

    }

    private fun setupCarsRecyclerView() {
        carsList.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)

        carsList.adapter = adapter
    }

    private fun subscribeObservers() {
        viewModel.carsResponse.observe(viewLifecycleOwner, Observer {
            val response = it ?: return@Observer
            when (response) {
                is ErrorWrapper.ResponseError -> {
                    adapter.clear()
                    adapter.notifyDataSetChanged()
                    Snackbar.make(clParent, response.message.toString(), Snackbar.LENGTH_SHORT)
                        .show()
                }
                is ErrorWrapper.SystemError -> {
                    adapter.clear()
                    adapter.notifyDataSetChanged()
                    Snackbar.make(clParent,
                                  getString(R.string.system_error),
                                  Snackbar.LENGTH_SHORT)
                        .show()

                }
                is ResultWrapper.Success -> {
                    showCars(response.value)
                }
                ResultWrapper.InProgress -> {
                    adapter.add(LoadingItem())
                }
            }.exhaustive

        })

    }

    private fun showCars(value: List<CarDetails>) {
        adapter.clear()
        value.forEach {
            adapter.add(CarItemView(it, OnItemClickListener { item, view ->
            }))
        }
        adapter.notifyDataSetChanged()
    }

    private fun setupListeners() {
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
            (requireActivity().applicationContext as App).releaseMainComponent()
        }


    }


    @ExperimentalSplittiesApi
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CODE_ADD_CAR && resultCode == RESULT_OK) {
            Log.d("ON ACTIVITY RESULT   ", "  $resultCode")
            viewModel.getCarList(AppPreferences.token)
        }
    }
}

