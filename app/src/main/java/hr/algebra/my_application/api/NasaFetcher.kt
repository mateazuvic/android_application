package hr.algebra.my_application.api

import android.content.ContentValues
import android.content.Context
import android.util.Log
import androidx.navigation.fragment.DialogFragmentNavigatorDestinationBuilder
import hr.algebra.my_application.AppReceiver
import hr.algebra.my_application.DATA_IMPORTED
import hr.algebra.my_application.NASA_PROVIDER_URI
import hr.algebra.my_application.framework.sendBroadcast
import hr.algebra.my_application.framework.setBooleanPreference
import hr.algebra.my_application.handler.downloadImageAndStore
import hr.algebra.my_application.model.Item
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NasaFetcher(private val context: Context) {

    private var nasaApi: NasaApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        nasaApi = retrofit.create(NasaApi::class.java)
    }

    fun fetchItems() {
        val request = nasaApi.fetchItems()
        request.enqueue(object: Callback<List<NasaItem>> {
            override fun onResponse(
                call: Call<List<NasaItem>>,
                response: Response<List<NasaItem>>
            ) {
                response.body()?.let {
                    populateItems(it)
                }
            }

            override fun onFailure(call: Call<List<NasaItem>>, t: Throwable) {
                Log.d(javaClass.name, t.message, t)
            }

        })
    }

    private fun populateItems(nasaItems: List<NasaItem>) {
        //val items = mutableListOf<Item>()
        GlobalScope.launch {
            nasaItems.forEach {
                val picturePath = downloadImageAndStore(context, it.url, it.title.replace(" ", "_"))
                /*items.add(
                Item(null,
                    it.title,
                    it.explanation,
                    picturePath ?: "",
                    it.date,
                    false )
            )*/
                val values = ContentValues().apply {
                    put(Item::title.name, it.title)              //refleksija
                    put(Item::explanation.name, it.explanation)
                    put(Item::picturePath.name, picturePath ?: "")
                    put(Item::date.name, it.date)
                    put(Item::read.name, false)

                }
                try{
                    context.contentResolver.insert(NASA_PROVIDER_URI, values)
                } catch (e: Exception){
                    throw Exception(e.message)
                }

            }

            context.setBooleanPreference(DATA_IMPORTED, true)

            //sendBroadcast(Intent(this, AppReceiver::class.java))
            context.sendBroadcast<AppReceiver>()
        }
    }

}