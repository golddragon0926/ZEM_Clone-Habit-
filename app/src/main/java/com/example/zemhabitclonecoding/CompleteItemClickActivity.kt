package com.example.zemhabitclonecoding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.zemhabitclonecoding.databinding.ActivityCompleteItemClickBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CompleteItemClickActivity : AppCompatActivity() {
    private val viewModel : HabitDataViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityCompleteItemClickBinding>(this, R.layout.activity_complete_item_click)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val intent = getIntent()

        val id : Int = intent.getIntExtra("id", 1)

        backBtn()
        initView(id)
        button(id)
    }

    private fun backBtn() {
        val backButton : ImageView = findViewById(R.id.complete_back_btn)
        backButton.setOnClickListener {
            onBackPressed()
        }
    }

    private fun initView(id: Int) {
        val firstTitle : TextView = findViewById(R.id.complete_item_click_title_txt)
        val secondTitle : TextView = findViewById(R.id.complete_item_click_name_write_txt)
        val iconImage : ImageView = findViewById(R.id.complete_item_click_icon_image)
        val startDate : TextView = findViewById(R.id.complete_item_click_date_startDate_txt)
        val endDate : TextView = findViewById(R.id.complete_item_click_date_endDate_txt)
        val week : TextView = findViewById(R.id.complete_item_click_week_write_txt)
        val alarm : TextView = findViewById(R.id.complete_item_click_alarm_write_txt)
        val zemcon : TextView = findViewById(R.id.complete_item_click_zemcon_write_txt)



        CoroutineScope(Dispatchers.IO).launch{
            val habit = viewModel.itemClick(id)

            when (habit.imageNumber){
                "0" -> {
                    iconImage.setImageResource(R.drawable.heart)
                }
                "1" -> {
                    iconImage.setImageResource(R.drawable.pencil)
                }
                "2" -> {
                    iconImage.setImageResource(R.drawable.talk)
                }
                "3" -> {
                    iconImage.setImageResource(R.drawable.time)
                }
                "4" -> {
                    iconImage.setImageResource(R.drawable.phone)
                }
                "5" -> {
                    iconImage.setImageResource(R.drawable.house)
                }
                "6" -> {
                    iconImage.setImageResource(R.drawable.school)
                }
                "7" -> {
                    iconImage.setImageResource(R.drawable.etc)
                }
            }

            firstTitle.text = habit.firstTitle
            secondTitle.text = habit.secondTitle
            startDate.text = habit.startDate
            endDate.text = habit.endDate
            week.text = habit.week
            alarm.text = habit.time
            zemcon.text = habit.zemCount.toString()
        }
    }

    private fun button(id: Int) {
        val cancelButton : Button = findViewById(R.id.complete_item_click_cancel_btn)

        cancelButton.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch{
                viewModel.delete(id)
            }
            finish()
        }
    }
}