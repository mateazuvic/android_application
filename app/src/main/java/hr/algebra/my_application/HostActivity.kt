package hr.algebra.my_application

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import hr.algebra.my_application.databinding.ActivityHostBinding


class HostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(
            android.R.anim.fade_in,
            android.R.anim.fade_out) //prilikom prijelaza (podizanja Host activity-ja) ulazeci ce fadeIn, izlazeci fadeOut
        binding = ActivityHostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initHamburgerMenu()
        initNavigation()
    }

    private fun initHamburgerMenu() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)
    }

    private fun initNavigation() {
        val navControler = Navigation.findNavController(this, R.id.navHostFragment)
        NavigationUI.setupWithNavController(binding.navigationView, navControler)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                toggleDrawer()
                return true
            }
            R.id.menuExit -> {
                exitApp()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun exitApp() {
        AlertDialog.Builder(this).apply {
            setTitle(R.string.exit)
            setMessage(getString(R.string.exit_app_question))
            setIcon(R.drawable.exit)
            setCancelable(true)
            setNegativeButton("Cancel", null)
            setPositiveButton("Ok") { _, _ -> finish()}
            show()
        }
    }

    private fun toggleDrawer() {
        if(binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawers()
        } else {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.host_menu, menu)
        return true
    }
}