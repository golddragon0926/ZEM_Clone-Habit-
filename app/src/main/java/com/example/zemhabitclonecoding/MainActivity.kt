package com.example.zemhabitclonecoding

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainButton()

    }

    private fun mainButton() {
        val button : Button = findViewById(R.id.main_button)

        button.setOnClickListener {
            val intent = Intent(this, HabitMainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}