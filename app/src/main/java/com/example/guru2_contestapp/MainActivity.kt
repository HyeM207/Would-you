package com.example.guru2_contestapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    lateinit var btn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn=findViewById(R.id.button)

        btn.setOnClickListener {
            val intent= Intent(this, NavigationActivity::class.java)
            startActivity(intent)
        }
    }
}