package com.app.shopin.homePage.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.shopin.R
import com.app.shopin.homePage.models.HomeBannerListDTO
import kotlinx.android.synthetic.main.item_banner_items.view.*

class HomeBannerAdapter (val homeBannerListDTO: ArrayList<HomeBannerListDTO>) : RecyclerView.Adapter<HomeBannerAdapter.ViewHolder>() {

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeBannerAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_banner_items, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: HomeBannerAdapter.ViewHolder, position: Int) {
        holder.bindItems(homeBannerListDTO[position])
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return homeBannerListDTO.size
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(user: HomeBannerListDTO) {
            itemView.textviewTV.text=user.name
            itemView.imageview.setImageResource(user.image!!)

        }
    }

}