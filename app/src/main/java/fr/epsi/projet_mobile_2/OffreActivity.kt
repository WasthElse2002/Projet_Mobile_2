package fr.epsi.projet_mobile_2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import okhttp3.*
import okio.IOException
import org.json.JSONObject
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class OffreActivity : BaseActivity() {

    lateinit var bottom_nav : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offre)
        setHeaderTitle(getString(R.string.offre))

        val url = "https://fr.freepik.com/vecteurs-libre/modele-banniere-vente-moderne-offre-speciale_5432257.htm#query=promotions&position=11&from_view=search&track=robertav1_2_sidr"
        val offres= arrayListOf<Offre>()

        val recyclerViewOffres = findViewById<RecyclerView>(R.id.recyclerviewOffre)
        recyclerViewOffres.layoutManager= GridLayoutManager(this, 2)
        val offresAdapter= OffreAdapter(offres)
        recyclerViewOffres.adapter=offresAdapter

        val okHttpClient: OkHttpClient = OkHttpClient.Builder().build()
        val mRequestURL="https://www.ugarit.online/epsi/offers.json"
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

                if(data!=null) {
                    val jsOffer = JSONObject(data)
                    val jsArrayOffer = jsOffer.getJSONArray("items")
                    for (i in 0 until jsArrayOffer.length()) {
                        val js = jsArrayOffer.getJSONObject(i)
                        val student = Offre(
                            js.optString("name", "not found"),
                            js.optString("description", "not found"),
                            js.optString("picture_url", url)
                        )
                        offres.add(student)
                        runOnUiThread(Runnable {
                            offresAdapter.notifyDataSetChanged()
                        })
                    }
                }
            }

        })
    }
}