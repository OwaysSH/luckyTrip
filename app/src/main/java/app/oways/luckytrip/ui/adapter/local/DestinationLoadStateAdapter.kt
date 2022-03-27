package app.oways.luckytrip.ui.adapter.local

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import app.oways.luckytrip.util.extentions.gone
import app.oways.luckytrip.util.extentions.visible

class DestinationLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<LoadStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        return when (loadState) {
            is LoadState.Loading -> LoadStateViewHolder.Loading(parent, retry)
            is LoadState.Error -> LoadStateViewHolder.Error(parent, retry)
            else -> LoadStateViewHolder.NotLoading(parent, retry)
        }
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        when (holder) {
            is LoadStateViewHolder.Loading -> {
                if (loadState.endOfPaginationReached) {
                    holder.itemView.gone()
                } else {
                    holder.itemView.visible()
                    holder.bind()
                }
            }
            is LoadStateViewHolder.Error -> holder.bind()
        }
    }
}