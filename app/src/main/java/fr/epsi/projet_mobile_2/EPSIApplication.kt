package fr.epsi.projet_mobile_2

import android.app.Application
import android.widget.Toast

class EPSIApplication:Application() {

    override fun onCreate() {
        super.onCreate()
    }

    override fun onLowMemory() {
        super.onLowMemory()
    }

    fun showToast(txt:String){
        Toast.makeText(this,txt,Toast.LENGTH_SHORT).show()
    }

}