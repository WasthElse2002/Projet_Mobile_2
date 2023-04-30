package fr.epsi.projet_mobile_2

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class ShopDetailsActivity : BaseActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_details)


        val bundle : Bundle?= intent.extras
        val name = bundle!!.getString("name")
        val desc = bundle.getString("description")
        val shopPicture = bundle.getString("pictureStore")
        val address = bundle.getString("address")
        val zipcode = bundle.getString("zipcode")
        val city = bundle.getString("city")

        setHeaderTitle(name.toString())
        showBack()

        val imageViewShop = findViewById<ImageView>(R.id.imageViewShop)
        val addressTextView = findViewById<TextView>(R.id.AddressTextField)
        val zipcodeCityTextView = findViewById<TextView>(R.id.ZipcodeTextField)
        val descriptionTextView = findViewById<TextView>(R.id.DescriptionTextField)

        if(imageViewShop != null) {
            Picasso.get().load(shopPicture).into(imageViewShop)
        }

        addressTextView.text = address
        zipcodeCityTextView.text = "$zipcode $city"
        descriptionTextView.text = desc

    }
}