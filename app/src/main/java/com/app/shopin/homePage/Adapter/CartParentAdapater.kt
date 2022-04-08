package com.app.shopin.homePage.Adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.shopin.Orders.viewmodels.TimeSlotListViewModel
import com.app.shopin.R
import com.app.shopin.Util.Utils
import com.app.shopin.customview.RegularTextView
import com.app.shopin.databinding.ItemCartParentBinding
import com.app.shopin.homePage.models.CartChildData
import com.app.shopin.homePage.models.CartParentData
import com.app.shopin.homePage.models.PlaceOrder
import com.app.shopin.homePage.viewmodels.PlaceOrderViewModel
import com.app.shopin.homePage.views.Activity.CartPageActivity
import com.app.shopin.utils.Constant
import com.app.shopin.utils.OpenDialogBox
import com.app.shopin.utils.Preference
import com.bigkoo.pickerview.MyOptionsPickerView
import com.bigkoo.pickerview.listener.OnItemSelectedListener
import com.google.android.material.bottomsheet.BottomSheetDialog


class CartParentAdapater(

    var allStoreDataValues: ArrayList<CartParentData>?,
    var norecrdfoundTV: RegularTextView,
    var recycleview: RecyclerView,
    var progressbarLL: LinearLayout,
    var ctx :Context
    ) : RecyclerView.Adapter<CartParentAdapater.MyViewHolder>()   {
    lateinit var binding: ItemCartParentBinding
    var overalltotal=0.0
    var total=0.0
    var order_type_val="Delivery"
    var order_type_key=Constant.DELIVERY
    val datelist=ArrayList<Any>()
    var timeslotlist=ArrayList<Any>()
    var selectedDate:String=""
    var SelectedTimeSlot:String=""
    var storelist=ArrayList<CartParentData>()
     var selectpos:Int=0

    lateinit var twoPicker: MyOptionsPickerView<Any>
    private lateinit var placeOrderViewModel: PlaceOrderViewModel
    private lateinit var timeSlotListViewModel: TimeSlotListViewModel

    companion object {
        @SuppressLint("StaticFieldLeak")
        var cartParentAdapater: CartParentAdapater? = null
        fun getInstance(): CartParentAdapater? {
            return cartParentAdapater
        }
    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        binding = DataBindingUtil.inflate(
            LayoutInflater.from(ctx),
            R.layout.item_cart_parent, parent, false
        )

        ctx = parent.context
        cartParentAdapater = this
        twoPicker= MyOptionsPickerView<Any>(ctx)
        placeOrderViewModel  = ViewModelProvider(ctx as CartPageActivity).get(PlaceOrderViewModel::class.java)
        timeSlotListViewModel  = ViewModelProvider(ctx as CartPageActivity).get(TimeSlotListViewModel::class.java)

        return MyViewHolder(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = allStoreDataValues!![position]


        order_type_val= data.cart_item?.get(0)?.order_type.toString()

        if (order_type_val.equals("Delivery"))
        {

            holder.binding.deliveryTV.setBackgroundResource(R.drawable.tabackgrnd)
            holder.binding.pickupTV.setBackgroundResource(0)
            order_type_key=Constant.DELIVERY

        }
        else
        {
            loadTimeSlotList(data.id.toString())
            holder.binding.pickupTV.setBackgroundResource(R.drawable.tabackgrnd)
            holder.binding.deliveryTV.setBackgroundResource(0)
            order_type_key=Constant.PICKUP

        }

        holder.binding.checkoutBTN.setOnClickListener {
            val data= allStoreDataValues!![position]
            selectpos=selectpos
            storelist= arrayListOf(data)

            if (order_type_val.equals("Delivery"))
            {
                progressbarLL.visibility=View.VISIBLE

                createJsonFormat( )
            }
            else
            {


                openBottomSheetDialog()


            }

        }
        holder.binding.shopnametv.text = data.name
        holder.binding.addressTV.text = data.address
        overalltotal=0.0
        try {
            val storeInventoryData: ArrayList<CartChildData> = data.cart_item!!
            for (i in 0..storeInventoryData.size-1)
            {
                overalltotal = storeInventoryData[i].price+overalltotal
            }
            total=overalltotal+total
            CartPageActivity.getInstance()?.runThread("showtotal", total.toString())
            holder.binding.totalpriceTV.text="Total \n$"+overalltotal.toString()
            val adapter = CartChildAdapater(storeInventoryData,holder,progressbarLL)
            val layoutManager = LinearLayoutManager(ctx)
            holder.binding.productRV.setHasFixedSize(false)
            holder.binding.productRV.layoutManager = layoutManager
            holder.binding.productRV.adapter = adapter
            adapter.notifyDataSetChanged()
        } catch (e: Exception) {
            Log.e("exce", e.message.toString())
        }

    }

    override fun getItemCount(): Int {
        return allStoreDataValues!!.size
    }


    class MyViewHolder(itemView: ItemCartParentBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val binding: ItemCartParentBinding

        init {
            binding = itemView
        }
    }

    fun runThread(pos: Int)
    {
        object : Thread() {
            @SuppressLint("NotifyDataSetChanged")
            override fun run() {
                try
                {
                    (ctx as Activity).runOnUiThread{


                            allStoreDataValues?.removeAt(pos)
                            notifyItemRemoved(pos)
                            if (allStoreDataValues!!.size==0)
                            {
                                recycleview.visibility=View.GONE
                                norecrdfoundTV.visibility=View.VISIBLE
                            }

                    }
                    sleep(300)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }.start()
    }



    private fun createJsonFormat() {
        var total_amount=overalltotal.toInt()
        storelist.get(selectpos).store_total_amount=total_amount
        Log.e("storelist",storelist.toString())

        val userInfo = PlaceOrder(
            order_type=order_type_key.toInt(),
            tax_percentage =0,
//            total_amount =overalltotal.toInt(),
            delivery_address = Preference.getInstance(ctx as CartPageActivity)?.getString(Constant.DELIVERY_ADDRESS_ID)?.toInt(),
            payment_method =2,
            pikcup_comment ="",
            tax_value =0,
            pickup_date =selectedDate,
            pickup_time_slot =SelectedTimeSlot,
            store =storelist
        )

//        placeOrderViewModel.getObserveData().removeObservers(ctx as CartPageActivity)
//        placeOrderViewModel.getObserveData()
//            .observe(ctx as CartPageActivity) {
//
//                if (it != null) {
//
//                    progressbarLL.visibility=View.GONE
//
//                    CartPageActivity.getInstance()?.runThread("cartdata","")
//
//
//                    OpenDialogBox.openDialog(ctx as CartPageActivity,"Success","Your order has been placed!","orderplace")
//
//
//                }
//                else
//                {
//                    progressbarLL.visibility=View.GONE
//
//                    Utils.showToast("error",ctx as CartPageActivity)
//
//                }
//            }
//        placeOrderViewModel.placeOrderResponse(ctx as CartPageActivity,userInfo)

    }


    fun setpicker()

    {

        for (i in 0 ..4)
        {
            selectedDate= Utils.getCalculatedDate("dd-MM-yyyy",0)!!
            val date= Utils.getCalculatedDate("dd-MM-yyyy",i)
            datelist.add(date!!)
        }
        twoPicker.setPicker(datelist, timeslotlist, true)
        twoPicker.setTitle("Time Slot")
        twoPicker.hideCancelButton()
        twoPicker.setSubmitButtonText("Done")

        twoPicker.btnSubmit.setOnClickListener {
            twoPicker.dismiss()

            progressbarLL.visibility=View.VISIBLE

            createJsonFormat()
        }
        twoPicker.setCyclic(false, false, false)
        twoPicker.setSelectOptions(0, 0)


        twoPicker.wheelOptions.setOption1SelectedListener(OnItemSelectedListener {
        index ->
         selectedDate=   datelist.get(index).toString()
        })

        twoPicker.wheelOptions.setOption2SelectedListener(OnItemSelectedListener {
        index ->
        SelectedTimeSlot=   timeslotlist.get(index).toString()
        })


    }



    fun loadTimeSlotList(id:String)
    {
        timeSlotListViewModel.getTimeSlotListObserver().removeObservers(ctx as CartPageActivity)
        timeSlotListViewModel.getTimeSlotListObserver()
            .observe(ctx as CartPageActivity) {
                if (it != null) {
                    val time_slots= it.data.time_slots!!
                    for (i in 0..time_slots.size-1)
                    {
                        val timeslot=time_slots.get(i).time_interval
                        val timestatus=time_slots.get(i).status
                        SelectedTimeSlot= time_slots.get(0).time_interval!!
                        if (timestatus.equals("1"))
                        {
                            timeslotlist.add(timeslot!!)

                        }
                    }
                    setpicker()
                }
            }

        timeSlotListViewModel.getTimeSlotList(ctx as CartPageActivity,id)
    }


    fun  openBottomSheetDialog()
    {
        val dialog = BottomSheetDialog(ctx as CartPageActivity)
        val view = (ctx as CartPageActivity).layoutInflater.inflate(R.layout.item_pickup_type, null)
        val curbsideIV = view.findViewById<ImageView>(R.id.curbsideIV)
        val instoreIV = view.findViewById<ImageView>(R.id.instoreIV)
        val instoreLL = view.findViewById<LinearLayout>(R.id.instoreLL)
        val curbsideLL = view.findViewById<LinearLayout>(R.id.curbsideLL)

        curbsideLL.setOnClickListener {
            Preference.getInstance(ctx as CartPageActivity)?.setString(Constant.INSTORE_OR_DELIVERY,Constant.PICKUP)
            curbsideIV.setImageResource(R.drawable.select)
            instoreIV.setImageResource(R.drawable.deselect)
            twoPicker.show()
            dialog.dismiss()

        }

        instoreLL.setOnClickListener {
            instoreIV.setImageResource(R.drawable.select)
            curbsideIV.setImageResource(R.drawable.deselect)
            twoPicker.show()
            dialog.dismiss()
        }
        dialog.setCancelable(false)
        dialog.setContentView(view)
        dialog.show()

    }
}
