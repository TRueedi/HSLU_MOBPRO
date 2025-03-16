package ch.hslu.mobpro.business.bands

import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface BandsApiService {

    @GET("all.json")
    suspend fun getBandNames(): Response<List<BandCode>>
    @GET("info/{bandCode}.json")
    suspend fun getBandInfo(@Path("bandCode") bandCode: String): Response<BandInfo>
}

@Serializable
data class BandCode(
    val name: String,
    val code: String
)

@Serializable
data class BandInfo(
    val name: String,
    val foundingYear: Int,
    val homeCountry: String,
    val bestOfCdCoverImageUrl: String?
)