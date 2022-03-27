package app.oways.luckytrip.ui.adapter.local

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import app.oways.luckytrip.R
import app.oways.luckytrip.database.entity.DestinationEntity
import app.oways.luckytrip.util.extentions.gone
import app.oways.luckytrip.util.extentions.load
import app.oways.luckytrip.util.extentions.visible
import kotlinx.android.synthetic.main.destination_list_item.view.*

class SelectedDestinationAdapter(private val removeDestinationById: (id: Long) -> Unit) :
    PagingDataAdapter<DestinationEntity, SelectedDestinationAdapter.SelectedDestinationViewHolder>(
        COMPARATOR
    ) {

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<DestinationEntity>() {
            override fun areItemsTheSame(oldItem: DestinationEntity, newItem: DestinationEntity) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: DestinationEntity,
                newItem: DestinationEntity
            ) = oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: SelectedDestinationViewHolder, position: Int) {
        holder.bind(
            destination = getItem(position),
            removeDestinationById
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        SelectedDestinationViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.destination_list_item, parent, false)
        )

    inner class SelectedDestinationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(
            destination: DestinationEntity?,
            removeDestinationById: (id: Long) -> Unit
        ) {
            with(itemView) {
                destination?.apply {
                    thumb_image_view?.load(thumbnail)
                    if (isVideo == true) video_icon?.gone() else video_icon?.visible()
                    title_text_view?.text = "$city, $countryName"

                    picker_icon?.apply {
                        isSelected = true
                        setOnClickListener {
                            destination.id?.let { id ->
                                removeDestinationById(id)
                            }
                        }
                    }
                }
            }
        }
    }

}