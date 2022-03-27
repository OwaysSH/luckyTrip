package app.oways.luckytrip.repository.remote

import app.oways.luckytrip.service.DestinationResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface DestinationServiceApi {

    @GET("test/destinations")
    suspend fun getDestinations( @Query("search_value") searchValue: String?, @Query("search_type") searchType: String?): Response<DestinationResponse>
}