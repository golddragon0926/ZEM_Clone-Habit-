package com.example.zemhabitclonecoding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class HabitMainCreateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_habit_main_create)

        backBtn()
        fragmentChange()
    }

    private fun backBtn() {
        val backButton : ImageView = findViewById(R.id.habit_main_create_back_btn)
        backButton.setOnClickListener {
            onBackPressed()
        }
    }

    private fun fragmentChange(){
        val fragment = supportFragmentManager.beginTransaction()
        fragment.replace(R.id.habit_create_frame, HabitMainCreateFragment()).commit()
    }
}