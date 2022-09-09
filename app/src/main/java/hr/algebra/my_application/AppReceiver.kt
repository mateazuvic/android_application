package hr.algebra.my_application

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import hr.algebra.my_application.framework.startActivity

class AppReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        context.startActivity<HostActivity>()
    }
}