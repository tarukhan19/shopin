package com.app.shopin.Orders.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.shopin.Orders.models.SelectedIssue
import com.app.shopin.Orders.views.Activity.IssueWithItemsActivity
import com.app.shopin.R
import com.app.shopin.UserAuth.view.EditProfileActivity
import com.app.shopin.customview.RegularTextView
import com.app.shopin.databinding.ItemIssuesBinding
import com.app.shopin.databinding.ItemIssuesDataBinding
import com.app.shopin.homePage.models.IssuesData

class IssueAdapter(
    var ctx: Context,
    var issuesArrayList: ArrayList<IssuesData>?,
    var selectissuestatusTV: TextView,
    var order_item: String
) : RecyclerView.Adapter<IssueAdapter.MyViewHolder>() {
    lateinit var binding: ItemIssuesBinding
    val selectedArrayList=ArrayList<SelectedIssue>()
    var order_issue_comment=""
    var selectedIssue=""

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(ctx),
            R.layout.item_issues, parent, false
        )
        return MyViewHolder(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = issuesArrayList!![position]

        holder.binding.issueTV.text=data.Value

        holder.binding.issueTV.setOnClickListener {
            selectedIssue=issuesArrayList!![position].Value
            selectissuestatusTV.text= selectedIssue
            createJsonFormat()

          //  IssueWithItemListAdapter.getInstance()?.runThread(issuesArrayList!![position].Value)
        }

    }

    override fun getItemCount(): Int {
        return issuesArrayList!!.size
    }


    class MyViewHolder(itemView: ItemIssuesBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val binding: ItemIssuesBinding

        init {
            binding = itemView
        }
    }


    private fun createJsonFormat()
    {

        val selectedIssue= SelectedIssue(
            order_item, order_issue_comment , selectedIssue
        )
        selectedArrayList.add(selectedIssue)
        IssueWithItemListAdapter.getInstance()?.runThread()

        IssueWithItemsActivity().getInstance()?.runThread(selectedArrayList,"api")

        Log.e("sendjsonobj",selectedArrayList.toString())

    }

}
