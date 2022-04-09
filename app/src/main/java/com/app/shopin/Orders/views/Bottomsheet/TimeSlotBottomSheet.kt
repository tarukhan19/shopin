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
import com.bigkoo.pickerview.MyOptionsPickerView
import com.bigkoo.pickerview.view.MyWheelOptions
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.item_pickup_slots.*


class TimeSlotBottomSheet : BottomSheetDialogFragment(), View.OnClickListener {
    lateinit var binding: ItemPickupSlotsBinding
    val datelist=ArrayList<String>()
    lateinit var twoPicker: MyOptionsPickerView<Any>

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


//        singlePicker = MyOptionsPickerView<Any>(requireActivity())
//        val items = ArrayList<Any>()
//        items.add("A")
//        items.add("B")
//        items.add("C")
//        items.add("D")
//        items.add("E")
//        singlePicker.setPicker(items)
//        singlePicker.setTitle("Single Picker")
//        singlePicker.setCyclic(false)
//        singlePicker.setSelectOptions(0)
//        singlePicker.setOnoptionsSelectListener(MyOptionsPickerView.OnOptionsSelectListener { options1, option2, options3 -> //  singleTVOptions.setText("Single Picker " + items.get(options1));
//            Toast.makeText(requireActivity(), "" + items[options1], Toast.LENGTH_SHORT).show()
//        })
//        singlePicker.show();


        //Two Options PickerView
        twoPicker = MyOptionsPickerView<Any>(requireActivity())
        val twoItemsOptions1 = java.util.ArrayList<Any>()
        twoItemsOptions1.add("AA")
        twoItemsOptions1.add("BB")
        twoItemsOptions1.add("CC")
        twoItemsOptions1.add("DD")
        twoItemsOptions1.add("EE")
        val twoItemsOptions2 = java.util.ArrayList<Any>()
        twoItemsOptions2.add("00")
        twoItemsOptions2.add("11")
        twoItemsOptions2.add("22")
        twoItemsOptions2.add("33")
        twoItemsOptions2.add("44")

        twoPicker.setPicker(twoItemsOptions1, twoItemsOptions2, false)
        twoPicker.setTitle("two Picker")
        twoPicker.setSubmitButtonText("done")
        twoPicker.btnSubmit.setOnClickListener {
        }
        twoPicker.setCyclic(false, false, false)
        twoPicker.setSelectOptions(0, 0)
//        twoPicker.setOnoptionsSelectListener(MyOptionsPickerView.OnOptionsSelectListener { options1, option2, options3 -> // twoTVOptions.setText("Two Options " + twoItemsOptions1.get(options1) + " " + twoItemsOptions2.get(option2));
//
//        })


    }

    override fun onClick(v: View?) {
        when (v?.id) {

            R.id.backbtn -> {
                //dismiss()
                twoPicker.show()
            }


        }
    }




}