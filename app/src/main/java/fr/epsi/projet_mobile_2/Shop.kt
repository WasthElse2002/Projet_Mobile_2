package fr.epsi.projet_mobile_2

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.maps.GoogleMap
import android.Manifest
import android.content.Intent
import android.util.Log
import okhttp3.*
import okio.IOException
import org.json.JSONObject
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions



// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Shop.newInstance] factory method to
 * create an instance of this fragment.
 */
class Shop : Fragment() {

    private lateinit var googleMap: GoogleMap
    @SuppressLint("MissingPermission")
    val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                // Precise location access granted.
                googleMap.isMyLocationEnabled = true
            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                // Only approximate location access granted.
                googleMap.isMyLocationEnabled = true
            }
            else -> {
                // No location access granted.
            }
        }
    }

    private val callback = OnMapReadyCallback { googleMap ->
        val magasins = arrayListOf<Magasin>()
        val okHttpClient: OkHttpClient = OkHttpClient.Builder().build()
        val mRequestURL = "https://www.ugarit.online/epsi/stores.json"
        val request = Request.Builder()
            .url(mRequestURL)
            .get()
            .cacheControl(CacheControl.FORCE_NETWORK)
            .build()

        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                TODO("Not yet implemented")
            }

            override fun onResponse(call: Call, response: Response) {
                val data = response.body?.string()

                if (data != null) {
                    activity?.runOnUiThread {
                        val jsMagasins = JSONObject(data)
                        val jsArrayMagasin = jsMagasins.getJSONArray("stores")
                        Log.i("Json :", jsArrayMagasin.toString())
                        for (i in 0 until jsArrayMagasin.length()) {
                            val js = jsArrayMagasin.getJSONObject(i)
                            val magasinEmplacement = MarkerOptions()
                            val adresse = js.optString("address", "not found").toString() + "" +
                                    js.optString("zipcode", "not found").toString() + "" +
                                    js.optString("city", "not found").toString()
                            val magasinLatLng =
                                LatLng(js.optDouble("latitude", 0.0), js.optDouble("longitude", 0.0))
                            Log.i("position :", magasinLatLng.toString())

                            magasinEmplacement.title(js.optString("name"))
                            magasinEmplacement.position(magasinLatLng)
                            magasinEmplacement.snippet(adresse)
                            googleMap.addMarker(magasinEmplacement)

                            Log.i("address", adresse)
                            val magasin = Magasin(
                                js.optInt("storeId", -1),
                                js.optString("name", "not found"),
                                js.optString("description", "not found"),
                                js.optString("pictureStore", "not found"),
                                js.optString("address", "not found"),
                                js.optString("zipcode", "not found"),
                                js.optString("city", "not found"),
                                js.optDouble("longitude", -1.0),
                                js.optDouble("latitude", -1.0),
                            )
                            magasins.add(magasin)
                        }
                    }
                }
            }
        })
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(48.854885, 2.338646), 5f))

        googleMap.setOnInfoWindowClickListener { it ->

            val intent2 = Intent(context, ShopDetailsActivity::class.java)
            val marker = it
            magasins.forEach {
                val magasin = it
                if(marker.title?.uppercase().toString() == magasin.name.uppercase().toString()) {

                    intent2.putExtra("name", magasin.name)
                    intent2.putExtra("description", magasin.description)
                    intent2.putExtra("pictureStore", magasin.pictureStore)
                    intent2.putExtra("address", magasin.address)
                    intent2.putExtra("zipcode", magasin.zipcode)
                    intent2.putExtra("city", magasin.city)
                    startActivity(intent2)
                }
            }

        }

        this.googleMap = googleMap
        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }


    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        val files : Array<String> = context?.fileList() as Array<String>
        requireContext().deleteFile(files[0])
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shop, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Shop.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Shop().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}