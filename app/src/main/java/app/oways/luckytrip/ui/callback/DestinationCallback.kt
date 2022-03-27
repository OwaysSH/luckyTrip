package app.oways.luckytrip.ui.callback

import app.oways.luckytrip.data.remote.response.Destination

interface DestinationCallback {

    fun onSaveDestination(destination: Destination)
    fun onRemoveDestination(destination: Destination)
}