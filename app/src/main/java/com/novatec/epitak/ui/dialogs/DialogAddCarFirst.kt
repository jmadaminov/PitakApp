package com.novatec.epitak.ui.dialogs

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.novatec.epitak.R
import com.novatec.epitak.ui.addcar.AddCarActivity
import com.novatec.epitak.ui.passenger_post.PassengerPostActivity
import kotlinx.android.synthetic.main.dialog_add_car_first.*

const val KEY_ADD_CAR = "ADD_CAR"

class DialogAddCarFirst : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_add_car_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnOk.setOnClickListener {
            requireActivity().activityResultRegistry.register("KEY",
                                                              ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == AppCompatActivity.RESULT_OK) {
                    (requireActivity() as? PassengerPostActivity)?.showOfferDialog()
                }
                dismiss()
            }.launch(Intent(requireActivity(), AddCarActivity::class.java))
        }
        btnCancel.setOnClickListener {
            dismiss()
        }
    }

    override fun getTheme() = R.style.Theme_Dialog


}