package fr.epsi.projet_mobile_2

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.google.android.material.textfield.TextInputLayout
import org.json.JSONObject

class RegisterActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        setHeaderTitle(getString(R.string.create_title))

        val firstName = findViewById<TextInputLayout>(R.id.FistNameTextField).editText
        val lastName = findViewById<TextInputLayout>(R.id.LastNameTextField).editText
        val address = findViewById<TextInputLayout>(R.id.AddressTextField).editText
        val email = findViewById<TextInputLayout>(R.id.EmailTextField).editText
        val zipcode = findViewById<TextInputLayout>(R.id.ZipcodeTextField).editText
        val city = findViewById<TextInputLayout>(R.id.CityTextField).editText
        val cardNumber = findViewById<TextInputLayout>(R.id.CardTextField).editText

        val buttonRegister = findViewById<Button>(R.id.buttonRegister)

        val bundle : Bundle? = intent.extras

        if(intent.hasExtra("json")) {

            val jsonObject = JSONObject(bundle?.getString("json").toString())
            firstName?.setText( jsonObject.getString("firstName"))
            lastName?.setText( jsonObject.getString("lastName"))
            address?.setText( jsonObject.getString("address"))
            email?.setText( jsonObject.getString("email"))
            zipcode?.setText( jsonObject.getString("zipcode"))
            city?.setText( jsonObject.getString("city"))
            cardNumber?.setText( jsonObject.getString("cardRef"))

        }

        buttonRegister.setOnClickListener {

            val prenom = firstName?.text.toString()
            val nom = lastName?.text.toString()
            val eMail = email?.text.toString()
            val adresse = address?.text.toString()
            val codePostal = zipcode?.text.toString()
            val ville = city?.text.toString()
            val carteNumero = cardNumber?.text.toString()

            val sharedPreferences = getSharedPreferences("Test", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("FIRSTNAME", prenom)
            editor.putString("LASTNAME", nom)
            editor.putString("EMAIL", eMail)
            editor.putString("ADDRESS", adresse)
            editor.putString("ZIPCODE", codePostal)
            editor.putString("CITY", ville)
            editor.putString("CARDNUMBER", carteNumero)
            editor.apply()

            val intent = Intent(this, SplashActivity::class.java )
            startActivity(intent)
        }
    }
}