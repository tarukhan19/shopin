package com.app.shopin.Orders.Adapter

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.shopin.Orders.models.IssueWithItemProdData
import com.app.shopin.R
import com.app.shopin.customview.RegularTextView
import com.app.shopin.databinding.ItemIssueListBinding
import com.app.shopin.databinding.ItemIssuesDataBinding
import com.app.shopin.homePage.models.IssuesData
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_order_history_detail_listing.*


class IssueWithItemListAdapter(
    var ctx: Context,
    var issueWithItemProdData: ArrayList<IssueWithItemProdData>,
    var issues_keys: ArrayList<IssuesData>) : RecyclerView.Adapter<IssueWithItemListAdapter.MyViewHolder>() {
    lateinit var binding: ItemIssuesDataBinding
    lateinit var  dialog:Dialog
    var order_item=""

    lateinit var issuelistBinding:ItemIssueListBinding
    companion object
    {
        @SuppressLint("StaticFieldLeak")
        var issueWithItemAdapter: IssueWithItemListAdapter? = null
        fun getInstance(): IssueWithItemListAdapter? {
            return issueWithItemAdapter
        }
    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(ctx),
            R.layout.item_issues_data, parent, false
        )
        issueWithItemAdapter =this


        return MyViewHolder(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = issueWithItemProdData[position]

        holder.binding.prodnameTV.text=data.inventory_name
        holder.binding.prodamountTV.text="$"+data.price+"/pc"

        if (data.quantity.equals("1"))
        {
            holder.binding.quantityTV.text=data.quantity +" Item"
        }
        else
        {
            holder.binding.quantityTV.text=data.quantity +" Items"
        }

        if (data.item_issue)
        {
            holder.binding.selectissueLL.visibility=View.GONE
            holder.binding.issueTVLL.visibility=View.VISIBLE
            holder.binding.issuestatusTV.text=data.issue_name

        }
        else
        {
            holder.binding.selectissueLL.visibility=View.VISIBLE
            holder.binding.issueTVLL.visibility=View.GONE

        }
        holder.binding.selectissueLL.setOnClickListener{
            order_item= data.id!!
            if (!data.item_issue)
            {
                try {
                    openIssueDialog(holder.binding.selectissuestatusTV)
                }
                catch (e:Exception)
                {
                    Log.e("Exception",e.message.toString())
                }

            }
        }



    }





    override fun getItemCount(): Int {
        return issueWithItemProdData.size
    }


    class MyViewHolder(itemView: ItemIssuesDataBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val binding: ItemIssuesDataBinding

        init {
            binding = itemView
        }
    }




    fun openIssueDialog(selectissuestatusTV: RegularTextView) {
         dialog = BottomSheetDialog(ctx)
        issuelistBinding = DataBindingUtil.inflate(
            LayoutInflater.from(ctx),
            R.layout.item_issue_list,
            null,
            false
        )
        dialog.setContentView(issuelistBinding.root)
        dialog.show()
        val adapter = IssueAdapter(ctx,issues_keys,selectissuestatusTV,order_item)
        issuelistBinding.issueRV.layoutManager = LinearLayoutManager(ctx, RecyclerView.VERTICAL, false)
        issuelistBinding.issueRV.adapter = adapter
        issuelistBinding.backbtn.setOnClickListener { dialog.dismiss() }


    }

    fun runThread()
    {
        object : Thread()
        {
            @SuppressLint("NotifyDataSetChanged")
            override fun run() {
                try
                {
                    dialog.dismiss()
                }
                catch (e: InterruptedException)
                {
                    e.printStackTrace()
                }
            }
        }.start()
    }



}
