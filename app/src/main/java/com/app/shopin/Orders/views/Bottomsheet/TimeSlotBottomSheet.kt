package com.app.shopin.Orders.views.Bottomsheet


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.app.shopin.R
import com.app.shopin.Util.Utils
import com.app.shopin.databinding.ItemPickupSlotsBinding

import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.item_pickup_slots.*


class TimeSlotBottomSheet : BottomSheetDialogFragment(), View.OnClickListener {
    lateinit var binding: ItemPickupSlotsBinding
    val datelist=ArrayList<String>()
  //  lateinit var twoPicker: MyOptionsPickerView<Any>

    companion object {
        const val TAG = "ActionBottomDialog"

        lateinit var timeSlotBottomSheet: TimeSlotBottomSheet
        fun newInstance(): TimeSlotBottomSheet {
            return TimeSlotBottomSheet()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.item_pickup_slots, container,
            false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    fun initialize() {
        timeSlotBottomSheet = this
        backbtn.setOnClickListener(this)

        for (i in 0 ..4)
        {
            val date= Utils.getCalculatedDate("dd-MM-yyyy",i)
            datelist.add(date!!)
        }




    }

    override fun onClick(v: View?) {
        when (v?.id) {

            R.id.backbtn -> {
                //dismiss()
            }


        }
    }




}