package com.app.shopin.homePage.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.shopin.R
import com.app.shopin.homePage.models.FeaturedItemListData
import kotlinx.android.synthetic.main.item_product.view.*

class FeaturedItemAdapter (val featuredItemListData: ArrayList<FeaturedItemListData>) : RecyclerView.Adapter<FeaturedItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(featuredItemListData[position])
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return featuredItemListData.size
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(data: FeaturedItemListData) {
            itemView.prodnameTV.text=data.prodname
            itemView.produnitTV.text=data.produnit
            itemView.prodpriceTV.text=data.prodprice
            itemView.prodIV.setImageResource(data.image!!)

        }
    }

}