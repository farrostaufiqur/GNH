package nh.nawafil.hidayatullah.release.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
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
import com.google.android.material.bottomnavigation.BottomNavigationView
import nh.nawafil.hidayatullah.release.R
import nh.nawafil.hidayatullah.release.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private var _binding: ActivityHomeBinding? = null
    private val binding get() = _binding

    private lateinit var title: LinearLayout
    private lateinit var back: ImageView
    private lateinit var text1: TextView
    private lateinit var text2: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

        loading(true)

        setupNavBar()
        setupActionBar()
        loading(false)
    }

    private fun setupNavBar() {
        val navView: BottomNavigationView? = binding?.navView

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_nawafil, R.id.nav_halaqah, R.id.nav_profile
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView?.setupWithNavController(navController)

        Log.d(TAG, "Selected: ${navView?.selectedItemId}")
    }

    private fun setupActionBar(){
        this.supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setCustomView(R.layout.custom_action_bar)
        title = findViewById(R.id.layout_title)
        back = findViewById(R.id.btBack)
        text1 = findViewById(R.id.tv_title_1)
        text2 = findViewById(R.id.tv_title_2)
        title.visibility = View.VISIBLE
        text1.text = "Nawafil "
        text2.text = "Hidayatullah"
        //todo: set all string res for action bar title
    }



    private fun loading(isLoading: Boolean){
        binding?.apply {
            if(isLoading){
                pbHome.visibility = View.VISIBLE
            }else{
                pbHome.visibility = View.GONE
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