package nh.nawafil.hidayatullah.release.ui.home.ui.nawafil

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import nh.nawafil.hidayatullah.release.R
import nh.nawafil.hidayatullah.release.UserPreferences
import nh.nawafil.hidayatullah.release.databinding.FragmentNawafilBinding
import nh.nawafil.hidayatullah.release.preferences.shared.PreferencesModel
import java.text.SimpleDateFormat
import java.util.*

class NawafilFragment : Fragment(), OnItemSelectedListener{
    private var _binding: FragmentNawafilBinding? = null
    private val binding get() = _binding!!

    private val paramsPost = HashMap<String?, Any?>()
    private val paramsGet = HashMap<String?, Any?>()
    private var spImg = intArrayOf( R.drawable.tidak, R.drawable.izin, R.drawable.setengah_iya, R.drawable.iya)

    private var userId: String? = null
    private var userToken: String? = null
    private var currentDate: String? = null

    private lateinit var viewModel: NawafilViewModel
    private lateinit var userPreferences: UserPreferences
    private lateinit var prefModel: PreferencesModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNawafilBinding.inflate(inflater, container, false)
        setupViewModel()
        getUserData()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupExpandListView()
        setupListView()
        setupSpinnerValue()
    }

    @SuppressLint("SetTextI18n")
    private fun setupView(){
        binding.apply {
            paramsPost["date"] = currentDate
            paramsGet["date"] = currentDate
            tvNawafilTitle.text = "Nawafil Harian : $currentDate"

            btSubmit.setOnClickListener {
                Log.d(TAG,"Nawafil : $paramsPost")
                viewModel.postNawafil(paramsPost)
            }
        }
    }

    private fun setupViewModel(){
        viewModel = ViewModelProvider(requireActivity())[NawafilViewModel::class.java]
        viewModel.message.observe(viewLifecycleOwner){
            if (it != null) {
                showToast(it)
            }
        }
    }

    private fun setupSpinnerValue(){
        viewModel.getNawafil(paramsGet)
        viewModel.getStatus.observe(viewLifecycleOwner){
            if (it == true){
                val nawafil = viewModel.nawafil.value
                if (nawafil != null) {
                    setSpinnerSelect(nawafil.tahajjud!!.toInt(), nawafil.witir!!.toInt(),
                        nawafil.qoSubuh!!.toInt(), nawafil.qoDzuhur!!.toInt(), nawafil.baDzuhur!!.toInt(),
                        nawafil.baMaghrib!!.toInt(), nawafil.baIsya!!.toInt(), nawafil.dzikirPagi!!.toInt(),
                        nawafil.dzikirSore!!.toInt(), nawafil.dzikirMalam!!.toInt(), nawafil.quran!!.toInt(),
                        nawafil.infaq!!.toInt(), nawafil.dhuha!!.toInt(), nawafil.dakwah!!.toInt())

                }else{
                    setSpinnerSelect()
                }
            }
        }
    }

    private fun setSpinnerSelect(
        tahajjud: Int = 0, witir: Int = 0, qoSubuh: Int = 0,
        qoDzuhur: Int = 0, baDzuhur: Int = 0, baMaghrib: Int = 0,
        baIsya: Int = 0, morningDzikir: Int = 0, eveningDzikir: Int = 0,
        nightDzikir: Int = 0, tilawah: Int = 0, infaq: Int = 0,
        dhuha: Int = 0, dakwah: Int = 0,
    ){
        binding.apply {
            spTahajjud.setSelection(tahajjud)
            spWitir.setSelection(witir)
            spQoSubuh.setSelection(qoSubuh)
            spQoDzuhur.setSelection(qoDzuhur)
            spBaDzuhur.setSelection(baDzuhur)
            spBaMaghrib.setSelection(baMaghrib)
            spBaIsya.setSelection(baIsya)
            spMorningDzikir.setSelection(morningDzikir)
            spEveningDzikir.setSelection(eveningDzikir)
            spNightDzikir.setSelection(nightDzikir)
            spTilawah.setSelection(tilawah)
            spInfaq.setSelection(infaq)
            spDhuha.setSelection(dhuha)
            spDakwah.setSelection(dakwah)
        }
    }

    private fun setupListView() {
        val customAdapter = SpinnerAdapter(requireContext(), spImg)
        binding.apply {
            spTahajjud.adapter = customAdapter
            spTahajjud.onItemSelectedListener = this@NawafilFragment

            spWitir.adapter = customAdapter
            spWitir.onItemSelectedListener = this@NawafilFragment

            spQoSubuh.adapter = customAdapter
            spQoSubuh.onItemSelectedListener = this@NawafilFragment

            spQoDzuhur.adapter = customAdapter
            spQoDzuhur.onItemSelectedListener = this@NawafilFragment

            spBaDzuhur.adapter = customAdapter
            spBaDzuhur.onItemSelectedListener = this@NawafilFragment

            spBaMaghrib.adapter = customAdapter
            spBaMaghrib.onItemSelectedListener = this@NawafilFragment

            spBaIsya.adapter = customAdapter
            spBaIsya.onItemSelectedListener = this@NawafilFragment

            spMorningDzikir.adapter = customAdapter
            spMorningDzikir.onItemSelectedListener = this@NawafilFragment

            spEveningDzikir.adapter = customAdapter
            spEveningDzikir.onItemSelectedListener = this@NawafilFragment

            spNightDzikir.adapter = customAdapter
            spNightDzikir.onItemSelectedListener = this@NawafilFragment

            spTilawah.adapter = customAdapter
            spTilawah.onItemSelectedListener = this@NawafilFragment

            spInfaq.adapter = customAdapter
            spInfaq.onItemSelectedListener = this@NawafilFragment

            spDhuha.adapter = customAdapter
            spDhuha.onItemSelectedListener =this@NawafilFragment

            spDakwah.adapter = customAdapter
            spDakwah.onItemSelectedListener = this@NawafilFragment
        }
    }

    private fun setupExpandListView(){
        binding.apply {
            titleNawafil.setOnClickListener {
                if (infoNawafil.visibility == View.VISIBLE){
                    TransitionManager.beginDelayedTransition(baseCardView, AutoTransition())
                    line1.visibility = View.GONE
                    infoNawafil.visibility = View.GONE
                } else {
                    TransitionManager.beginDelayedTransition(baseCardView, AutoTransition())
                    infoNawafil.visibility = View.VISIBLE
                    btSubmit.visibility = View.VISIBLE
                    line1.visibility = View.VISIBLE
                }
            }
            tahajjudTitle.setOnClickListener {
                if (tahajjudList.visibility == View.VISIBLE){
                    TransitionManager.beginDelayedTransition(baseCardView, AutoTransition())
                    tahajjudList.visibility = View.GONE
                    line3.visibility = View.VISIBLE
                    arrowTahajjud.animate()
                        .rotation(0f)
                        .setDuration(200)
                        .start()
                }else{
                    TransitionManager.beginDelayedTransition(baseCardView, AutoTransition())
                    tahajjudList.visibility = View.VISIBLE
                    line3.visibility = View.GONE
                    infoNawafil.visibility = View.VISIBLE
                    btSubmit.visibility = View.VISIBLE
                    line1.visibility = View.VISIBLE
                    arrowTahajjud.animate()
                        .rotation(90f)
                        .setDuration(200)
                        .start()
                }
            }
            sunnahTitle.setOnClickListener {
                if (sunnahList.visibility == View.VISIBLE){
                    TransitionManager.beginDelayedTransition(baseCardView, AutoTransition())
                    sunnahList.visibility = View.GONE
                    line4.visibility = View.VISIBLE
                    arrowSunnah.animate()
                        .rotation(0f)
                        .setDuration(200)
                        .start()
                }else{
                    TransitionManager.beginDelayedTransition(baseCardView, AutoTransition())
                    sunnahList.visibility = View.VISIBLE
                    line4.visibility = View.GONE
                    btSubmit.visibility = View.VISIBLE
                    infoNawafil.visibility = View.VISIBLE
                    line1.visibility = View.VISIBLE
                    arrowSunnah.animate()
                        .rotation(90f)
                        .setDuration(200)
                        .start()
                }
            }
            dzikirWiridTitle.setOnClickListener {
                if (dzikirWiridList.visibility == View.VISIBLE){
                    TransitionManager.beginDelayedTransition(baseCardView, AutoTransition())
                    dzikirWiridList.visibility = View.GONE
                    line5.visibility = View.VISIBLE
                    arrowDzikirWirid.animate()
                        .rotation(0f)
                        .setDuration(200)
                        .start()
                }else{
                    TransitionManager.beginDelayedTransition(baseCardView, AutoTransition())
                    dzikirWiridList.visibility = View.VISIBLE
                    line5.visibility = View.GONE
                    infoNawafil.visibility = View.VISIBLE
                    line1.visibility = View.VISIBLE
                    btSubmit.visibility = View.VISIBLE
                    arrowDzikirWirid.animate()
                        .rotation(90f)
                        .setDuration(200)
                        .start()
                }
            }
            otherTitle.setOnClickListener {
                if (otherList.visibility == View.VISIBLE){
                    TransitionManager.beginDelayedTransition(baseCardView, AutoTransition())
                    otherList.visibility = View.GONE
                    arrowOther.animate()
                        .rotation(0f)
                        .setDuration(200)
                        .start()
                }else{
                    TransitionManager.beginDelayedTransition(baseCardView, AutoTransition())
                    otherList.visibility = View.VISIBLE
                    infoNawafil.visibility = View.VISIBLE
                    btSubmit.visibility = View.VISIBLE
                    line1.visibility = View.VISIBLE
                    arrowOther.animate()
                        .rotation(90f)
                        .setDuration(200)
                        .start()
                }
            }
        }
    }

    private fun getUserData(){
        userPreferences = UserPreferences(requireContext())
        prefModel = userPreferences.getUser()
        userId = prefModel.id
        userToken = prefModel.token

        val time = Calendar.getInstance().time
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ROOT)
        currentDate = formatter.format(time)
    }

    private fun showToast(text: String) {
        Toast.makeText(requireContext(),
            text, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        const val TAG = "NawafilFragment"
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (view?.id){
            R.id.spTahajjud -> paramsPost["tahajjud"] = position
            R.id.spWitir -> paramsPost["witir"] = position
            R.id.spQoSubuh -> paramsPost["qo_subuh"] = position
            R.id.spQoDzuhur -> paramsPost["qo_dzuhur"] = position
            R.id.spBaDzuhur -> paramsPost["ba_dzuhur"] = position
            R.id.spBaMaghrib -> paramsPost["ba_maghrib"] = position
            R.id.spBaIsya -> paramsPost["ba_isya"] = position
            R.id.spMorningDzikir -> paramsPost["dzikir_pagi"] = position
            R.id.spEveningDzikir -> paramsPost["dzikir_sore"] = position
            R.id.spNightDzikir -> paramsPost["dzikir_malam"] = position
            R.id.spTilawah -> paramsPost["quran"] = position
            R.id.spInfaq -> paramsPost["infaq"] = position
            R.id.spDhuha -> paramsPost["dhuha"] = position
            R.id.spDakwah -> paramsPost["dakwah"] = position
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {}
}