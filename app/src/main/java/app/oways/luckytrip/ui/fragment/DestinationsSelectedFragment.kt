package app.oways.luckytrip.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import app.oways.luckytrip.R
import app.oways.luckytrip.data.remote.DataState
import app.oways.luckytrip.ui.adapter.local.DestinationLoadStateAdapter
import app.oways.luckytrip.ui.adapter.local.SelectedDestinationAdapter
import app.oways.luckytrip.util.extentions.gone
import app.oways.luckytrip.util.extentions.visible
import app.oways.luckytrip.viewmodel.DestinationPickerViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.empty_list_layout.*
import kotlinx.android.synthetic.main.fragment_destinations_sellected.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DestinationsSelectedFragment : Fragment(R.layout.fragment_destinations_sellected) {

    private val destinationViewModel: DestinationPickerViewModel by viewModels()
    private var destinationAdapter: SelectedDestinationAdapter? = null
    private var recyclerListStatus: Bundle? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observe()
    }

    private fun observe() {
        destinationViewModel.selectedDestinationsLiveData.observe(viewLifecycleOwner, {
            count_tv?.text = String.format(getString(R.string.selected_destination_count), it?.size?:0)
            if (it?.isNullOrEmpty() == true){
                empty_container?.visible()
            }else{
                empty_container?.gone()
            }
        })

        destinationViewModel.selectedDestinations.observe(viewLifecycleOwner, { dataState->
            when(dataState){
                is DataState.Success->{

                    lifecycleScope.launch {

                        dataState.data.collectLatest { pagingData ->
                            destinationAdapter?.submitData(pagingData)
                        }
                    }
                }
                is DataState.Empty ->{
                    empty_container?.visible()
                }
            }
        })

    }

    private fun initView() {
        initRecyclerView()
        add_destination_btn?.setOnClickListener {
            findNavController().navigate(DestinationsSelectedFragmentDirections.actionToDestinationsPicker())
        }
    }

    private fun initRecyclerView() {
        if (destinationAdapter == null) {

            destinationAdapter = SelectedDestinationAdapter{destinationId->
                destinationViewModel.deleteDestinationById(destinationId)
            }
            movies_recycler_view?.apply {
                adapter = destinationAdapter?.withLoadStateHeaderAndFooter(
                    header = DestinationLoadStateAdapter(destinationAdapter!!::retry),
                    footer = DestinationLoadStateAdapter(destinationAdapter!!::retry)
                )
            }
            getDestinations()
        } else {
            restoreRecyclerState()
        }
    }

    private fun getDestinations() {
        destinationViewModel.getLocalDestinations()
    }

    private fun restoreRecyclerState() {
        if (recyclerListStatus != null) {
            movies_recycler_view?.layoutManager?.onRestoreInstanceState(
                recyclerListStatus?.getParcelable(
                    "KEY_RECYCLER_STATE"
                )
            )
            movies_recycler_view?.apply {
                postponeEnterTransition()
                viewTreeObserver.addOnPreDrawListener {
                    startPostponedEnterTransition()
                    true
                }
                this.adapter = destinationAdapter?.withLoadStateHeaderAndFooter(
                    header = DestinationLoadStateAdapter(
                        destinationAdapter!!::retry
                    ),
                    footer = DestinationLoadStateAdapter(
                        destinationAdapter!!::retry
                    )
                )
            }
        }
    }

    override fun onPause() {
        super.onPause()
        recyclerListStatus = Bundle()
        val listState = movies_recycler_view?.layoutManager?.onSaveInstanceState()
        recyclerListStatus?.putParcelable("KEY_RECYCLER_STATE", listState)

    }
}