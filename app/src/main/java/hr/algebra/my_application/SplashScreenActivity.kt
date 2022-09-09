package hr.algebra.my_application

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hr.algebra.my_application.databinding.ActivitySplashScreenBinding
import hr.algebra.my_application.framework.*

private const val DELAY = 3000L
const val DATA_IMPORTED="hr.algebra.my_application.data_imported"
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startAnimations()
        redirect()
    }

    private fun startAnimations() {
        binding.ivSplash.startAnimation(R.anim.rotate)
        binding.tvSplash.startAnimation(R.anim.blink)
    }

    private fun redirect() {

        //ako podaci postoje, redirect na Host
        if(getBooleanPreference(DATA_IMPORTED)) {
            callDelayed(DELAY) {startActivity<HostActivity>()} //nasa ekstenzijska mtoda
        } else {  //ako podaci ne postoje:
            if(isOnline()) {   // 1) ako si online odi na servis i skini podatka
                setBooleanPreference(DATA_IMPORTED, true)
                Intent(this, AppService::class.java).apply {
                    AppService.enqueue(
                        this@SplashScreenActivity,
                        this)       //prvi je outer this iz scope-a activitija, drugi je od Intenta
                 }
            } else {    // 2) ako nisi online ispisi poruku i izadji
                binding.tvSplash.text = getString(R.string.no_internet)
                callDelayed(DELAY) { finish() } //kad je parametar zadnji lambda mozemo u capicama
            }
        }
    }


}