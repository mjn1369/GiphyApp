package apps.mjn.giphyapp.network

import apps.mjn.giphyapp.models.response.RandomResponse
import apps.mjn.giphyapp.models.response.TrendingResponse

import io.reactivex.Observable
import retrofit2.http.*

/**
 * Created by mJafarinejad on 2/26/2018.
 */

interface ApiInterface {

    @GET("/v1/gifs/trending")
    fun getTrending(@Query("api_key") apiKey:String, @Query("limit") limit:Long, @Query("offset") offset:Long): Observable<TrendingResponse>

    @GET("/v1/gifs/random")
    fun getRandom(@Query("api_key") apiKey:String): Observable<RandomResponse>

}
