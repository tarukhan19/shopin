package com.app.shopin.homePage.views.Activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.app.shopin.R
import com.app.shopin.Util.Utils
import com.app.shopin.autocompleteLib.adapter.PlacesAutoCompleteAdapter
import com.app.shopin.autocompleteLib.listener.OnPlacesDetailsListener
import com.app.shopin.autocompleteLib.model.Place
import com.app.shopin.autocompleteLib.model.PlaceAPI
import com.app.shopin.autocompleteLib.model.PlaceDetails
import com.app.shopin.databinding.ActivityDeliveryAddressUpdateBinding
import com.app.shopin.homePage.viewmodels.DeliveryAddressAddViewModel
import com.app.shopin.homePage.viewmodels.DeliveryAddressUpdateViewModel
import com.app.shopin.homePage.viewmodels.LoadSingleAddressViewModel
import com.app.shopin.homePage.viewmodels.RemoveAddressViewModel
import com.app.shopin.utils.LocationMethods
import com.app.shopin.utils.OpenDialogBox
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import kotlinx.android.synthetic.main.activity_delivery_address_add.*
import kotlinx.android.synthetic.main.activity_delivery_address_update.*
import kotlinx.android.synthetic.main.activity_delivery_address_update.addAddressBTN
import kotlinx.android.synthetic.main.activity_delivery_address_update.addressET
import kotlinx.android.synthetic.main.activity_delivery_address_update.addressTIL
import kotlinx.android.synthetic.main.activity_delivery_address_update.addresstypeLL
import kotlinx.android.synthetic.main.activity_delivery_address_update.addresstypeTV
import kotlinx.android.synthetic.main.activity_delivery_address_update.aptfloorcompET
import kotlinx.android.synthetic.main.activity_delivery_address_update.deliveryinstructionET
import kotlinx.android.synthetic.main.activity_delivery_address_update.isdefaultswitch
import kotlinx.android.synthetic.main.activity_delivery_address_update.nameET
import kotlinx.android.synthetic.main.activity_delivery_address_update.nameTIL
import kotlinx.android.synthetic.main.activity_delivery_address_update.progressbarLL
import kotlinx.android.synthetic.main.activity_delivery_address_update.toolbar
import kotlinx.android.synthetic.main.toolbar.view.*
import kotlinx.android.synthetic.main.toolbar.view.back_LL
import kotlinx.android.synthetic.main.toolbar.view.titleTV
import kotlinx.android.synthetic.main.toolbar_updateaddress.view.*


class DeliveryAddressUpdateActivity : AppCompatActivity(),View.OnClickListener, AdapterView.OnItemClickListener, OnMapReadyCallback {
    lateinit var  binding:ActivityDeliveryAddressUpdateBinding
    private lateinit var loadSingleAddressViewModel: LoadSingleAddressViewModel

    private lateinit var removeAddressViewModel: RemoveAddressViewModel

    var id:String=""
    lateinit var addresstypeArrayAdapter: ArrayAdapter<String>
    lateinit var addresstypebuilder: AlertDialog.Builder
    lateinit var addresstypeArrayList: ArrayList<String>
    lateinit var addresstypeLV: ListView
    lateinit var addresstypedialog: AlertDialog
    private val addresstypeList = arrayOf("Home", "Office")
    private var addresstypeS: String = "Home"
    private var addresstype: String = "1"
    private var isDefault: String = "0"

    lateinit var name: String
    lateinit var location: String
    lateinit var floor: String
    lateinit var deliveryinstruction: String
    private var  lat=0.0
    private var  lng=0.0
    private lateinit var deliveryAddressUpdateViewModel: DeliveryAddressUpdateViewModel

    lateinit var mMap: GoogleMap

    val placesApi = PlaceAPI.Builder()
        .apiKey("AIzaSyAdoq9IbV9WMAc_wAyEpNZslrPUmJ_RCLg")
        .build(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_delivery_address_update)
        initialize()
    }
    companion object {
        @SuppressLint("StaticFieldLeak")
        var deliveryAddressUpdateActivity: DeliveryAddressUpdateActivity? = null
        fun getInstance(): DeliveryAddressUpdateActivity? {
            return deliveryAddressUpdateActivity
        }
    }

    private fun initialize() {
        deliveryAddressUpdateActivity=this
        toolbar.titleTV.text=getString(R.string.delivAddresUpdate)
        toolbar.back_LL.setOnClickListener(this)
        toolbar.removeLL.setOnClickListener(this)
        id= intent.getStringExtra("id")!!

        loadSingleAddressViewModel= ViewModelProvider(this).get(LoadSingleAddressViewModel::class.java)
        deliveryAddressUpdateViewModel = ViewModelProvider(this).get(DeliveryAddressUpdateViewModel::class.java)
        removeAddressViewModel= ViewModelProvider(this).get(RemoveAddressViewModel::class.java)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        if (!Places.isInitialized()) {
            Places.initialize(
            applicationContext,
            getString(R.string.api_key)
        )}

        addresstypebuilder = AlertDialog.Builder(this)
        addresstypeLV = ListView(this)
        addresstypeArrayList = ArrayList<String>()
        addresstypeArrayList.addAll(addresstypeList)
        addresstypeArrayAdapter =
            ArrayAdapter<String>(this, R.layout.item_spinner, R.id.text, addresstypeArrayList)
        addresstypeLV.setAdapter(addresstypeArrayAdapter)
        addresstypebuilder.setView(addresstypeLV)
        addresstypedialog = addresstypebuilder.create()

        addresstypeTV.setText(addresstypeS)

        isdefaultswitch.setOnCheckedChangeListener { switch, isChecked ->
            if (isChecked) isDefault = "1"
            else isDefault = "0" }

        addressET.setAdapter(PlacesAutoCompleteAdapter(this, placesApi))
        addressET.onItemClickListener =
            AdapterView.OnItemClickListener { parent, _, position, _ ->
                val place = parent.getItemAtPosition(position) as Place
                addressET.setText(place.description)
                aptfloorcompET.setText(" ")
                getPlaceDetails(place.id)
            }

        addresstypeLV.setOnItemClickListener(this)
        addresstypeLL.setOnClickListener(this)
        addAddressBTN.setOnClickListener(this)
        loadAddress()
    }

    private fun getPlaceDetails(placeId: String) {
        placesApi.fetchPlaceDetails(placeId, object :
            OnPlacesDetailsListener {
            override fun onError(errorMessage: String) {
                Toast.makeText(this@DeliveryAddressUpdateActivity, errorMessage, Toast.LENGTH_SHORT).show()
            }

            override fun onPlaceDetailsFetched(placeDetails: PlaceDetails) {
                runOnUiThread {

                    lat = placeDetails.lat
                    lng = placeDetails.lng

                }
            }
        })
    }
    fun loadAddress()
    {
        progressbarLL.visibility = View.VISIBLE
        loadSingleAddressViewModel.getObserve().observe(this) {
            if (it?.status == true && it.status_code == 200) {

                progressbarLL.visibility = View.GONE
                name= it.data?.name!!
                location= it.data.address!!
                floor= it.data.apartment_building!!
                addresstypeS= it.data.address_type!!
                deliveryinstruction=it.data.delivery_instruction
                lat= it.data.lattitude
                lng= it.data.longitude
                val isdef=it.data.is_default
                if (isdef!!)
                {
                    isDefault = "1"
                    isdefaultswitch.isChecked=true
                }
                else
                {
                    isDefault = "0"
                    isdefaultswitch.isChecked=false

                }

                Log.e("lat",lat.toString())
                Log.e("lng",lng.toString())

                if (addresstypeS.equals("Home")) {
                    addresstype = "1"
                } else {
                    addresstype = "2"
                }

                nameET.setText(name)
                addressET.setText(location)
                aptfloorcompET.setText(floor)
                addresstypeTV.setText(addresstypeS)
                deliveryinstructionET.setText(deliveryinstruction)
                setMap()


            } else {
                progressbarLL.visibility = View.GONE
                Utils.showToast("Something Went wrong", this)
            }
        }
        loadSingleAddressViewModel.makeLoadAddressApiCall(this,id)
    }


    override fun onMapReady(googleMap: GoogleMap)
    {
        mMap = googleMap
        try {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            )
            {

                return
            }

            mMap.setMyLocationEnabled(true);

            // Enable / Disable zooming controls
            googleMap.getUiSettings().setZoomControlsEnabled(true);

            // Enable / Disable my location button
            googleMap.getUiSettings().setMyLocationButtonEnabled(true);

            // Enable / Disable Compass icon
            googleMap.getUiSettings().setCompassEnabled(true);

            // Enable / Disable Rotate gesture`enter code here`
            googleMap.getUiSettings().setRotateGesturesEnabled(true);

            // Enable / Disable zooming functionality
            googleMap.getUiSettings().setZoomGesturesEnabled(true);

            setMap()


        }
        catch (e: Exception)
        {
        }
    }

    fun setMap()
    {
        val latlng = LatLng(lat, lng)

        val marker = mMap.addMarker(
            MarkerOptions()
                .position(latlng)
                .title(location)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
        )

        mMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(
                    lat,
                    lng
                ), 15.0f,
            )
        )
        mMap.setOnCameraMoveListener {
            val midLatLng = mMap.cameraPosition.target
            if (marker != null) {
                marker.setPosition(midLatLng)
                val nowLocation = marker.getPosition()
                lat=nowLocation.latitude
                lng=nowLocation.longitude
                val loc=LocationMethods.getCompleteAddressString(lat,lng,this)
                val strReturnedAddress = StringBuilder("")
                if (loc != null)
                {
                    for (i in 0..loc.maxAddressLineIndex)
                    {
                        location=loc.getAddressLine(i)
                        floor=loc.featureName
                        strReturnedAddress.append(location).append("\n")
                    }
                }

                runOnUiThread{
                    addressET.setText(location)
                    aptfloorcompET.setText(floor)

                }

            }
        }

    }

    override fun onClick(v: View?) {
        when (v?.id)
        {
            R.id.addresstypeLL -> {
                Utils.hideKeyBord(this)
                addresstypedialog.setView(addresstypeLV)
                addresstypedialog.show()
            }
            R.id.addAddressBTN -> {
                Utils.hideKeyBord(this)
                submit()
            }
            R.id.back_LL -> {
                finish()
            }
            R.id.removeLL -> {
                removeAddress()
            }


        }
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val vg = view as ViewGroup
        val txt = vg.findViewById<TextView>(R.id.text)
        addresstypeTV.setText(txt.text.toString())
        addresstypeS = txt.text.toString()
        if (addresstypeS.equals("Home")) {
            addresstype = "1"
        } else {
            addresstype = "2"
        }
        addresstypedialog.dismiss()
    }

    private fun submit()
    {
        name = nameET.text.toString()
        floor= aptfloorcompET.text.toString()
        deliveryinstruction=deliveryinstructionET.text.toString()
        location=addressET.text.toString()

        if (name.isEmpty())
        {
            Utils.validateEditText("name", nameET, nameTIL, this)
        }
        else if (location.isEmpty())
        {
            Utils.validateEditText("address", addressET, addressTIL, this)
        }
        else
        {
            Utils.hideKeyBord(this)
            updateAddress()
        }
    }

    @SuppressLint("ResourceType")
    fun updateAddress()
    {
        progressbarLL.visibility = View.VISIBLE
        deliveryAddressUpdateViewModel.getObserveData().removeObservers(this)

        deliveryAddressUpdateViewModel.getObserveData().observe(this) {

            if (it?.status == true && it.status_code == 200) {
                progressbarLL.visibility = View.GONE

                OpenDialogBox.openDialog(this,"Success",getString(R.string.updateadresssuc),"updateaddress")
            } else {
                progressbarLL.visibility = View.GONE
            }
        }
        Log.e("latlng",lat.toString()+" "+lng.toString())

        deliveryAddressUpdateViewModel.getDeliveryAddressUpdateResp(this,id,name,location,addresstype,
            deliveryinstruction,floor,isDefault,lat,lng)
    }


    @SuppressLint("ResourceType")
    fun removeAddress()
    {
        progressbarLL.visibility = View.VISIBLE
        removeAddressViewModel.getObserveData().removeObservers(this)

        removeAddressViewModel.getObserveData().observe(this) {

            if (it?.status == true && it.status_code == 200) {
                progressbarLL.visibility = View.GONE

                OpenDialogBox.openDialog(this,"Success",getString(R.string.removed),"updateaddress")
            } else {
                progressbarLL.visibility = View.GONE
            }
        }
        Log.e("latlng",lat.toString()+" "+lng.toString())

        removeAddressViewModel.removeAddressResponse(this,id)
    }

    fun runThread()
    {
        object : Thread()
        {
            @SuppressLint("NotifyDataSetChanged")
            override fun run() {
                try
                {
                    finish()
                }
                catch (e: InterruptedException)
                {
                    e.printStackTrace()
                }
            }
        }.start()
    }
}