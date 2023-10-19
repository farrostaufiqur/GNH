package nh.nawafil.hidayatullah.release.ui.home.ui.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import nh.nawafil.hidayatullah.release.databinding.FragmentProfileBinding
import nh.nawafil.hidayatullah.release.preferences.datastore.AppPreferences
import nh.nawafil.hidayatullah.release.preferences.datastore.PreferencesModelFactory
import nh.nawafil.hidayatullah.release.preferences.shared.PreferencesModel
import nh.nawafil.hidayatullah.release.preferences.shared.UserPreferences
import nh.nawafil.hidayatullah.release.ui.splash.MainActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private var userId: String? = null
    private var userToken: String? = null
    private var userName: String? = null
    private var userUsername: String? = null
    private var userImage: String? = null
    private var userDateJoin: String? = null

    private lateinit var viewModel: ProfileViewModel
    private lateinit var userPreferences: UserPreferences
    private lateinit var prefModel: PreferencesModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        setupViewModel()
        getUserData()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() {
        binding.apply {
            val link = "https://nawafilonline.my.id/api/user/image/$userImage"
            tvProfileName.text = "Hi, $userName"
            tvProfileUsername.text = userUsername
            tvProfileDateJoin.text = userDateJoin
            Glide.with(requireActivity())
                .load(link)
                .into(ivProfileImage)
            btLogout.setOnClickListener{
                viewModel.logout()
                val intent = Intent(requireActivity(), MainActivity::class.java)
                intent.flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
            }
        }
    }

    private fun setupViewModel(){
        val pref = AppPreferences.getInstance(requireContext().dataStore)
        viewModel = ViewModelProvider(this, PreferencesModelFactory(pref))[ProfileViewModel::class.java]
    }

    private fun getUserData(){
        userPreferences = UserPreferences(requireContext())
        prefModel = userPreferences.getUser()
        userId = prefModel.id
        userToken = prefModel.token

        userName = prefModel.name
        userImage = prefModel.image
        userUsername = prefModel.username
        userDateJoin = "Joined since: ${prefModel.dateJoin}"
    }

    private fun showToast(text: String) {
        Toast.makeText(requireContext(),
            text, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}