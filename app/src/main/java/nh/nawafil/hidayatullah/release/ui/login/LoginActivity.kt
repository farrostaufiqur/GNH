package nh.nawafil.hidayatullah.release.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import nh.nawafil.hidayatullah.release.R
import nh.nawafil.hidayatullah.release.databinding.ActivityLoginBinding
import nh.nawafil.hidayatullah.release.ui.first.FirstTimeActivity
import nh.nawafil.hidayatullah.release.ui.register.RegisterActivity
import nh.nawafil.hidayatullah.release.ui.splash.MainActivity
import nh.nawafil.hidayatullah.release.preferences.datastore.AppPreferences
import nh.nawafil.hidayatullah.release.preferences.datastore.PreferencesModelFactory
import nh.nawafil.hidayatullah.release.util.isEmailValid

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class LoginActivity : AppCompatActivity() {
    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding
    private lateinit var viewModel: LoginViewModel
    private var email: String? = null
    private var password: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        supportActionBar?.hide()
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

        setupViewModel()
        setupAction()
    }

    private fun setupViewModel() {
        val pref = AppPreferences.getInstance(dataStore)
        viewModel = ViewModelProvider(this, PreferencesModelFactory(pref))[LoginViewModel::class.java]
        viewModel.isLoading.observe(this@LoginActivity) {
            loading(it)
        }
        viewModel.message.observe(this@LoginActivity){
            showToast(it.toString())
        }
        viewModel.isSuccess.observe(this@LoginActivity){
            if (it == true){
                Intent(this@LoginActivity, MainActivity::class.java).also { intent ->
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

    private fun setupAction() {
        binding?.apply {
            btLogin.setOnClickListener {
                email = etEmailLogin.text.toString().trim()
                password = etPasswordLogin.text.toString().trim()
                when {
                    email!!.isBlank() -> etEmailLogin.error = getString(R.string.empty_email)
                    !email!!.isEmailValid() -> etEmailLogin.error = getString(R.string.invalid_email)
                    password!!.isBlank() -> etPasswordLogin.error = getString(R.string.empty_password)
                    else -> {
                        Log.d(TAG, "Email: $email, Password: $password")
                        viewModel.login(email!!, password!!)
                    }
                }
            }


            tvRegistHere.setOnClickListener {
                val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun loading(isLoading: Boolean){
        binding?.apply {
            if(isLoading){
                pbLogin.visibility = View.VISIBLE
            }else{
                pbLogin.visibility = View.GONE
            }
        }
    }

    private val onBackPressedCallback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            val intent = Intent(this@LoginActivity, FirstTimeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(EMAIL_KEY, email)
        outState.putString(PASSWORD_KEY, password)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        email = savedInstanceState.getString(EMAIL_KEY)
        password = savedInstanceState.getString(PASSWORD_KEY)
    }

    private fun showToast(text: String) {
        Toast.makeText(this@LoginActivity,
            text, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val TAG = "LoginActivity"
        const val EMAIL_KEY = "email-key"
        const val PASSWORD_KEY = "password-key"
    }
}