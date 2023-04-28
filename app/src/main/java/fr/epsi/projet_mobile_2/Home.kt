package fr.epsi.projet_mobile_2

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.journeyapps.barcodescanner.BarcodeEncoder


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val ARG_PARAM3 = "param3"


/**
 * A simple [Fragment] subclass.
 * Use the [Home.newInstance] factory method to
 * create an instance of this fragment.
 */
class Home : Fragment() {
    // TODO: Rename and change types of parameters
    private var barcode_data: String? = null
    private var prenom: String? = null
    private var nom : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            barcode_data = it.getString(ARG_PARAM1)
            prenom = it.getString(ARG_PARAM2)
            nom = it.getString(ARG_PARAM3)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences = this.activity?.getSharedPreferences("Test", Context.MODE_PRIVATE)
        Log.i("activity : ", activity.toString())
        // barcode data
        val barcode_data = sharedPreferences?.getString("CARDNUMBER","000000000").toString();
        val prenom = sharedPreferences?.getString("FIRSTNAME","").toString()
        val nom = sharedPreferences?.getString("LASTNAME","").toString()

        barcode_data.let { Log.i("param ", it) }
        prenom.let { Log.i("param ", it) }
        nom.let { Log.i("param ", it) }

        if (barcode_data != null) {
            val imageView = view.findViewById<ImageView>(R.id.codeBar)
            val userInfo = view.findViewById<TextView>(R.id.userInfo)
            userInfo.text = "$prenom    $nom"
            val cardInfo = view.findViewById<TextView>(R.id.cardInfo)
            cardInfo.text = "$barcode_data"
            val writer : MultiFormatWriter = MultiFormatWriter();
            try {
                val matrix : BitMatrix = writer.encode(barcode_data, BarcodeFormat.CODABAR, 800, 400)
                val encoder = BarcodeEncoder()
                val bitmap : Bitmap = encoder.createBitmap(matrix);
                imageView.setImageBitmap(bitmap)
            } catch (e : WriterException) {
                e.printStackTrace()
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Home.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String, param3 : String ) =
            Home().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                    putString(ARG_PARAM2, param3)
                }
            }
    }
}