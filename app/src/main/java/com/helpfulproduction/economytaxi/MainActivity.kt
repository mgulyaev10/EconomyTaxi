package com.helpfulproduction.economytaxi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (supportFragmentManager.findFragmentByTag(MapsFragment.TAG) == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, MapsFragment(), MapsFragment.TAG)
                .commit()
        }
    }
}
