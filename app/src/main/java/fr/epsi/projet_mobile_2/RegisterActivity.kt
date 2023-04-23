package fr.epsi.projet_mobile_2

import android.os.Bundle

class RegisterActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.`activity_register.xml`)
        setHeaderTitle(getString(R.string.create_title))
    }
}