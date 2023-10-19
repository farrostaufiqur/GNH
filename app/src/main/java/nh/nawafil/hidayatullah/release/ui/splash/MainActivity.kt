package nh.nawafil.hidayatullah.release.ui.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import nh.nawafil.hidayatullah.release.Constant.SPLASH_LOADING
import nh.nawafil.hidayatullah.release.UserPreferences
import nh.nawafil.hidayatullah.release.preferences.datastore.PreferencesModelFactory
import nh.nawafil.hidayatullah.release.databinding.ActivityMainBinding
import nh.nawafil.hidayatullah.release.preferences.shared.PreferencesModel
import nh.nawafil.hidayatullah.release.preferences.datastore.AppPreferences
import nh.nawafil.hidayatullah.release.ui.first.FirstTimeActivity
import nh.nawafil.hidayatullah.release.ui.home.HomeActivity


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        supportActionBar?.hide()

        setup()
        getPreference()
    }

    private fun setup() {
        val pref = AppPreferences.getInstance(dataStore)
        viewModel = ViewModelProvider(this, PreferencesModelFactory(pref))[MainViewModel::class.java]
    }

    private fun getPreference() {
        val handler = Handler(mainLooper)
        handler.postDelayed({
            viewModel.getTheme().observe(this) {
                if (it == true) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }
            val userPreferences = UserPreferences(this)
            val model = PreferencesModel()
            viewModel.authorized.observe(this) {
                if (it == false){
                    intentNotLogin()
                }
            }

            viewModel.getUserId().observe(this) {itId->
                viewModel.getUserToken().observe(this) {itToken->
                    if (itToken.isNullOrEmpty() && itId.isNullOrEmpty()) {
                        intentNotLogin()
                    }else{
                        viewModel.getUser(itId, itToken)
                        viewModel.user.observe(this){
                            if (it != null){
                                val (image, halaqahName, level, halaqahLevel, halaqahId, halaqahDateCreated, token, halaqahDateJoin, password, name, id, dateJoin, username) = it
                                model.id = id
                                model.token = token
                                model.name = name
                                model.username = username
                                model.level = level
                                model.image = image
                                model.dateJoin = dateJoin
                                model.halaqahId = halaqahId
                                model.halaqahName = halaqahName
                                model.halaqahLevel = halaqahLevel
                                model.halaqahDateCreated = halaqahDateCreated
                                model.halaqahDateJoin = halaqahDateJoin
                                userPreferences.setUser(model)
                                intentLogin()
                            }
                        }
                    }
                }
            }
        }, SPLASH_LOADING)
    }
    
    private fun intentNotLogin(){
        Intent(this, FirstTimeActivity::class.java).also { intent ->
            startActivity(intent)
            finish()
        }
    }

    private fun intentLogin(){
        Intent(this, HomeActivity::class.java).also { intent ->
            startActivity(intent)
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}