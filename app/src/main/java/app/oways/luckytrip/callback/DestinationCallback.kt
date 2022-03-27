package app.oways.luckytrip.callback

import app.oways.luckytrip.data.remote.response.Destination

interface DestinationCallback {

    fun onSaveDestination(destination: Destination)
    fun onRemoveDestination(destination: Destination)
}