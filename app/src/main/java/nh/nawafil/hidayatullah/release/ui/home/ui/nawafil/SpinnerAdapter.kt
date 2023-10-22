package nh.nawafil.hidayatullah.release.ui.home.ui.nawafil

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import nh.nawafil.hidayatullah.release.R

class SpinnerAdapter (context: Context, private var images: IntArray) :
    BaseAdapter() {
    private var inflter: LayoutInflater

    init {
        inflter = LayoutInflater.from(context)
    }

    override fun getCount(): Int {
        return images.size
    }

    override fun getItem(i: Int): Any? {
        return null
    }

    override fun getItemId(i: Int): Long {
        return 0
    }

    @SuppressLint("ViewHolder", "InflateParams")
    override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {

        val itemLayout = inflter.inflate(R.layout.item_spinner,null)
        val icon = itemLayout.findViewById<View>(R.id.ivSpinner) as ImageView?
        icon!!.setImageResource(images[i])
        return itemLayout
    }
}