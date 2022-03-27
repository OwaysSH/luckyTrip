package app.oways.luckytrip.callback

import app.oways.luckytrip.service.dmain.Destination

interface DestinationCallback {

    fun onSaveDestination(destination: Destination)
    fun onRemoveDestination(destination: Destination)
}