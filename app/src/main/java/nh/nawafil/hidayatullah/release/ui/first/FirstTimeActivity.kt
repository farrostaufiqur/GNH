package nh.nawafil.hidayatullah.release.ui.first

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import nh.nawafil.hidayatullah.release.databinding.ActivityFirstTimeBinding
import nh.nawafil.hidayatullah.release.ui.login.LoginActivity
import nh.nawafil.hidayatullah.release.ui.register.RegisterActivity

class FirstTimeActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityFirstTimeBinding
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFirstTimeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setupAction()
    }

    private fun setupAction(){
        binding.btSignup.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}