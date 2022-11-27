package com.example.zemhabitclonecoding

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.zemhabitclonecoding.databinding.ActivityHabitSubCreateBinding
import com.google.android.material.tabs.TabLayout
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import kotlin.properties.Delegates

class HabitSubCreateActivity : AppCompatActivity() {
    companion object{
        val now: LocalDate = LocalDate.now()

        var startDate: LocalDate = LocalDate.now()
        var endDate : LocalDate = now.plusDays(29)

        var startYearPosition = now.year
        var startMonthPosition = now.monthValue-1
        var startDayPosition = now.dayOfMonth-1

        var endYearPosition = endDate.year
        var endMonthPosition = endDate.monthValue-1
        var endDayPosition = endDate.dayOfMonth-1

        var count = 30
        var zemconCount = 5

        var apmPickerPosition = 0
        var hourPosition = 0
        var minutePosition = 0
    }
    private val viewModel : HabitDataViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityHabitSubCreateBinding>(this, R.layout.activity_habit_sub_create)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val selectNumber: String? = intent.getStringExtra("img")
        val gMainTitle: String? = intent.getStringExtra("mainTitle")
        val gSubTitle: String? = intent.getStringExtra("subTitle")

        backBtn()
        initTitle(selectNumber!!, gMainTitle!!, gSubTitle!!)
        dateSet()
        initWeek()
        initAlarm()
        zemconsetting()
        confirm(selectNumber!!, gMainTitle!!)
    }

    private fun backBtn() {
        val backButton : ImageView = findViewById(R.id.habit_sub_create_back_btn)
        backButton.setOnClickListener {
            onBackPressed()
        }
    }

    private fun initTitle(selectNumber: String, mainGetTitle: String, subGetTitle: String) {
        val image: ImageView = findViewById(R.id.habit_sub_create_img)
        val mainTitle: TextView = findViewById(R.id.habit_sub_create_title_txt)
        val subTitle: TextView = findViewById(R.id.habit_sub_create_title_write_txt)

        mainTitle.text = mainGetTitle
        subTitle.text = subGetTitle

        when(selectNumber){
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
    }

    //기간 설정
    private fun dateSet() {
        val dateStart : TextView = findViewById(R.id.habit_sub_create_term_write1_txt)
        val dateEnd : TextView = findViewById(R.id.habit_sub_create_term_write2_txt)

        val description : TextView = findViewById(R.id.habit_sub_create_zemcon_description_txt)

        val strNow: String = now.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))
        var strPlusDay = endDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))

        dateStart.text = strNow
        dateEnd.text = strPlusDay

        //시작 기간
        dateStart.setOnClickListener {
            val startDialogView = Dialog(this)
            startDialogView.setContentView(R.layout.start_dialog)

            startDialogView.show()
            startDialogView.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            val startYearPicker : NumberPicker = startDialogView.findViewById(R.id.start_dialog_year)
            val startMonthPicker : NumberPicker = startDialogView.findViewById(R.id.start_dialog_month)
            val startDayPicker : NumberPicker = startDialogView.findViewById(R.id.start_dialog_day)

            val confirmButton: Button = startDialogView.findViewById(R.id.start_dialog_confirm)
            val cancelButton: Button = startDialogView.findViewById(R.id.start_dialog_cancel)

            startYearPicker.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
            startMonthPicker.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
            startDayPicker.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS

            val sMonth : Array<String> = Array(12){
                now.monthValue.toString()
            }
            val sDay : Array<String> = Array(31){
                now.dayOfMonth.toString()
            }

            for(i in 0..11){
                if(i < 9){
                    sMonth[i] = "0${i+1}"
                }
                else{
                    sMonth[i] = "${i+1}"
                }
            }

            for(i in 0..30){
                if(i < 9){
                    sDay[i] = "0${i+1}"
                }
                else{
                    sDay[i] = "${i+1}"
                }
            }

            startYearPicker.setOnValueChangedListener { numberPicker, i, i2 ->
                startYearPosition = i2
            }
            startMonthPicker.setOnValueChangedListener { numberPicker, i, i2 ->
                startMonthPosition = i2
            }
            startDayPicker.setOnValueChangedListener { numberPicker, i, i2 ->
                startDayPosition = i2
            }

            startYearPicker.minValue = now.year
            startYearPicker.maxValue = now.year+2
            startYearPicker.value = startYearPosition

            startMonthPicker.minValue = 0
            startMonthPicker.maxValue = 11
            startMonthPicker.displayedValues = sMonth
            startMonthPicker.value = startMonthPosition

            startDayPicker.minValue = 0
            startDayPicker.maxValue = 30
            startDayPicker.displayedValues = sDay
            startDayPicker.value = startDayPosition

            confirmButton.setOnClickListener {
                startDate = LocalDate.of(startYearPosition, sMonth[startMonthPosition].toInt(), sDay[startDayPosition].toInt())
                count = (ChronoUnit.DAYS.between(startDate, endDate).toInt()) + 1
                Log.e("startCount", (count+1).toString())

                if (count <= 0) {
                    endYearPosition = startYearPosition
                    endMonthPosition = startMonthPosition
                    endDayPosition = startDayPosition
                    endDate = LocalDate.of(endYearPosition, sMonth[endMonthPosition].toInt(), sDay[endDayPosition].toInt())

                    dateStart.text = "${startYearPosition}.${sMonth[startMonthPosition]}.${sDay[startDayPosition]}"
                    dateEnd.text = "${startYearPosition}.${sMonth[startMonthPosition]}.${sDay[startDayPosition]}"

                    startDialogView.dismiss()
                }
                else if (ChronoUnit.DAYS.between(now, startDate).toInt() < 0) {
                    Log.e("ChronoUnit.DAYS.between(now, startDate)", ChronoUnit.DAYS.between(now, startDate).toString())
                    Toast.makeText(this, "오늘 이전으로 설정할 수 없어요.", Toast.LENGTH_SHORT).show()
                }
                else {
                    dateStart.text = "${startYearPosition}.${sMonth[startMonthPosition]}.${sDay[startDayPosition]}"
                    startDialogView.dismiss()
                }
            }

            cancelButton.setOnClickListener{
                startDialogView.cancel()
            }
        }

        //종료 기간
        dateEnd.setOnClickListener {
            val endDialogView = Dialog(this)
            endDialogView.setContentView(R.layout.end_dialog)

            endDialogView.show()
            endDialogView.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            val endYearPicker : NumberPicker = endDialogView.findViewById(R.id.end_dialog_year)
            val endMonthPicker : NumberPicker = endDialogView.findViewById(R.id.end_dialog_month)
            val endDayPicker : NumberPicker = endDialogView.findViewById(R.id.end_dialog_day)

            val confirmButton: Button = endDialogView.findViewById(R.id.end_dialog_confirm)
            val cancelButton: Button = endDialogView.findViewById(R.id.end_dialog_cancel)

            endYearPicker.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
            endMonthPicker.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
            endDayPicker.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS

            val sMonth : Array<String> = Array(12){
                now.monthValue.toString()
            }
            val sDay : Array<String> = Array(31){
                now.dayOfMonth.toString()
            }

            for(i in 0..11){
                if(i < 9){
                    sMonth[i] = "0${i+1}"
                }
                else{
                    sMonth[i] = "${i+1}"
                }
            }

            for(i in 0..30){
                if(i < 9){
                    sDay[i] = "0${i+1}"
                }
                else{
                    sDay[i] = "${i+1}"
                }
            }

            endYearPicker.setOnValueChangedListener { numberPicker, i, i2 ->
                endYearPosition = i2
                endYearPicker.value = endYearPosition
            }
            endMonthPicker.setOnValueChangedListener { numberPicker, i, i2 ->
                endMonthPosition = i2
                endMonthPicker.value = endMonthPosition
            }
            endDayPicker.setOnValueChangedListener { numberPicker, i, i2 ->
                endDayPosition = i2
                endDayPicker.value = endDayPosition
            }

            endYearPicker.minValue = now.year
            endYearPicker.maxValue = now.year+2
            endYearPicker.value = endYearPosition


            endMonthPicker.minValue = 0
            endMonthPicker.maxValue = 11
            endMonthPicker.displayedValues = sMonth
            endMonthPicker.value = endMonthPosition

            endDayPicker.minValue = 0
            endDayPicker.maxValue = 30
            endDayPicker.displayedValues = sDay
            endDayPicker.value = endDayPosition

            confirmButton.setOnClickListener {
                endDate = LocalDate.of(endYearPosition, sMonth[endMonthPosition].toInt(), sDay[endDayPosition].toInt())

                count = (ChronoUnit.DAYS.between(startDate, endDate).toInt()) + 1
                Log.e("count", count.toString())
                if (count <= 0) {
                    Toast.makeText(this, "오늘 이전으로 설정할 수 없어요.", Toast.LENGTH_SHORT).show()
                }
                else if (count > 180) {
                    Toast.makeText(this, "기간 설정은 최대 180일을 초과하여 입력할 수 없어요.", Toast.LENGTH_SHORT).show()
                }
                else{
                    dateEnd.text = "${endYearPosition}.${sMonth[endMonthPosition]}.${sDay[endDayPosition]}"
                    description.text = "빠짐없이 한다면, ${count}회 x ${zemconCount}잼콘 = 최대 ${(count)*zemconCount}잼콘"
                    endDialogView.dismiss()
                }
            }

            cancelButton.setOnClickListener{
                endDialogView.cancel()
            }


        }

        //tab 7일, 14일, 30일, 100일
        val tabLayout: TabLayout = findViewById(R.id.habit_sub_create_tab)
        val tab : TabLayout.Tab? = tabLayout.getTabAt(2)
        tab!!.select()
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab!!.position){
                    0 -> {
                        endDate = startDate.plusDays(6)
                        strPlusDay = endDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))
                        endYearPosition = endDate.year
                        endMonthPosition = endDate.monthValue-1
                        endDayPosition = endDate.dayOfMonth-1
                        count = (ChronoUnit.DAYS.between(startDate, endDate).toInt()) + 1
                        description.text = "빠짐없이 한다면, ${count}회 x ${zemconCount}잼콘 = 최대 ${(count)*zemconCount}잼콘"
                        dateEnd.text = strPlusDay
                    }
                    1 -> {
                        endDate = startDate.plusDays(13)
                        strPlusDay = endDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))
                        endYearPosition = endDate.year
                        endMonthPosition = endDate.monthValue-1
                        endDayPosition = endDate.dayOfMonth-1
                        count = (ChronoUnit.DAYS.between(startDate, endDate).toInt()) + 1
                        description.text = "빠짐없이 한다면, ${count}회 x ${zemconCount}잼콘 = 최대 ${(count)*zemconCount}잼콘"
                        dateEnd.text = strPlusDay
                    }
                    2 -> {
                        endDate = startDate.plusDays(29)
                        strPlusDay = endDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))
                        endYearPosition = endDate.year
                        endMonthPosition = endDate.monthValue-1
                        endDayPosition = endDate.dayOfMonth-1
                        count = (ChronoUnit.DAYS.between(startDate, endDate).toInt()) + 1
                        description.text = "빠짐없이 한다면, ${count}회 x ${zemconCount}잼콘 = 최대 ${(count)*zemconCount}잼콘"
                        dateEnd.text = strPlusDay
                    }
                    3 -> {
                        endDate = startDate.plusDays(99)
                        strPlusDay = endDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))
                        endYearPosition = endDate.year
                        endMonthPosition = endDate.monthValue-1
                        endDayPosition = endDate.dayOfMonth-1
                        count = (ChronoUnit.DAYS.between(startDate, endDate).toInt()) + 1
                        description.text = "빠짐없이 한다면, ${count}회 x ${zemconCount}잼콘 = 최대 ${(count)*zemconCount}잼콘"
                        dateEnd.text = strPlusDay
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })


    }

    //요일 설정
    private fun initWeek() {
        val week : TextView = findViewById(R.id.habit_sub_create_week_write_txt)

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
                    when (sum) {
                        0 -> {
                            Toast.makeText(applicationContext, "요일을 선택해주세요.", Toast.LENGTH_SHORT).show()
                        }
                        7 -> {
                            sWeek = "매일"
                            week.text = sWeek
                            weekDialogView.dismiss()
                        }
                        else -> {
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
            }

            cancelButton.setOnClickListener{
                weekDialogView.dismiss()
            }
        }
    }

    private fun initAlarm() {
        val switch : Switch = findViewById(R.id.habit_sub_create_alarm_switch)
        val alarmTextView : TextView = findViewById(R.id.habit_sub_create_alarm_write_txt)

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

        val alarmTextView : TextView = findViewById(R.id.habit_sub_create_alarm_write_txt)

        val alarmDialogView = Dialog(this)
        alarmDialogView.setContentView(R.layout.alarm_dialog)

        alarmDialogView.show()
        alarmDialogView.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val apmPicker : NumberPicker = alarmDialogView.findViewById(R.id.am_pm)
        val hourPicker : NumberPicker = alarmDialogView.findViewById(R.id.hour)
        val minutePicker : NumberPicker = alarmDialogView.findViewById(R.id.minute)

        val apm : Array<String> =  arrayOf("오전", "오후")
        var aHour : Array<String> = Array(12){
                i -> i.toString()
        }
        var aMinute : Array<String> = Array(60){
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
        apmPicker.value = apmPickerPosition

        hourPicker.minValue = 0
        hourPicker.maxValue = 11
        hourPicker.displayedValues = aHour
        hourPicker.value = hourPosition

        minutePicker.minValue = 0
        minutePicker.maxValue = 59
        minutePicker.displayedValues = aMinute
        minutePicker.value = minutePosition

        apmPicker.setOnValueChangedListener { numberPicker, i, i2 ->
            apmPickerPosition = i2
            apmPicker.value = apmPickerPosition
        }
        hourPicker.setOnValueChangedListener { numberPicker, i, i2 ->
            hourPosition = i2
            hourPicker.value = hourPosition
        }
        minutePicker.setOnValueChangedListener { numberPicker, i, i2 ->
            minutePosition = i2
            minutePicker.value = minutePosition
        }

        val confirmButton: Button = alarmDialogView.findViewById(R.id.alarm_dialog_confirm)
        val cancelButton: Button = alarmDialogView.findViewById(R.id.alarm_dialog_cancel)

        confirmButton.setOnClickListener {
            alarmTextView.text = "${apm[apmPickerPosition]} ${aHour[hourPosition]}시 ${aMinute[minutePosition]}분"

            alarmDialogView.dismiss()
        }

        cancelButton.setOnClickListener{
            alarmDialogView.dismiss()
        }
    }

    private fun zemconsetting() {
        val zemconView : TextView = findViewById(R.id.habit_sub_create_zemcon_write_txt)
        val minusButton : Button = findViewById(R.id.habit_sub_create_minus_btn)
        val plusButton : Button = findViewById(R.id.habit_sub_create_plus_btn)
        val description : TextView = findViewById(R.id.habit_sub_create_zemcon_description_txt)

        description.text = "빠짐없이 한다면, ${count}회 x ${zemconCount}잼콘 = 최대 ${count*zemconCount}잼콘"
        minusButton.setOnClickListener {
            if (zemconCount == 0){
                zemconCount = 0
            }
            else if(zemconCount != 0){
                zemconCount -= 5
            }
            zemconView.text = zemconCount.toString()
            description.text = "빠짐없이 한다면, ${count}회 x ${zemconCount}잼콘 = 최대 ${count*zemconCount}잼콘"
        }
        plusButton.setOnClickListener {
            if (zemconCount == 50){
                zemconCount = 50
            }
            else if(zemconCount != 50){
                zemconCount += 5
            }
            zemconView.text = zemconCount.toString()
            description.text = "빠짐없이 한다면, ${count}회 x ${zemconCount}잼콘 = 최대 ${count*zemconCount}잼콘"
        }
    }

    private fun confirm(selectNumber: String, mainGetTitle: String) {
        val cancelButton : Button = findViewById(R.id.habit_sub_create_zemcon_cancel_button)
        val confirmButton : Button = findViewById(R.id.habit_sub_create_zemcon_confirm_button)

        cancelButton.setOnClickListener {
            finish()
        }

        confirmButton.setOnClickListener {
            val habitName : TextView = findViewById(R.id.habit_sub_create_title_write_txt)
            val startDate : TextView = findViewById(R.id.habit_sub_create_term_write1_txt)
            val endDate : TextView = findViewById(R.id.habit_sub_create_term_write2_txt)
            val week : TextView = findViewById(R.id.habit_sub_create_week_write_txt)
            val alarm : TextView = findViewById(R.id.habit_sub_create_alarm_write_txt)
            val zemcon : TextView = findViewById(R.id.habit_sub_create_zemcon_write_txt)

            var sHabitName : String = habitName.text.toString()
            var sStartDate : String = startDate.text.toString()
            var sEndDate : String = endDate.text.toString()
            var sWeek : String = week.text.toString()
            var sAlarm : String = alarm.text.toString()
            var sZemcon : String = zemcon.text.toString()

            Log.e("selectNumber", selectNumber)
            Log.e("mainGetTitle", mainGetTitle)
            Log.e("sHabitName", sHabitName)
            Log.e("sStartDate", sStartDate)
            Log.e("sEndDate", sEndDate)
            Log.e("sWeek", sWeek)
            Log.e("sAlarm", sAlarm)
            Log.e("sZemcon", sZemcon)

            if (mainGetTitle.isNotEmpty()){
                viewModel.insert(Habit(null, 1, selectNumber, mainGetTitle, sHabitName, sStartDate, sEndDate, sWeek, sAlarm, sZemcon.toInt()))
                Toast.makeText(applicationContext, "습관이 등록되었습니다.", Toast.LENGTH_SHORT).show()
                val intent = Intent(applicationContext, HabitMainActivity::class.java)
                intent.putExtra("tab", 2)
                startActivity(intent)
            }
            else{
                Toast.makeText(applicationContext, "습관 이름을 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

