package fr.epsi.projet_mobile_2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton

class CreateUserActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)
        setHeaderTitle(getString(R.string.create_title))

        val buttonInfo=findViewById<ImageButton>(R.id.buttonQR)
        buttonInfo.setOnClickListener(View.OnClickListener {
            val newIntent = Intent(application, ShowqrActivity::class.java)
            startActivity(newIntent)
        })

        val buttonProducts=findViewById<Button>(R.id.buttonNoQR)
        buttonProducts.setOnClickListener(View.OnClickListener {
            val newIntent = Intent(application, NoqrActivity::class.java)
            startActivity(newIntent)
        })
    }
}