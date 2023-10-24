package nh.nawafil.hidayatullah.release.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import carbon.widget.ImageView
import nh.nawafil.hidayatullah.release.R
import nh.nawafil.hidayatullah.release.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private var _binding: ActivityHomeBinding? = null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

        loading(true)
        setupActionBar()
        setupNavBar()
        loading(false)
    }

    private fun setupNavBar() {
        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_nawafil, R.id.nav_halaqah, R.id.nav_profile
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding?.navView?.apply {
            setupWithNavController(navController)
            itemIconTintList = null

            Log.d(TAG, "Destination: ${navController.currentDestination?.label.toString()}")
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.label){
                "Nawafil" -> {
                    changeActionBar(getString(R.string.bar_nawafil_text1), getString(R.string.bar_nawafil_text2), back = false, title = true)
                }

                "Halaqah" -> {
                    changeActionBar(getString(R.string.bar_halaqah_text1), getString(R.string.bar_halaqah_text2), back = true, title = true)
                }

                "Profile" -> {
                    changeActionBar(getString(R.string.bar_profile_text1), getString(R.string.bar_profile_text2), back = true, title = true)
                }
            }
        }
    }

    private fun setupActionBar(){
        this.supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setCustomView(R.layout.custom_action_bar)
    }

    @Suppress("SameParameterValue")
    private fun changeActionBar(text1: String, text2: String, back: Boolean = false, title: Boolean = true){
        val layoutTitle: LinearLayout = findViewById(R.id.layout_title)
        val btBack: ImageView  = findViewById(R.id.btBack)
        val tvText1: TextView = findViewById(R.id.tv_title_1)
        val tvText2: TextView = findViewById(R.id.tv_title_2)

        if (back){
            btBack.visibility = VISIBLE
        }else{
            btBack.visibility = GONE
        }

        if (title){
            layoutTitle.visibility = VISIBLE
        }else{
            layoutTitle.visibility = GONE
        }

        tvText1.text = text1
        tvText2.text = text2
    }

    private fun loading(isLoading: Boolean){
        binding?.apply {
            if(isLoading){
                pbHome.visibility = VISIBLE
            }else{
                pbHome.visibility = GONE
            }
        }
    }

    private val onBackPressedCallback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object{
        const val TAG = "HomeActivity"
    }
}