package hr.algebra.my_application

import android.content.Context
import android.content.Intent
import androidx.core.app.JobIntentService
import hr.algebra.my_application.api.NasaFetcher
import hr.algebra.my_application.framework.sendBroadcast
import hr.algebra.my_application.framework.setBooleanPreference


private const val JOB_ID = 1
class AppService: JobIntentService() {

    override fun onHandleWork(intent: Intent) {
        NasaFetcher(this).fetchItems()


 }

    companion object{
        fun enqueue(context: Context, intent: Intent) {
            enqueueWork(context, AppService::class.java, JOB_ID, intent)
        }
    }
}