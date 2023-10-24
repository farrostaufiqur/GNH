package nh.nawafil.hidayatullah.release.ui.register

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import nh.nawafil.hidayatullah.release.Constant.CAMERA_X
import nh.nawafil.hidayatullah.release.Constant.REQ_CODE_PERMIT
import nh.nawafil.hidayatullah.release.R
import nh.nawafil.hidayatullah.release.databinding.ActivityRegisterBinding
import nh.nawafil.hidayatullah.release.ui.camera.CameraActivity
import nh.nawafil.hidayatullah.release.ui.first.FirstTimeActivity
import nh.nawafil.hidayatullah.release.ui.login.LoginActivity
import nh.nawafil.hidayatullah.release.util.*
import java.io.File

class RegisterActivity : AppCompatActivity() {
    private var _binding: ActivityRegisterBinding? = null
    private val binding get() = _binding
    private lateinit var viewModel: RegisterViewModel
    private var image: String? = null
    private var name: String? = null
    private var username: String? = null
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

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQ_CODE_PERMIT
            )
        }
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
            btCamera.setOnClickListener { startCameraX() }
            btGallery.setOnClickListener { startGallery() }
            btRegist.setOnClickListener {
                name = etNameRegist.text.toString().trim()
                username = etUsernameRegist.text.toString().trim()
                password = etPasswordRegist.text.toString().trim()
                rePassword = etConfirmPassRegist.text.toString().trim()
                when {
                    name!!.isBlank() -> etNameRegist.error = getString(R.string.empty_name)
                    username!!.isBlank() -> etUsernameRegist.error = "Please enter your username"
                    password!!.isBlank() -> etPasswordRegist.error = getString(R.string.empty_password)
                    rePassword!!.isBlank() -> etConfirmPassRegist.error = getString(R.string.empty_password)
                    rePassword!=password -> etConfirmPassRegist.error = getString(R.string.not_same_password)
                    else -> {
                        viewModel.register(name!!, username!!, password!!, image!!)
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQ_CODE_PERMIT) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    "Please give permission first!",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun startCameraX() {
        val intent = Intent(this, CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_X) {
            @Suppress("DEPRECATION") val myFile = it.data?.getSerializableExtra("picture") as File
            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean
            image = encodeImage(myFile)
            val result = rotateBitmap(
                BitmapFactory.decodeFile(myFile.path),
                isBackCamera
            )

            binding?.ivRegisterPhoto?.setImageBitmap(result)
        }
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, getString(R.string.open_gallery))
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedImg, this)
            image = encodeImage(myFile)
            binding?.ivRegisterPhoto?.setImageURI(selectedImg)
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
        outState.putString(USERNAME_KEY, username)
        outState.putString(NAME_KEY, name)
        outState.putString(PASSWORD_KEY, password)
        outState.putString(REPASSWORD_KEY, rePassword)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        name = savedInstanceState.getString(NAME_KEY)
        username = savedInstanceState.getString(USERNAME_KEY)
        password = savedInstanceState.getString(PASSWORD_KEY)
        rePassword = savedInstanceState.getString(REPASSWORD_KEY)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val NAME_KEY = "name-key"
        const val USERNAME_KEY = "email-key"
        const val PASSWORD_KEY = "password-key"
        const val REPASSWORD_KEY = "repassword-key"
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }
}