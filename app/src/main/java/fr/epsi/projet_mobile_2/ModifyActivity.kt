package fr.epsi.projet_mobile_2

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.textfield.TextInputLayout

class ModifyActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify)
        setHeaderTitle(getString(R.string.myAccount))
        showBack()

        val sharedPreferences = getSharedPreferences("Test", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        var prenom = sharedPreferences.getString("FIRSTNAME","").toString()
        var nom = sharedPreferences.getString("LASTNAME","").toString()
        var email = sharedPreferences.getString("EMAIL","").toString()
        var address = sharedPreferences.getString("ADDRESS","").toString()
        var zipcode = sharedPreferences.getString("ZIPCODE","").toString()
        var city = sharedPreferences.getString("CITY","").toString()

        val firstNameView = findViewById<TextInputLayout>(R.id.FistNameTextFieldModify).editText
        val lastName = findViewById<TextInputLayout>(R.id.LastNameTextFieldModify).editText
        val adresseView = findViewById<TextInputLayout>(R.id.AddressTextFieldModify).editText
        val emailView = findViewById<TextInputLayout>(R.id.EmailTextFieldModify).editText
        val zipcodeView = findViewById<TextInputLayout>(R.id.ZipcodeTextFieldModify).editText
        val cityView = findViewById<TextInputLayout>(R.id.CityTextFieldModify).editText
        val modifyButton = findViewById<Button>(R.id.buttonModify)


        firstNameView?.setText(prenom)
        lastName?.setText(nom)
        adresseView?.setText(address)
        emailView?.setText(email)
        zipcodeView?.setText(zipcode)
        cityView?.setText(city)

        modifyButton.setOnClickListener {

            prenom = firstNameView?.text.toString()
            nom = lastName?.text.toString()
            email = emailView?.text.toString()
            address = adresseView?.text.toString()
            zipcode = zipcodeView?.text.toString()
            city = cityView?.text.toString()

            editor.putString("FIRSTNAME", prenom)
            editor.putString("LASTNAME", nom)
            editor.putString("EMAIL", email)
            editor.putString("ADDRESS", address)
            editor.putString("ZIPCODE", zipcode)
            editor.putString("CITY", city)
            editor.apply()
            finish()
        }
    }
}