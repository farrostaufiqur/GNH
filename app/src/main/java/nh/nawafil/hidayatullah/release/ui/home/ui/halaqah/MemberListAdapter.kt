package nh.nawafil.hidayatullah.release.ui.home.ui.halaqah

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import nh.nawafil.hidayatullah.release.LinkWebServer.LINK_WEB_IMAGE
import nh.nawafil.hidayatullah.release.R
import nh.nawafil.hidayatullah.release.UserPreferences
import nh.nawafil.hidayatullah.release.data.network.response.HalaqahMemberItem
import nh.nawafil.hidayatullah.release.databinding.ItemHalaqahBinding
import nh.nawafil.hidayatullah.release.ui.nawafil_detail.NawafilDetailActivity

class MemberListAdapter(private val listMember: ArrayList <HalaqahMemberItem>) : RecyclerView.Adapter<MemberListAdapter.ListViewHolder>() {
    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemHalaqahBinding.bind(itemView)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_halaqah, viewGroup, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (no, image, half, _, yes, _, _, token, _, permit, name, id, dateJoin) = listMember[position]
        val link = "$LINK_WEB_IMAGE$image"
        holder.apply {
            val userPreferences = UserPreferences(itemView.context)
            userPreferences.setMemberHalaqah(id!!, token!!)
            binding.apply {
                tvItemHalaqahName.text = name
                tvItemHalaqahDateJoin.text = dateJoin
                tvItemHalaqahCountNo.text = no.toString()
                tvItemHalaqahCountPermit.text = permit.toString()
                tvItemHalaqahCountHalf.text = half.toString()
                tvItemHalaqahCountYes.text = yes.toString()
                Glide.with(itemView.context)
                    .load(link)
                    .into(ivItemHalaqahPhoto)
            }
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, NawafilDetailActivity::class.java)
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int = listMember.size
}