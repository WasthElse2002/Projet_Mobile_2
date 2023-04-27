package fr.epsi.projet_mobile_2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import okhttp3.*
import okio.IOException
import org.json.JSONObject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Offer.newInstance] factory method to
 * create an instance of this fragment.
 */
class Offer : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_offer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val url = "https://fr.freepik.com/vecteurs-libre/modele-banniere-vente-moderne-offre-speciale_5432257.htm#query=promotions&position=11&from_view=search&track=robertav1_2_sidr"
        val offres= arrayListOf<Offre>()

        val recyclerViewOffres = view.findViewById<RecyclerView>(R.id.recyclerviewOffre)
        recyclerViewOffres.layoutManager= GridLayoutManager(this.context, 2)
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
                        activity?.runOnUiThread(Runnable {
                            offresAdapter.notifyDataSetChanged()
                        })
                    }
                }
            }

        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Offer.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Offer().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}