package app.oways.luckytrip.ui.adapter.remote

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.oways.luckytrip.R
import app.oways.luckytrip.callback.DestinationCallback
import app.oways.luckytrip.data.remote.response.Destination
import app.oways.luckytrip.util.comparator.DestinationComparator
import app.oways.luckytrip.util.comparator.SortBy
import app.oways.luckytrip.util.extentions.gone
import app.oways.luckytrip.util.extentions.load
import app.oways.luckytrip.util.extentions.visible
import kotlinx.android.synthetic.main.destination_list_item.view.*
import java.util.*
import kotlin.collections.ArrayList

class DestinationAdapter( private val destinationCallback: DestinationCallback): RecyclerView.Adapter<DestinationAdapter.DestinationViewHolder>() {

    private var list: ArrayList<Destination>? = null
    private var selectedList: ArrayList<Long> = ArrayList()
    private var sortBy: SortBy = SortBy.NON
    inner class DestinationViewHolder(itemView: View, private val destinationCallback: DestinationCallback) : RecyclerView.ViewHolder(itemView) {
        fun bind(
            destination: Destination?
        ) {
            with(itemView) {
                destination?.apply {
                    thumb_image_view?.load(thumbnail?.image_url)
                    if (destinationVideo?.url.isNullOrEmpty()) video_icon?.gone() else video_icon?.visible()
                    title_text_view?.text = "$city, $countryName"

                    picker_icon?.apply {
                        isSelected = selectedList?.contains(destination.id)?:false
                        setOnClickListener {
                            if (selectedList?.contains(destination.id)){
                                destinationCallback.onRemoveDestination(destination)
                                selectedList.remove(destination.id!!)
                            }else {
                                destinationCallback.onSaveDestination(destination)
                                selectedList.add(destination.id!!)
                            }
                            this@DestinationAdapter.notifyDataSetChanged()
                        }
                    }

                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DestinationViewHolder {
        return DestinationViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.destination_list_item, parent, false),
            destinationCallback
        )
    }

    override fun onBindViewHolder(holder: DestinationViewHolder, position: Int) {
        holder.bind(list?.get(position))
    }

    override fun getItemCount() = list?.size ?: 0

    fun addList(data: ArrayList<Destination>) {
        list = data
        Collections.sort(list, DestinationComparator(sortBy))
        this.notifyDataSetChanged()
    }
    fun addSelected(list: ArrayList<Long>) {
        selectedList = list
    }

    fun setSortBy(sort: SortBy) {
        sortBy = sort
        list?.let { addList(it) }
    }
}
