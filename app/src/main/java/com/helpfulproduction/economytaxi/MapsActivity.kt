package com.helpfulproduction.economytaxi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MapsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        supportFragmentManager.beginTransaction()
            .add(R.id.container, MapsFragment())
            .commit()
    }
}
