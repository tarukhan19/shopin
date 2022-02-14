package com.app.shopin.homePage.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.shopin.R
import com.app.shopin.homePage.models.HomeNewStoreListDTO
import kotlinx.android.synthetic.main.item_new_stores.view.*

class HomeNewStoreAdapter (val homeNewStoreListDTO: ArrayList<HomeNewStoreListDTO>) : RecyclerView.Adapter<HomeNewStoreAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeNewStoreAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_new_stores, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: HomeNewStoreAdapter.ViewHolder, position: Int) {
        holder.bindItems(homeNewStoreListDTO[position])
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return homeNewStoreListDTO.size
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(user: HomeNewStoreListDTO) {
            itemView.newstorenameTV.text=user.name
            itemView.newstoreaddressTV.text=user.address
            itemView.newstoreIV.setImageResource(user.image!!)

        }
    }

}