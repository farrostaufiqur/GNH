package nh.nawafil.hidayatullah.release.ui.home.ui.halaqah

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import nh.nawafil.hidayatullah.release.preferences.shared.PreferencesModel
import nh.nawafil.hidayatullah.release.preferences.shared.UserPreferences
import nh.nawafil.hidayatullah.release.data.network.response.HalaqahMemberItem
import nh.nawafil.hidayatullah.release.databinding.FragmentHalaqahBinding
import nh.nawafil.hidayatullah.release.ui.splash.MainActivity
import java.text.SimpleDateFormat
import java.util.*

class HalaqahFragment : Fragment() {
    private var _binding: FragmentHalaqahBinding? = null
    private val binding get() = _binding!!

    private var dateCreated: String? = null
    private var name: String? = null
    private var id: String? = null

    private var userId: String? = null
    private var userToken: String? = null
    private var userHalaqah: String? = null
    private var currentDate: String? = null

    private var halaqahName: String? = null
    private var halaqahDateCreated: String? = null
    private var memberCount: Int = 0

    private lateinit var viewModel: HalaqahViewModel
    private lateinit var userPreferences: UserPreferences
    private lateinit var prefModel: PreferencesModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHalaqahBinding.inflate(inflater, container, false)
        setupViewModel()
        getUserData()
        getCurrentDate()
        setupView()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getHalaqahMember(userId, userToken, userHalaqah)
        setupHalaqahList()
        viewModel.member.observe(this@HalaqahFragment){
            setupMemberData(it)
        }
    }

    private fun setupView() {
        binding.apply {
            tvHalaqahName.text = halaqahName
            tvHalaqahDateCreated.text = halaqahDateCreated
            btCreate.setOnClickListener {
                val name = etHalaqahName.text.toString()
                when {
                    name.isBlank() -> etHalaqahName.error = "Halaqah Name is Empty"
                    else -> {
                        viewModel.createHalaqah(userId, userToken, name)
                    }
                }
            }

            btRequest.setOnClickListener {
                val halaqah = etHalaqahId.text.toString()
                when {
                    halaqah.isBlank() -> etHalaqahId.error = "Halaqah ID is Empty"
                    else -> {
                        viewModel.requestHalaqah(userId, userToken, halaqah)
                    }
                }
            }
        }
    }

    private fun transToSplashScreen(){
        val intent = Intent(requireActivity(), MainActivity::class.java)
        intent.flags =
            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
    }

    private fun isHaveGroup(isHave: Boolean){
        binding.apply {
            if (isHave){
                halaqahListLayout.visibility = VISIBLE
                halaqahReqLayout.visibility = GONE
                infoLayout.visibility = VISIBLE
            }else{
                halaqahListLayout.visibility = GONE
                halaqahReqLayout.visibility = VISIBLE
                infoLayout.visibility = GONE
            }
        }

    }

    @SuppressLint("SetTextI18n")
    private fun getUserData(){
        userPreferences = UserPreferences(requireContext())
        prefModel = userPreferences.getUser()
        userId = prefModel.id
        userToken = prefModel.token
        userHalaqah = prefModel.halaqahId
        halaqahName = prefModel.halaqahName
        halaqahDateCreated = prefModel.halaqahDateCreated
        if (prefModel.halaqahId == ""){
            isHaveGroup(false)
        }else{
            isHaveGroup(true)
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun getCurrentDate(){
        val date = Calendar.getInstance().time
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        currentDate = formatter.format(date)
    }

    private fun setupViewModel(){
        viewModel = ViewModelProvider(this)[HalaqahViewModel::class.java]
        viewModel.message.observe(viewLifecycleOwner){
            showToast(it.toString())
        }
        viewModel.authorized.observe(viewLifecycleOwner){
            //todo: auto logout if not auth
        }
        viewModel.statusReqHalaqah.observe(viewLifecycleOwner){
            if (it == true){
                transToSplashScreen()
            }
        }
        viewModel.message.observe(viewLifecycleOwner){
            if (it != null) {
                showToast(it)
            }
        }
    }

    private fun setupHalaqahList(){
        val layoutManager = LinearLayoutManager(context)
        val itemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        binding.rvHalaqah.layoutManager = layoutManager
        binding.rvHalaqah.addItemDecoration(itemDecoration)
    }

    private fun setupMemberData(ListMember: List<HalaqahMemberItem?>?){
        val list = ArrayList<HalaqahMemberItem>()
        if (ListMember != null) {
            for (member in ListMember) {
                if (member != null) {
                    list.add(
                        HalaqahMemberItem(
                            member.no,member.image,member.half,member.level,member.yes,
                            member.halaqahLevel,member.halaqahId,member.token,member.halaqahDateJoin
                            ,member.permit,member.name,member.id,member.dateJoin
                        )
                    )
                    if (member.halaqahLevel == "high"){
                        binding.tvAdminHalaqah.text = member.name
                    }
                }
            }
        }
        binding.apply {
            rvHalaqah.adapter = MemberListAdapter(list)
            tvHalaqahMemberCount.text = (list.size).toString()
        }
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