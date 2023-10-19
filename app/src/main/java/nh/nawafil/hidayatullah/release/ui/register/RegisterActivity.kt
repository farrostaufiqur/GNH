package nh.nawafil.hidayatullah.release.ui.register

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import nh.nawafil.hidayatullah.release.R
import nh.nawafil.hidayatullah.release.databinding.ActivityRegisterBinding
import nh.nawafil.hidayatullah.release.ui.first.FirstTimeActivity
import nh.nawafil.hidayatullah.release.ui.login.LoginActivity
import nh.nawafil.hidayatullah.release.util.isEmailValid

class RegisterActivity : AppCompatActivity() {
    private var _binding: ActivityRegisterBinding? = null
    private val binding get() = _binding
    private lateinit var viewModel: RegisterViewModel
    private var name: String? = null
    private var email: String? = null
    private var password: String? = null
    private var rePassword: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        supportActionBar?.hide()
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

        setupViewModel()
        setupAction()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this)[RegisterViewModel::class.java]
        viewModel.isLoading.observe(this) {
            loading(it)
        }
        viewModel.message.observe(this){
            showToast(it.toString())
        }
        viewModel.isSuccess.observe(this){
            if (it == true){
                Intent(this, LoginActivity::class.java).also { intent ->
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

    private fun setupAction() {
        binding?.apply {
            btRegist.setOnClickListener {
                name = etNameRegist.text.toString().trim()
                email = etEmailRegist.text.toString().trim()
                password = etPasswordRegist.text.toString().trim()
                rePassword = etConfirmPassRegist.text.toString().trim()
                when {
                    name!!.isBlank() -> etNameRegist.error = getString(R.string.empty_name)
                    email!!.isBlank() -> etEmailRegist.error = getString(R.string.empty_email)
                    !email!!.isEmailValid() -> etEmailRegist.error = getString(R.string.invalid_email)
                    password!!.isBlank() -> etPasswordRegist.error = getString(R.string.empty_password)
                    rePassword!!.isBlank() -> etConfirmPassRegist.error = getString(R.string.empty_password)
                    rePassword!=password -> etConfirmPassRegist.error = getString(R.string.not_same_password)
                    else -> {
                        Log.d(TAG, "Name: $name, Email: $email, Password: $password")
                        viewModel.register(name!!, email!!, password!!)
                    }
                }
            }

            tvLoginHere.setOnClickListener {
                val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun loading(isLoading: Boolean){
        binding?.apply {
            if(isLoading){
                pbRegist.visibility = View.VISIBLE
            }else{
                pbRegist.visibility = View.GONE
            }
        }

    }

    private fun showToast(text: String) {
        Toast.makeText(this,
            text, Toast.LENGTH_SHORT).show()
    }

    private val onBackPressedCallback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            val intent = Intent(this@RegisterActivity, FirstTimeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(EMAIL_KEY, email)
        outState.putString(NAME_KEY, name)
        outState.putString(PASSWORD_KEY, password)
        outState.putString(REPASSWORD_KEY, rePassword)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        name = savedInstanceState.getString(NAME_KEY)
        email = savedInstanceState.getString(EMAIL_KEY)
        password = savedInstanceState.getString(PASSWORD_KEY)
        rePassword = savedInstanceState.getString(REPASSWORD_KEY)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val NAME_KEY = "name-key"
        const val EMAIL_KEY = "email-key"
        const val PASSWORD_KEY = "password-key"
        const val REPASSWORD_KEY = "repassword-key"
        const val TAG = "RegisterActivity"
    }
}