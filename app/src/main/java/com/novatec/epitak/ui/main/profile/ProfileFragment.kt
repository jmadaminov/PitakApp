package com.novatec.epitak.ui.main.profile

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.novatec.core.Constants.CODE_ADD_CAR
import com.novatec.core.Constants.TXT_CAR
import com.novatec.core.ErrorWrapper
import com.novatec.core.ResultWrapper
import com.novatec.core.exhaustive
import com.novatec.domain.domainmodel.CarDetails
import com.novatec.epitak.R
import com.novatec.epitak.ui.addcar.AddCarActivity
import com.novatec.epitak.ui.addcar.MyItemClickListener
import com.novatec.epitak.ui.auth.AuthActivity
import com.novatec.epitak.ui.edit_profile.EditProfileActivity
import com.novatec.epitak.ui.feedback.FeedbackActivity
import com.novatec.epitak.ui.interfaces.IOnSignOut
import com.novatec.epitak.ui.main.MainActivity
import com.novatec.epitak.ui.settings.SettingsActivity
import com.novatec.epitak.ui.viewgroups.CarItemView
import com.novatec.epitak.ui.viewgroups.ItemAddCar
import com.novatec.epitak.ui.viewgroups.LoadingItem
import com.novatec.epitak.util.UserPrefs
import com.novatec.epitak.util.loadRound
import com.novatec.epitak.viewobjects.CarViewObj
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.item_car.view.*
import splitties.experimental.ExperimentalSplittiesApi
import splitties.fragments.start
import splitties.preferences.edit

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile), IOnSignOut {

    private val adapter = GroupAdapter<GroupieViewHolder>()

    private val viewModel: ProfileViewModel by viewModels()

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
        viewModel.getCarList()
        setupViews()
    }

    @ExperimentalSplittiesApi
    private fun setupViews() {
        (activity as MainActivity).hideTabLayout()
        nameSurname.text = "${UserPrefs.name} ${UserPrefs.surname}"
        phone.text = "+${UserPrefs.phone}"
        if (UserPrefs.rating > 0) {
            ratingBarDriver.isVisible = true
            ratingBarDriver.text = UserPrefs.rating.toString()
        }

    }

    private fun setupCarsRecyclerView() {
        carsList.adapter = adapter
    }

    private fun subscribeObservers() {
        viewModel.carsResponse.observe(viewLifecycleOwner, Observer {
            val response = it ?: return@Observer
            when (response) {
                is ErrorWrapper.RespError -> {
                    adapter.clear()
                    adapter.notifyDataSetChanged()
                    Snackbar.make(clParent, response.message, Snackbar.LENGTH_SHORT)
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
                    adapter.clear()
                    adapter.add(LoadingItem())
                }
            }.exhaustive

        })

        viewModel.deleteCarResponse.observe(viewLifecycleOwner, Observer {
            val response = it ?: return@Observer
            when (response) {
                is ErrorWrapper.RespError -> {
                    Snackbar.make(clParent, response.message, Snackbar.LENGTH_SHORT)
                        .show()
                }
                is ErrorWrapper.SystemError -> {
                    Snackbar.make(clParent, getString(R.string.system_error), Snackbar.LENGTH_SHORT)
                        .show()
                }
                is ResultWrapper.Success -> {
                    showCars(response.value)
                }
                ResultWrapper.InProgress -> {
                }
            }.exhaustive

        })

        viewModel.defaultCarResponse.observe(viewLifecycleOwner, Observer {
            val response = it ?: return@Observer
            when (response) {
                is ErrorWrapper.RespError -> {
                    Snackbar.make(clParent, response.message.toString(), Snackbar.LENGTH_SHORT)
                        .show()
                }
                is ErrorWrapper.SystemError -> {
                    Snackbar.make(clParent,
                                  getString(R.string.system_error),
                                  Snackbar.LENGTH_SHORT)
                        .show()
                }
                is ResultWrapper.Success -> {
                    val editingCarItem = (adapter.getItem(response.value) as CarItemView)
                    UserPrefs.edit {
                        defaultCarId = editingCarItem.car.id.toString()
                    }
                    editingCarItem.car.def = true
                    adapter.notifyItemChanged(response.value)
                }
                ResultWrapper.InProgress -> {
                }
            }.exhaustive

        })

    }

    @ExperimentalSplittiesApi
    private fun showCars(value: List<CarDetails>) {
        adapter.clear()
        UserPrefs.edit { defaultCarId = null }
        value.forEach { carDetails ->
            if (carDetails.def) {
                UserPrefs.edit { defaultCarId = carDetails.id.toString() }
            }

            adapter.add(CarItemView(carDetails, object : MyItemClickListener {
                override fun onClick(pos: Int, view: View) {
                    val popUpMenu = PopupMenu(context, view.carAction)
                    requireActivity().menuInflater.inflate(R.menu.car_item_menu, popUpMenu.menu)
                    popUpMenu.setOnMenuItemClickListener { menuItem ->
                        when (menuItem.itemId) {
                            R.id.delete -> {
                                viewModel.deleteCar(carDetails.id!!)
                            }
                            R.id.edit -> {
                                val intent = Intent(context, AddCarActivity::class.java)
                                intent.putExtra(TXT_CAR, CarViewObj.fromCarModel(carDetails))
                                startActivityForResult(intent, CODE_ADD_CAR)
                            }
                            R.id.setDefault -> {
                                viewModel.setDefault(carDetails.id!!, pos)
                            }
                            else -> {

                            }
                        }
                        true
                    }
                    popUpMenu.show()
                }
            }))
        }
        if (value.size < 4) {
            adapter.add(ItemAddCar({ item, view ->
                                       val intent = Intent(context, AddCarActivity::class.java)
                                       startActivityForResult(intent, CODE_ADD_CAR)
                                   }))
        }
        adapter.notifyDataSetChanged()
    }

    private fun setupListeners() {

        ivEditProfile.setOnClickListener {
            start<EditProfileActivity> {}
        }

        btnFeedback.setOnClickListener {
            start<FeedbackActivity> {}
        }

        settings.setOnClickListener {
            start<SettingsActivity> {}
        }


        signOut.setOnClickListener {
            val dialog = DialogSignOut()
            dialog.setTargetFragment(this, 88)
            dialog.show(parentFragmentManager, "")
        }


    }


    @ExperimentalSplittiesApi
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CODE_ADD_CAR && resultCode == RESULT_OK) {
            Log.d("ON ACTIVITY RESULT   ", "  $resultCode")
            viewModel.getCarList()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        carsList.adapter = null
    }

    override fun onSignOut() {
        requireActivity().finish()
        UserPrefs.prefs.edit().clear().apply()
        start<AuthActivity> {}
    }

    override fun onResume() {
        super.onResume()
        if (UserPrefs.avatar.isNotBlank()) profilePhoto.loadRound(UserPrefs.avatar)
        nameSurname.text = "${UserPrefs.name} ${UserPrefs.surname}"
    }
}

