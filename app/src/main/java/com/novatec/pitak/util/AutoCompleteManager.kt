package com.novatec.pitak.util

import android.content.Context
import android.text.Editable
import android.text.Spannable
import android.view.Gravity
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import com.novatec.pitak.R
import com.novatec.pitak.ui.addpost.destinations.DestFeedPresenter
import com.novatec.pitak.ui.interfaces.IOnPlaceSearchQueryListener
import com.novatec.pitak.ui.viewgroups.PlaceFeedItemView
import com.otaliastudios.autocomplete.Autocomplete
import com.otaliastudios.autocomplete.AutocompleteCallback
import com.otaliastudios.autocomplete.AutocompletePolicy
import splitties.experimental.ExperimentalSplittiesApi


/**
 * Created by jahon on 22-Jul-20
 */
@ExperimentalSplittiesApi class AutoCompleteManager(builder: Builder) {

    lateinit var toFeedCallback: AutocompleteCallback<PlaceFeedItemView>
    lateinit var fromFeedCallback: AutocompleteCallback<PlaceFeedItemView>
    var context: Context? = null

    //    var targetViewModel: ViewModel? = null
    var from: EditText? = null
    var to: EditText? = null
    var onPopUpItemClicked: (isFrom: Boolean,
                             item: PlaceFeedItemView) -> Unit = { _, _ -> }
    var onInputQuery: (queryStr: String, isFrom: Boolean) -> Unit = { _, _ -> }
    var updateBtnState: () -> Unit = { }

    init {
        context = builder.context
//        targetViewModel = builder.targetViewModel
        from = builder.from
        to = builder.to
        onPopUpItemClicked = builder.onPopUpItemClicked
        onInputQuery = builder.onQuery
        updateBtnState = builder.updateBtnState

        setupFromInputAutocomplete()
        setupToInputAutocomplete()

        setupListeners()
    }


    var fromFeed: Autocomplete<PlaceFeedItemView>? = null
    var toFeed: Autocomplete<PlaceFeedItemView>? = null

    @ExperimentalSplittiesApi
    lateinit var fromPresenter: DestFeedPresenter

    @ExperimentalSplittiesApi
    lateinit var toPresenter: DestFeedPresenter

    @ExperimentalSplittiesApi
    private fun setupFromInputAutocomplete() {
        fromPresenter =
            DestFeedPresenter(context!!,
                              from!!,
                              object : IOnPlaceSearchQueryListener {
                                  override fun onQuery(query: CharSequence?,
                                                       isFrom: Boolean,
                                                       isSelectedFromFeed: Boolean) {
                                      if (query!!.length % 3 == 0 && !isSelectedFromFeed) {
                                          onInputQuery(query.toString(), isFrom)
                                      }
                                  }
                              })

        fromFeedCallback =
            object : AutocompleteCallback<PlaceFeedItemView> {
                override fun onPopupItemClicked(editable: Editable,
                                                item: PlaceFeedItemView): Boolean {
                    editable.clear()
                    editable.insert(0, item.place.name)
                    from!!.clearFocus()
                    from!!.hideKeyboard()

                    onPopUpItemClicked(true, item)
                    updateBtnState()
                    return true
                }

                override fun onPopupVisibilityChanged(shown: Boolean) {
                }
            }

        fromFeed = Autocomplete.on<PlaceFeedItemView>(from)
            .with(autoCompletePolicy)
            .with(fromFeedCallback)
            .with(fromPresenter)
            .with(context!!.getDrawable(R.drawable.selector_rounded_corners))
            .with(3f)
            .build()
        fromFeed!!.setGravity(Gravity.BOTTOM)
    }

    @ExperimentalSplittiesApi
    private fun setupToInputAutocomplete() {
        toPresenter =
            DestFeedPresenter(context!!, to!!,
                              object : IOnPlaceSearchQueryListener {
                                  override fun onQuery(query: CharSequence?,
                                                       isFrom: Boolean,
                                                       isSelectedFromFeed: Boolean) {
                                      if (query!!.length % 3 == 0 && !isSelectedFromFeed) {
                                          onInputQuery(query.toString(), isFrom)
                                      }
                                  }
                              },
                              false)

        toFeedCallback =
            object : AutocompleteCallback<PlaceFeedItemView> {
                override fun onPopupItemClicked(editable: Editable,
                                                item: PlaceFeedItemView): Boolean {
                    editable.clear()
                    editable.insert(0, item.place.name)
                    to!!.clearFocus()
                    to!!.hideKeyboard()
                    onPopUpItemClicked(false, item)
                    updateBtnState()
                    return true
                }

                override fun onPopupVisibilityChanged(shown: Boolean) {
                }
            }

        toFeed = Autocomplete.on<PlaceFeedItemView>(to)
            .with(autoCompletePolicy)
            .with(toFeedCallback)
            .with(toPresenter)
            .with(context!!.getDrawable(R.drawable.selector_rounded_corners))
            .with(3f)
            .build()
        toFeed!!.setGravity(Gravity.END)
    }

    fun setupListeners() {
        from!!.setOnEditorActionListener { _, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    if (fromPresenter.getAdr()?.itemCount != 0) {
                        val itm = fromPresenter.getAdr()!!.getItem(0) as PlaceFeedItemView
                        from!!.clearFocus()
                        fromFeedCallback.onPopupItemClicked(from!!.editableText, itm)
                        to!!.requestFocus()
                        to!!.showKeyboard()
                    }
                }
            }
            false
        }
        to!!.setOnEditorActionListener { _, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    to!!.clearFocus()
                    if (toPresenter.getAdr()?.itemCount != 0) {
                        toFeedCallback.onPopupItemClicked(to?.editableText, toPresenter.getAdr()!!
                            .getItem(0) as PlaceFeedItemView)
                    }
                }
            }
            false
        }
    }

    fun dispose() {
        toPresenter.disposeAdr()
        fromPresenter.disposeAdr()
    }

//    @ExperimentalSplittiesApi
//    val autoCompleteQueryListener = object : IOnPlaceSearchQueryListener {
//        override fun onQuery(query: CharSequence?, isFrom: Boolean, isSelectedFromFeed: Boolean) {
//            if (query!!.length % 3 == 0 && !isSelectedFromFeed) {
//                onInputQuery(query.toString(), isFrom)
////                if (viewModel is DestinationsViewModel) (viewModel!! as DestinationsViewModel).getPlacesFeed(
////                    query.toString(),
////                    isFrom)
////                else if (viewModel is SearchTripViewModel) (viewModel!! as SearchTripViewModel).getPlacesFeed(
////                    query.toString(),
////                    isFrom)
//            }
//        }
//    }


    private val autoCompletePolicy = object : AutocompletePolicy {
        override fun shouldShowPopup(text: Spannable?, cursorPos: Int): Boolean {
            return text!!.length > 0
        }

        override fun shouldDismissPopup(text: Spannable?, cursorPos: Int): Boolean {
            return text!!.length == 0
        }

        override fun getQuery(text: Spannable?): CharSequence {
            return text!!
        }

        override fun onDismiss(text: Spannable?) {
        }

    }


    class Builder {
        var context: Context? = null

        //        var targetViewModel: ViewModel? = null
        var from: EditText? = null
        var to: EditText? = null
        var onPopUpItemClicked: (isFrom: Boolean,
                                 item: PlaceFeedItemView) -> Unit = { _, _ -> }
        var onQuery: (queryStr: String, isFrom: Boolean) -> Unit =
            { _, _ -> }
        var updateBtnState: () -> Unit = { }

        inline fun with(context: () -> Context): Builder {
            this.context = context()
            return this
        }

//        inline fun targetViewModel(viewModel: () -> ViewModel): Builder {
//            this.targetViewModel = viewModel()
//            return this
//        }

        inline fun fromEditText(fromInput: () -> EditText): Builder {
            this.from = fromInput()
            return this
        }

        inline fun toEditText(toInput: () -> EditText): Builder {
            this.to = toInput()
            return this
        }

        fun popUpClickAction(onPopUpItemClicked: (isFrom: Boolean,
                                                  item: PlaceFeedItemView) -> Unit): Builder {
            this.onPopUpItemClicked = onPopUpItemClicked
            return this
        }

        fun updateBtnStateAction(updateBtnState: () -> Unit): Builder {
            this.updateBtnState = updateBtnState
            return this
        }

        fun onQueryAction(onQuery: (queryStr: String, isFrom: Boolean) -> Unit): Builder {
            this.onQuery = onQuery
            return this
        }

        fun create(): AutoCompleteManager {
            if (context == null /*|| targetViewModel == null */ || from == null || to == null) throw Exception(
                "SOME PARAMS NULL")
            return AutoCompleteManager(this)
        }


    }

}