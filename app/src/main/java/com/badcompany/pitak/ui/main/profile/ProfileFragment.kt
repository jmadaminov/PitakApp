package com.badcompany.pitak.ui.main.profile

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.badcompany.core.Constants.CODE_ADD_CAR
import com.badcompany.core.Constants.TXT_CAR
import com.badcompany.core.ErrorWrapper
import com.badcompany.core.ResultWrapper
import com.badcompany.core.exhaustive
import com.badcompany.domain.domainmodel.CarDetails
import com.badcompany.pitak.R
import com.badcompany.pitak.ui.addcar.AddCarActivity
import com.badcompany.pitak.ui.addcar.MyItemClickListener
import com.badcompany.pitak.ui.auth.AuthActivity
import com.badcompany.pitak.ui.edit_profile.EditProfileActivity
import com.badcompany.pitak.ui.feedback.FeedbackActivity
import com.badcompany.pitak.ui.interfaces.IOnSignOut
import com.badcompany.pitak.ui.main.MainActivity
import com.badcompany.pitak.ui.viewgroups.CarItemView
import com.badcompany.pitak.ui.viewgroups.ItemAddCar
import com.badcompany.pitak.ui.viewgroups.LoadingItem
import com.badcompany.pitak.util.AppPrefs
import com.badcompany.pitak.util.loadImageUrl
import com.badcompany.pitak.viewobjects.CarColorViewObj
import com.badcompany.pitak.viewobjects.CarViewObj
import com.badcompany.pitak.viewobjects.IdNameViewObj
import com.badcompany.pitak.viewobjects.ImageViewObj
import com.google.android.material.snackbar.Snackbar
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.OnItemClickListener
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.item_car.view.*
import splitties.experimental.ExperimentalSplittiesApi
import splitties.fragments.start
import splitties.preferences.edit

//@FlowPreview
//@ExperimentalCoroutinesApi
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
        cardProfile.setBackgroundResource(R.drawable.stroke_rounded_bottom_corners)
        nameSurname.text = "${AppPrefs.name} ${AppPrefs.surname}"
        phone.text = "+${AppPrefs.phone}"

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
                    adapter.clear()
                    adapter.add(LoadingItem())
                }
            }.exhaustive

        })

        viewModel.deleteCarResponse.observe(viewLifecycleOwner, Observer {
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
                    adapter.remove(adapter.getItem(response.value))
                    adapter.notifyItemRemoved(response.value)
                    if (adapter.itemCount > 0 && adapter.getItem(adapter.itemCount - 1) is CarItemView) {
                        adapter.add(ItemAddCar(OnItemClickListener { item, view ->
                            val intent = Intent(context, AddCarActivity::class.java)
                            startActivityForResult(intent, CODE_ADD_CAR)
                        }))
                        adapter.notifyItemInserted(adapter.itemCount)
                    } else {

                    }
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
                    (adapter.getItem(response.value) as CarItemView).car.def = true
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
        value.forEach { carDetails ->
            adapter.add(CarItemView(carDetails, object : MyItemClickListener {
                override fun onClick(pos: Int, view: View) {
                    val popUpMenu = PopupMenu(context, view.carAction)
                    requireActivity().menuInflater.inflate(R.menu.car_item_menu, popUpMenu.menu)
                    popUpMenu.setOnMenuItemClickListener { menuItem ->
                        when (menuItem.itemId) {
                            R.id.delete -> {
                                viewModel.deleteCar(carDetails.id!!, pos)
                            }
                            R.id.edit -> {
                                val intent = Intent(context, AddCarActivity::class.java)
                                val imgList = arrayListOf<ImageViewObj>()
                                carDetails.imageList?.forEach {
                                    imgList.add(ImageViewObj(it.id, it.link))
                                }
                                val carViewObj = CarViewObj(carDetails.id,
                                                            IdNameViewObj(carDetails.carModel!!.id),
                                                            ImageViewObj(carDetails.image!!.id,
                                                                         carDetails.image!!.link),
                                                            carDetails.fuelType,
                                                            CarColorViewObj(carDetails.carColor!!.id),
                                                            carDetails.carNumber,
                                                            carDetails.carYear,
                                                            carDetails.airConditioner,
                                                            carDetails.def,
                                                            imgList)
                                intent.putExtra(TXT_CAR, carViewObj)
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

        cardProfile.setOnClickListener {
            start<EditProfileActivity> {}
        }

        btnFeedback.setOnClickListener {
            start<FeedbackActivity> {}
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
        AppPrefs.edit {
            token = ""
            name = ""
            surname = ""
            phone = ""
        }
        start<AuthActivity> {}
    }

    override fun onResume() {
        super.onResume()
        if (AppPrefs.avatar.isNotBlank())      profilePhoto.loadImageUrl(AppPrefs.avatar)
        nameSurname.text = "${AppPrefs.name} ${AppPrefs.surname}"
    }
}

