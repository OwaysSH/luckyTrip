package app.oways.luckytrip.ui.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import app.oways.luckytrip.R
import app.oways.luckytrip.ui.callback.DestinationCallback
import app.oways.luckytrip.ui.callback.IFragment
import app.oways.luckytrip.data.local.entity.DestinationEntity
import app.oways.luckytrip.data.remote.response.Destination
import app.oways.luckytrip.util.mapper.DestinationMapper
import app.oways.luckytrip.data.remote.DataState
import app.oways.luckytrip.data.remote.DestinationResponse
import app.oways.luckytrip.ui.adapter.remote.DestinationAdapter
import app.oways.luckytrip.util.comparator.SortBy
import app.oways.luckytrip.util.extentions.gone
import app.oways.luckytrip.util.extentions.visible
import app.oways.luckytrip.viewmodel.DestinationPickerViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_destinations_picker.*
import kotlinx.android.synthetic.main.picker_toolbar.*
import kotlinx.android.synthetic.main.progress_loading.*


@AndroidEntryPoint
class DestinationsPickerFragment : Fragment(R.layout.fragment_destinations_picker), IFragment,
    TextWatcher {

    private var searchValue: String? = null
    private val destinationViewModel: DestinationPickerViewModel by viewModels()
    private var destinationAdapter: DestinationAdapter? = null
    private var recyclerListStatus: Bundle? = null

    private var selectedList: HashMap<Long, DestinationEntity> = HashMap()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (destinationAdapter == null) {
            showProgress()
            initView()
        } else {
            restoreRecyclerState()
        }
        observers()
    }

    private fun initView() {
        initAdapter()
        setRecyclerViewAdapter()
        getDestinations()
        initSearchView()
    }

    private fun initSearchView() {
        search_edit_text?.addTextChangedListener(this)
        search_edit_text?.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                getDestinations(searchValue)
                true
            }
            false
        }
        search_btn?.setOnClickListener {
            getDestinations(searchValue)
        }

        sort_by_city_container?.setOnClickListener {
            it.isSelected = !it.isSelected
            sort_by_country_container?.isSelected = false
            destinationAdapter?.setSortBy(if (it.isSelected) SortBy.CITY else SortBy.NON)
        }
        sort_by_country_container?.setOnClickListener {
            it.isSelected = !it.isSelected
            sort_by_city_container?.isSelected = false
            destinationAdapter?.setSortBy(if (it.isSelected) SortBy.COUNTRY else SortBy.NON)
        }
    }

    private fun setCustomToolbar() {
        destination_selected_count_tv?.text = String.format(getString(R.string.selected_count),
            selectedList.size
        )
        save_destinations_image_view?.apply {
            val enabled = selectedList.size > 2
            if (enabled) visible() else gone()
            isEnabled = enabled
            isSelected = enabled
            setOnClickListener {
                destinationViewModel.addDestination(ArrayList(selectedList.values))
                onBack()
            }
        }
        back_btn?.setOnClickListener {
            onBack()
        }
    }


    private fun observers() {
        destinationViewModel.selectedDestinationsLiveData.observe(viewLifecycleOwner, {

            it?.map { it ->
                it.id?.let { id -> selectedList[id] = it }
            }
            destinationAdapter?.addSelected(ArrayList(selectedList.keys))
            setCustomToolbar()

        })
        destinationViewModel.destinations.observe(viewLifecycleOwner, Observer { dataState ->
            when (dataState) {
                is DataState.Success<DestinationResponse?> -> {
                    dataState.data?.destinationList?.let { list ->
                        hideProgress()
                        destinationAdapter?.addList(list)
                    }
                }
                is DataState.GenericError -> {
                    hideProgress()
                }
                is DataState.NetworkError -> {
                    hideProgress()
                }
            }
        })

    }

    private fun setRecyclerViewAdapter() {
        destination_recycler_view?.apply {
            this.adapter = destinationAdapter
        }
    }

    private fun initAdapter() {
        destinationAdapter = DestinationAdapter(object : DestinationCallback {
            override fun onSaveDestination(destination: Destination) {
                selectedList[destination.id!!] = DestinationMapper.transformItem(destination)
                setCustomToolbar()
            }

            override fun onRemoveDestination(destination: Destination) {
                selectedList.remove(destination.id)
                setCustomToolbar()
            }
        })
    }

    private fun restoreRecyclerState() {
        if (recyclerListStatus != null) {
            destination_recycler_view?.layoutManager?.onRestoreInstanceState(
                recyclerListStatus?.getParcelable(
                    "KEY_RECYCLER_STATE"
                )
            )
            destination_recycler_view?.apply {
                postponeEnterTransition()
                viewTreeObserver.addOnPreDrawListener {
                    startPostponedEnterTransition()
                    true
                }
            }
        }
    }

    private fun getDestinations(searchValue: String? = null) {
        destinationViewModel.getRemoteDestinations(searchValue)
    }

    override fun onPause() {
        super.onPause()
        recyclerListStatus = Bundle()
        val listState = destination_recycler_view?.layoutManager?.onSaveInstanceState()
        recyclerListStatus?.putParcelable("KEY_RECYCLER_STATE", listState)
    }

    override fun showProgress() {
        pb_loading_layout?.visible()
    }

    override fun hideProgress() {
        pb_loading_layout?.gone()
    }

    override fun onBack() {
        requireActivity().onBackPressed()
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {


    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

    }

    override fun afterTextChanged(editable: Editable?) {
        if (editable != null || editable.toString().isEmpty().not()) {
            searchValue = editable.toString().trim()
        }
    }
}