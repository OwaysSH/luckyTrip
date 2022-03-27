package app.oways.luckytrip.util.comparator

import app.oways.luckytrip.data.remote.response.Destination
import java.util.Comparator

class DestinationComparator(private val sortBy: SortBy) : Comparator<Destination> {
    override fun compare(item1: Destination, item2: Destination): Int {
        return when(sortBy){
            SortBy.CITY ->{
                item1.city!!.compareTo(item2.city!!)

            }
            SortBy.COUNTRY ->{
                item1.countryName!!.compareTo(item2.countryName!!)

            }
            else ->{
                item1.id!!.compareTo(item2.id!!)

            }
        }
    }

}