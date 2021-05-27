//package com.novatec.epitak.ui.addpost.datetime
//
////import org.joda.time.DateTime
//import android.graphics.PorterDuff
//import android.os.Bundle
//import android.view.View
//import androidx.core.content.ContextCompat
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.activityViewModels
//import androidx.navigation.NavController
//import androidx.navigation.fragment.findNavController
//import androidx.navigation.fragment.navArgs
//import com.novatec.epitak.R
//import com.novatec.epitak.ui.addpost.AddPostViewModel
//import dagger.hilt.android.AndroidEntryPoint
//import kotlinx.android.synthetic.main.fragment_date_and_time.*
//import splitties.experimental.ExperimentalSplittiesApi
//import java.text.SimpleDateFormat
//import java.util.*
//
//
//@AndroidEntryPoint
//class DateAndTimeFragment : Fragment(R.layout.fragment_date_and_time) {
//
//    val args: DateAndTimeFragmentArgs by navArgs()
//
//    private lateinit var selectedDate: String
//    private var timeFirstPart = true
//    private var timeSecondPart = true
//    private var timeThirdPart = true
//    private var timeFourthPart = true
//    val dateFormat = SimpleDateFormat("dd.MM.yyyy")
//
//    private val activityViewModel: AddPostViewModel by activityViewModels()
//
//    lateinit var navController: NavController
//
//    @ExperimentalSplittiesApi
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        setupObservers()
//        setupListeners()
//        if (args.ISFROMPOSTPREVIEW) {
//            checkFirstPartDay.isChecked = activityViewModel.timeFirstPart
//            checkSecondPartDay.isChecked = activityViewModel.timeSecondPart
//            checkThirdPartDay.isChecked = activityViewModel.timeThirdPart
//            checkFourthPartDay.isChecked = activityViewModel.timeFourthPart
//        }
//
//        navController = findNavController()
//        setupCalendar()
//        updateNextButtonState()
//    }
//
//    private fun setupCalendar() {
//        val cal = Calendar.getInstance()
//        calendar.minDate = cal.timeInMillis
//
//        if (!args.ISFROMPOSTPREVIEW) {
//            calendar.date = Calendar.getInstance().timeInMillis
//            selectedDate = dateFormat.format(cal.time)
//        } else {
//            calendar.date = dateFormat.parse(activityViewModel.departureDate).time
//            selectedDate = activityViewModel.departureDate
//        }
//    }
//
//
//    @ExperimentalSplittiesApi
//    private fun setupListeners() {
////        asapCheck.setOnCheckedChangeListener { buttonView, isChecked ->
////
////            checkSecondPartDay.isEnabled = !isChecked
////            checkThirdPartDay.isEnabled = !isChecked
////            checkFirstPartDay.isEnabled = !isChecked
////            checkFourthPartDay.isEnabled = !isChecked
//
//        checkFirstPartDay.isChecked = true
//        checkSecondPartDay.isChecked = true
//        checkThirdPartDay.isChecked = true
//        checkFourthPartDay.isChecked = true
//
//
////            if (isChecked) {
////                calendar.selectedDate = CalendarDay.today()
////                calendar.state().edit()
////                    .setMinimumDate(CalendarDay.today())
////                    .setMaximumDate(CalendarDay.today())
////                    .commit()
//
//                var cal = Calendar.getInstance().timeInMillis
//                calendar.date = cal
//                selectedDate = dateFormat.format(cal)
////            } else {
////                val todayPlusThreeMonths = DateTime.now().plusMonths(3)
////                calendar.state().edit().setMinimumDate(CalendarDay.today())
////                    .setMaximumDate(CalendarDay.from(todayPlusThreeMonths.year,
////                                                     todayPlusThreeMonths.monthOfYear,
////                                                     todayPlusThreeMonths.dayOfMonth)).commit()
//
//
////            }
//
////        }
//
//
//        checkFirstPartDay.setOnCheckedChangeListener { buttonView, isChecked ->
//            timeFirstPart = isChecked
//            updateNextButtonState()
//        }
//        checkSecondPartDay.setOnCheckedChangeListener { buttonView, isChecked ->
//            timeSecondPart = isChecked
//            updateNextButtonState()
//        }
//        checkThirdPartDay.setOnCheckedChangeListener { buttonView, isChecked ->
//            timeThirdPart = isChecked
//            updateNextButtonState()
//        }
//        checkFourthPartDay.setOnCheckedChangeListener { buttonView, isChecked ->
//            timeFourthPart = isChecked
//            updateNextButtonState()
//        }
//
////        calendar.setOnDateChangedListener { widget, date, selected ->
////            val cal = Calendar.getInstance()
////            cal[Calendar.YEAR] = date.year
////            cal[Calendar.MONTH] = date.month - 1
////            cal[Calendar.DAY_OF_MONTH] = date.day
////            selectedDate = dateFormat.format(cal.time)
////        }
//
//        navNext.setOnClickListener {
//            activityViewModel.departureDate = selectedDate
//            activityViewModel.timeFirstPart = timeFirstPart
//            activityViewModel.timeSecondPart = timeSecondPart
//            activityViewModel.timeThirdPart = timeThirdPart
//            activityViewModel.timeFourthPart = timeFourthPart
//
//            navController.navigate(if (args.ISFROMPOSTPREVIEW) R.id.action_dateTimeFragment_to_previewFragment else R.id.action_dateTimeFragment_to_priceAndSeatFragment)
//        }
//
//        calendar.setOnDateChangeListener { view, year, month, dayOfMonth ->
//            val cal = Calendar.getInstance()
//            cal[Calendar.YEAR] = year
//            cal[Calendar.MONTH] = month
//            cal[Calendar.DAY_OF_MONTH] = dayOfMonth
//            selectedDate = dateFormat.format(cal.time)
////            asapCheck.isChecked = false
//        }
//
//    }
//
//    private fun updateNextButtonState() {
//        navNext.isEnabled =
//            !(!timeFirstPart && !timeSecondPart && !timeThirdPart && !timeFourthPart) && selectedDate != null
//
//        if (navNext.isEnabled) {
//            val bg = navNext.background
//            bg.setColorFilter(ContextCompat.getColor(requireContext(), R.color.colorPrimary),
//                              PorterDuff.Mode.SRC_ATOP)
//            navNext.background = bg
//        } else {
//            val bg = navNext.background
//            bg.setColorFilter(ContextCompat.getColor(requireContext(), R.color.ic_grey),
//                              PorterDuff.Mode.SRC_ATOP)
//            navNext.background = bg
//        }
//    }
//
//    @ExperimentalSplittiesApi
//    private fun setupObservers() {
//
//    }
//
//
//}
//
//
//
//
