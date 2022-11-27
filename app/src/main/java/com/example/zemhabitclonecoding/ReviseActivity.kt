package com.example.zemhabitclonecoding

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.example.zemhabitclonecoding.databinding.ActivityReviseBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ReviseActivity : AppCompatActivity() {
    private val viewModel : HabitDataViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityReviseBinding>(this,
            R.layout.activity_revise
        )
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val id : Int = intent.getIntExtra("id", 0)

        backButton()
        initCreate(id)
        weekSetting()
        alarmSetting()
        zemconSetting()
        confirm(id)
    }

    private fun backButton() {
        val backBtn : ImageView = findViewById(R.id.revise_back_btn)
        backBtn.setOnClickListener {
            onBackPressed()
        }
    }

    private fun initCreate(id : Int) {
        val image : ImageView = findViewById(R.id.revise_img)
        val habitTitle : TextView = findViewById(R.id.revise_title_txt)
        val habitName : TextView = findViewById(R.id.revise_name_write_txt)
        val startDate : TextView = findViewById(R.id.revise_term_write1_txt)
        val endDate : TextView = findViewById(R.id.revise_term_write2_txt)

        CoroutineScope(Dispatchers.IO).launch {
            val habit = viewModel.itemClick(id)

            when(habit.imageNumber){
                "0" -> {
                    image.setImageResource(R.drawable.heart)
                }
                "1" -> {
                    image.setImageResource(R.drawable.pencil)
                }
                "2" -> {
                    image.setImageResource(R.drawable.talk)
                }
                "3" -> {
                    image.setImageResource(R.drawable.time)
                }
                "4" -> {
                    image.setImageResource(R.drawable.phone)
                }
                "5" -> {
                    image.setImageResource(R.drawable.house)
                }
                "6" -> {
                    image.setImageResource(R.drawable.school)
                }
                "7" -> {
                    image.setImageResource(R.drawable.etc)
                }
            }

            habitTitle.text = habit.firstTitle
            habitName.text = habit.secondTitle
            startDate.text = habit.startDate
            endDate.text = habit.endDate
        }
    }

    private fun weekSetting() {
        val week : TextView = findViewById(R.id.revise_week_write_txt)

        week.setOnClickListener {
            val weekDialogView = Dialog(this)
            weekDialogView.setContentView(R.layout.week_dialog)

            weekDialogView.show()
            weekDialogView.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            val allButton: TextView = weekDialogView.findViewById(R.id.week_all_btn)
            val monday: TextView = weekDialogView.findViewById(R.id.week_Monday)
            val tuesday: TextView = weekDialogView.findViewById(R.id.week_Tuesday)
            val wednesday: TextView = weekDialogView.findViewById(R.id.week_Wednesday)
            val thursday: TextView = weekDialogView.findViewById(R.id.week_Thursday)
            val friday: TextView = weekDialogView.findViewById(R.id.week_Friday)
            val saturday: TextView = weekDialogView.findViewById(R.id.week_Saturday)
            val sunday: TextView = weekDialogView.findViewById(R.id.week_Sunday)

            var sWeek = ""
            var sum = 0

            var nWeek = IntArray(8)
            for (i: Int in 0..7){
                nWeek[i] = 0
            }

            allButton.setBackgroundResource(R.drawable.week_allclick_shape)
            nWeek[0] = 1

            allButton.setOnClickListener {
                if (nWeek[0] == 1){
                    allButton.setBackgroundResource(R.drawable.cancel_button_shape)
                    nWeek[0] = 0
                }
                else{
                    allButton.setBackgroundResource(R.drawable.week_allclick_shape)
                    nWeek[0] = 1
                }
            }

            monday.setOnClickListener {
                if (nWeek[1] == 1){
                    monday.setBackgroundResource(R.drawable.tab_habit_create_unselected)
                    nWeek[1] = 0
                }
                else{
                    monday.setBackgroundResource(R.drawable.tab_habit_create_selected)
                    nWeek[1] = 1

                    if (nWeek[0] == 1){
                        allButton.setBackgroundResource(R.drawable.cancel_button_shape)
                        nWeek[0] = 0
                    }
                }
            }

            tuesday.setOnClickListener {
                if (nWeek[2] == 1){
                    tuesday.setBackgroundResource(R.drawable.tab_habit_create_unselected)
                    nWeek[2] = 0
                }
                else{
                    tuesday.setBackgroundResource(R.drawable.tab_habit_create_selected)
                    nWeek[2] = 1

                    if (nWeek[0] == 1){
                        allButton.setBackgroundResource(R.drawable.cancel_button_shape)
                        nWeek[0] = 0
                    }
                }
            }

            wednesday.setOnClickListener {
                if (nWeek[3] == 1) {
                    wednesday.setBackgroundResource(R.drawable.tab_habit_create_unselected)
                    nWeek[3] = 0
                }
                else{
                    wednesday.setBackgroundResource(R.drawable.tab_habit_create_selected)
                    nWeek[3] = 1

                    if (nWeek[0] == 1) {
                        allButton.setBackgroundResource(R.drawable.cancel_button_shape)
                        nWeek[0] = 0
                    }
                }
            }

            thursday.setOnClickListener {
                if (nWeek[4] == 1){
                    thursday.setBackgroundResource(R.drawable.tab_habit_create_unselected)
                    nWeek[4] = 0
                }
                else{
                    thursday.setBackgroundResource(R.drawable.tab_habit_create_selected)
                    nWeek[4] = 1

                    if (nWeek[0] == 1){
                        allButton.setBackgroundResource(R.drawable.cancel_button_shape)
                        nWeek[0] = 0
                    }
                }
            }

            friday.setOnClickListener {
                if (nWeek[5] == 1){
                    friday.setBackgroundResource(R.drawable.tab_habit_create_unselected)
                    nWeek[5] = 0
                }
                else{
                    friday.setBackgroundResource(R.drawable.tab_habit_create_selected)
                    nWeek[5] = 1

                    if (nWeek[0] == 1){
                        allButton.setBackgroundResource(R.drawable.cancel_button_shape)
                        nWeek[0] = 0
                    }
                }
            }

            saturday.setOnClickListener {
                if (nWeek[6] == 1){
                    saturday.setBackgroundResource(R.drawable.tab_habit_create_unselected)
                    nWeek[6] = 0
                }
                else{
                    saturday.setBackgroundResource(R.drawable.tab_habit_create_selected)
                    nWeek[6] = 1

                    if (nWeek[0] == 1){
                        allButton.setBackgroundResource(R.drawable.cancel_button_shape)
                        nWeek[0] = 0
                    }
                }
            }

            sunday.setOnClickListener {
                if (nWeek[7] == 1){
                    sunday.setBackgroundResource(R.drawable.tab_habit_create_unselected)
                    nWeek[7] = 0
                }
                else{
                    sunday.setBackgroundResource(R.drawable.tab_habit_create_selected)
                    nWeek[7] = 1

                    if (nWeek[0] == 1){
                        allButton.setBackgroundResource(R.drawable.cancel_button_shape)
                        nWeek[0] = 0
                    }
                }
            }

            val confirmButton: Button = weekDialogView.findViewById(R.id.week_confirm)
            val cancelButton: Button = weekDialogView.findViewById(R.id.week_cancel)

            confirmButton.setOnClickListener {
                if (nWeek[0] == 1){
                    week.text = "매일"
                    weekDialogView.dismiss()
                }
                else{
                    for (i in 1..7){
                        if(nWeek[i] == 1){
                            sum += nWeek[i]
                        }
                    }
                    Log.e("sum", sum.toString())
                    if (sum == 0){
                        Toast.makeText(applicationContext, "요일을 선택해주세요.", Toast.LENGTH_SHORT).show()
                    }
                    else if(sum == 7){
                        sWeek = "매일"
                        week.text = sWeek
                        weekDialogView.dismiss()
                    }
                    else{
                        if (nWeek[1] == 1){
                            sWeek += "월 "
                        }
                        if (nWeek[2] == 1){
                            sWeek += "화 "
                        }
                        if (nWeek[3] == 1){
                            sWeek += "수 "
                        }
                        if (nWeek[4] == 1){
                            sWeek += "목 "
                        }
                        if (nWeek[5] == 1){
                            sWeek += "금 "
                        }
                        if (nWeek[6] == 1){
                            sWeek += "토 "
                        }
                        if (nWeek[7] == 1){
                            sWeek += "일 "
                        }
                        week.text = sWeek
                        weekDialogView.dismiss()
                    }
                }
            }

            cancelButton.setOnClickListener{
                weekDialogView.dismiss()
            }
        }
    }

    private fun alarmSetting() {
        val switch : Switch = findViewById(R.id.revise_alarm_switch)
        val alarmTextView : TextView = findViewById(R.id.revise_alarm_write_txt)

        switch.setOnCheckedChangeListener { compoundButton, b ->

            if (b){
                alarmClick()
                alarmTextView.setOnClickListener {
                    alarmClick()
                }
            }
            else{
                alarmTextView.text = "없음"
            }
        }
    }

    private fun alarmClick() {
        val alarmTextView : TextView = findViewById(R.id.revise_alarm_write_txt)

        val alarmDialogView = Dialog(this)
        alarmDialogView.setContentView(R.layout.alarm_dialog)

        alarmDialogView.show()
        alarmDialogView.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val apmPicker : NumberPicker = alarmDialogView.findViewById(R.id.am_pm)
        val hourPicker : NumberPicker = alarmDialogView.findViewById(R.id.hour)
        val minutePicker : NumberPicker = alarmDialogView.findViewById(R.id.minute)

        val apm : Array<String> =  arrayOf("오전", "오후")
        val aHour : Array<String> = Array(12){
                i -> i.toString()
        }
        val aMinute : Array<String> = Array(60){
                i -> i.toString()
        }

        for(i in 0..11){
            if(i < 9){
                aHour[i] = "0${i+1}"
            }
            else{
                aHour[i] = "${i+1}"
            }
        }

        for(i in 0..59){
            if(i < 10){
                aMinute[i] = "0$i"
            }
            else{
                aMinute[i] = "$i"
            }
        }

        apmPicker.minValue = 0
        apmPicker.maxValue = 1
        apmPicker.displayedValues = apm

        hourPicker.minValue = 0
        hourPicker.maxValue = 11
        hourPicker.displayedValues = aHour

        minutePicker.minValue = 0
        minutePicker.maxValue = 59
        minutePicker.displayedValues = aMinute

        val confirmButton: Button = alarmDialogView.findViewById(R.id.alarm_dialog_confirm)
        val cancelButton: Button = alarmDialogView.findViewById(R.id.alarm_dialog_cancel)

        confirmButton.setOnClickListener {
            apmPicker.setOnValueChangedListener { numberPicker, i, i2 ->
                apmPicker.value = i2
            }
            hourPicker.setOnValueChangedListener { numberPicker, i, i2 ->
                hourPicker.value = i2
            }
            minutePicker.setOnValueChangedListener { numberPicker, i, i2 ->
                minutePicker.value = i2
            }
            alarmTextView.text = "${apm[apmPicker.value]} ${aHour[hourPicker.value]}시 ${aMinute[minutePicker.value]}분"

            alarmDialogView.dismiss()
        }

        cancelButton.setOnClickListener{
            alarmDialogView.dismiss()
        }
    }

    private fun zemconSetting() {
        val zemconView : TextView = findViewById(R.id.revise_zemcon_write_txt)
        val minusButton : Button = findViewById(R.id.revise_minus_btn)
        val plusButton : Button = findViewById(R.id.revise_plus_btn)

        var zemconCount = 5
        minusButton.setOnClickListener {
            if (zemconCount == 0){
                zemconCount = 0
            }
            else if(zemconCount != 0){
                zemconCount -= 5
            }
            zemconView.text = zemconCount.toString()
        }
        plusButton.setOnClickListener {
            if (zemconCount == 50){
                zemconCount = 50
            }
            else if(zemconCount != 50){
                zemconCount += 5
            }
            zemconView.text = zemconCount.toString()
        }
    }

    private fun confirm(id : Int) {
        val cancelButton : Button = findViewById(R.id.revise_zemcon_cancel_button)
        val confirmButton : Button = findViewById(R.id.revise_zemcon_confirm_button)

        cancelButton.setOnClickListener {
            finish()
        }

        confirmButton.setOnClickListener {
            val week : TextView = findViewById(R.id.revise_week_write_txt)
            val alarm : TextView = findViewById(R.id.revise_alarm_write_txt)
            val zemcon : TextView = findViewById(R.id.revise_zemcon_write_txt)

            val sWeek : String = week.text.toString()
            val sAlarm : String = alarm.text.toString()
            val sZemcon : String = zemcon.text.toString()

            CoroutineScope(Dispatchers.IO).launch {
                viewModel.update(id, 4)
                viewModel.reviseInsert(HabitReviseData(id, sWeek, sAlarm, sZemcon.toInt()))
            }

            val intent = Intent(applicationContext, HabitMainActivity::class.java)
            intent.putExtra("tab", 2)
            startActivity(intent)
            finish()
        }
    }
}