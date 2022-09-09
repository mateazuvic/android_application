package hr.algebra.my_application.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

const val API_URL = "https://api.nasa.gov/planetary/"
interface NasaApi {
    @GET("apod?api_key=DEMO_KEY&count=10")
    fun fetchItems() : Call<List<NasaItem>>


   /* @GET("apod?api_key=DEMO_KEY") //&count=10
    fun fetchItems(@Path("count") count: Int) : Call<List<NasaItem>>*/
}