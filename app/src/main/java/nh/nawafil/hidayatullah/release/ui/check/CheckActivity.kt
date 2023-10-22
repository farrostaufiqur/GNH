package nh.nawafil.hidayatullah.release.ui.check

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import nh.nawafil.hidayatullah.release.databinding.ActivityCheckBinding

class CheckActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityCheckBinding
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCheckBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
    }
}