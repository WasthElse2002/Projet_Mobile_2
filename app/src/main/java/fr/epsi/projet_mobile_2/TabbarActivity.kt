package fr.epsi.projet_mobile_2

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.journeyapps.barcodescanner.BarcodeEncoder

class TabbarActivity : BaseActivity() {

    lateinit var bottom_nav : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tabbar)
        setHeaderTitle(getString(R.string.myAccount))

        loadFragment(Home())

        // bottom Navigation
        bottom_nav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottom_nav.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.page_1 -> {
                    loadFragment(Home())
                    setHeaderTitle(getString(R.string.carte))
                    true
                }
                R.id.page_2 -> {
                    loadFragment(Offer())
                    setHeaderTitle(getString(R.string.offre))
                    true
                }
                R.id.page_3 -> {
                    loadFragment(Shop())
                    setHeaderTitle(getString(R.string.magasins))
                    true
                }
                else -> false
            }
        }

    }

    private  fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContent,fragment)
        transaction.commit()
    }

}