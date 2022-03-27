package app.oways.luckytrip.mapper

import app.oways.luckytrip.data.local.entity.DestinationEntity
import app.oways.luckytrip.data.remote.response.Destination

object DestinationMapper {

    fun transform(destinationList: ArrayList<Destination>?): ArrayList<DestinationEntity> {
        val destinationEntityList = arrayListOf<DestinationEntity>()
        destinationList?.forEach { destination ->
            destination.run {
                destinationEntityList.add(
                    DestinationEntity(
                        id = id,
                        city = city,
                        countryName = countryName,
                        airportName = airportName,
                        isVideo = destinationVideo != null,
                        thumbnail = thumbnail?.image_url
                    )
                )
            }
        }
        return destinationEntityList
    }

    fun transformItem(destination: Destination): DestinationEntity {

        var destinationEntity: DestinationEntity

        destination.run {

            destinationEntity = DestinationEntity(
                id = id,
                city = city,
                countryName = countryName,
                airportName = airportName,
                isVideo = destinationVideo != null,
                thumbnail = thumbnail?.image_url
            )

        }
        return destinationEntity
    }
}