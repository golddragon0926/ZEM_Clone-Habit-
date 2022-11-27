package com.example.zemhabitclonecoding

import android.content.Intent
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.example.zemhabitclonecoding.databinding.ActivityWaitItemClickBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WaitItemClickActivity : AppCompatActivity() {
    private val viewModel : HabitDataViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityWaitItemClickBinding>(this, R.layout.activity_wait_item_click)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val intent = getIntent()

        val id : Int = intent.getIntExtra("id", 1)

        backBtn()
        initView(id)
    }

    private fun backBtn() {
        val backButton : ImageView = findViewById(R.id.wait_back_btn)
        backButton.setOnClickListener {
            onBackPressed()
        }
    }

    private fun initView(id : Int) {
        val status : ImageView = findViewById(R.id.wait_item_click_status_img)
        val firstTitle : TextView = findViewById(R.id.wait_item_click_title_txt)
        val secondTitle : TextView = findViewById(R.id.wait_item_click_name_write_txt)
        val iconImage : ImageView = findViewById(R.id.wait_item_click_icon_image)
        val startDate : TextView = findViewById(R.id.wait_item_click_date_startDate_txt)
        val endDate : TextView = findViewById(R.id.wait_item_click_date_endDate_txt)
        val week : TextView = findViewById(R.id.wait_item_click_week_write_txt)
        val alarm : TextView = findViewById(R.id.wait_item_click_alarm_write_txt)
        val zemcon : TextView = findViewById(R.id.wait_item_click_zemcon_write_txt)

        CoroutineScope(Dispatchers.IO).launch{

            val habit = viewModel.itemClick(id)

            firstTitle.text = habit.firstTitle
            secondTitle.text = habit.secondTitle

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

            startDate.text = habit.startDate
            endDate.text = habit.endDate
            week.text = habit.week
            alarm.text = habit.time
            zemcon.text = habit.zemCount.toString()

            if (habit.statusNumber == 1){
                status.setImageResource(R.drawable.registration)
                waitButton(id)
            }
            else if(habit.statusNumber == 4){
                status.setImageResource(R.drawable.revise)

                stateRevise(id)
                reviseButton(id)
            }
        }
    }

    private fun waitButton(id : Int) {
        val cancelButton : Button = findViewById(R.id.wait_item_click_cancel_btn)
        val confirmButton : Button = findViewById(R.id.wait_item_click_confirm_btn)

        cancelButton.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch{
                viewModel.delete(id)
            }
            finish()
        }

        confirmButton.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch{
                viewModel.update(id, 2)
            }
            val intent = Intent(this, HabitMainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun reviseButton(id : Int) {
        val cancelButton : Button = findViewById(R.id.wait_item_click_cancel_btn)
        val confirmButton : Button = findViewById(R.id.wait_item_click_confirm_btn)

        cancelButton.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch{
                viewModel.update(id, 2)
                viewModel.deleteRevise(id)
            }
            finish()
        }

        confirmButton.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val reviseHabit = viewModel.reviseDB(id)
                viewModel.reviseUpdate(id, 2, reviseHabit.reviseWeek, reviseHabit.reviseTime, reviseHabit.reviseZemCount)
                viewModel.deleteRevise(id)
                Log.e("reviseWeek", reviseHabit.reviseWeek)
                Log.e("reviseTime", reviseHabit.reviseTime)
                Log.e("reviseZemCount", reviseHabit.reviseZemCount.toString())
            }
            val intent = Intent(this, HabitMainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun stateRevise(id: Int) {
        val week : TextView = findViewById(R.id.wait_item_click_week_write_txt)
        val alarm : TextView = findViewById(R.id.wait_item_click_alarm_write_txt)
        val zemcon : TextView = findViewById(R.id.wait_item_click_zemcon_write_txt)


        val reviseWeek : TextView = findViewById(R.id.wait_item_click_week_revise_txt)
        val reviseAlarm : TextView = findViewById(R.id.wait_item_click_alarm_revise_txt)
        val reviseZemcon : TextView = findViewById(R.id.wait_item_click_zemcon_revise_txt)

        CoroutineScope(Dispatchers.IO).launch {
            val habit = viewModel.itemClick(id)
            val reviseHabit = viewModel.reviseDB(id)

            reviseWeek.text = reviseHabit.reviseWeek
            reviseAlarm.text = reviseHabit.reviseTime
            reviseZemcon.text = reviseHabit.reviseZemCount.toString()

            if (habit.week != reviseWeek.text){
                week.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG)
                reviseWeek.isVisible = true
            }

            if (habit.time != reviseAlarm.text){
                alarm.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG)
                reviseAlarm.isVisible = true
            }

            if (habit.zemCount.toString() != reviseZemcon.text){
                zemcon.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG)
                reviseZemcon.isVisible = true
            }
        }
    }
}