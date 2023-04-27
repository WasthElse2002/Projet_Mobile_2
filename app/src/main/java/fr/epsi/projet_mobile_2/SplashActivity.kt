package fr.epsi.projet_mobile_2

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val sharedPreferences = getSharedPreferences("TestFragment", Context.MODE_PRIVATE)

        Handler(Looper.getMainLooper()).postDelayed(Runnable {

            val prenom = sharedPreferences.getString("FIRSTNAME","").toString()
            val nom = sharedPreferences.getString("LASTNAME","").toString()

            if(prenom.isNotEmpty() && nom.isNotEmpty()) {
                val newIntent=Intent(application,TabbarActivity::class.java)
                startActivity(newIntent)
                finish()
            } else {
                val newIntent=Intent(application,CreateUserActivity::class.java)
                startActivity(newIntent)
                finish()
            }
        },2000)
    }
}

