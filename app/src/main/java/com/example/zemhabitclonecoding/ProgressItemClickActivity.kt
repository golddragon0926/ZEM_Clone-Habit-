package com.example.zemhabitclonecoding

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.example.zemhabitclonecoding.databinding.ActivityProgressItemClickBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProgressItemClickActivity : AppCompatActivity() {
    private val viewModel : HabitDataViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityProgressItemClickBinding>(this, R.layout.activity_progress_item_click)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val intent = getIntent()

        val id : Int = intent.getIntExtra("id", 1)

        backBtn()
        initView(id)
        deleteButton(id)
        reviseButton(id)
        button(id)
    }

    private fun backBtn() {
        val backButton : ImageView = findViewById(R.id.progress_back_btn)
        backButton.setOnClickListener {
            onBackPressed()
        }
    }

    private fun reviseButton(id: Int) {
        val reviseButton : ImageView = findViewById(R.id.progress_item_click_revise_btn)

        reviseButton.setOnClickListener {
            val intent = Intent(this, ReviseActivity::class.java)
            intent.putExtra("id", id)
            startActivity(intent)
        }
    }

    private fun deleteButton(id: Int) {
        val deleteButton : ImageView = findViewById(R.id.progress_item_click_delete_btn)
        deleteButton.setOnClickListener {
            val alarmDialogView = Dialog(this)
            alarmDialogView.setContentView(R.layout.delete_dialog)

            alarmDialogView.show()
            alarmDialogView.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            val dialogText : TextView = alarmDialogView.findViewById(R.id.delete_dialog_name)

            val cancelButton : Button = alarmDialogView.findViewById(R.id.delete_dialog_cancel)
            val confirmButton : Button = alarmDialogView.findViewById(R.id.delete_dialog_confirm)


            CoroutineScope(Dispatchers.IO).launch {
                dialogText.text = viewModel.itemClick(id).secondTitle
            }
            cancelButton.setOnClickListener {
                alarmDialogView.dismiss()
            }

            confirmButton.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    viewModel.delete(id)
                    alarmDialogView.dismiss()
                }
            }
        }
    }

    private fun initView(id : Int) {
        val status : ImageView = findViewById(R.id.progress_item_click_status_img)
        val firstTitle : TextView = findViewById(R.id.progress_item_click_title_txt)
        val secondTitle : TextView = findViewById(R.id.progress_item_click_name_write_txt)
        val iconImage : ImageView = findViewById(R.id.progress_item_click_icon_image)
        val startDate : TextView = findViewById(R.id.progress_item_click_date_startDate_txt)
        val endDate : TextView = findViewById(R.id.progress_item_click_date_endDate_txt)
        val week : TextView = findViewById(R.id.progress_item_click_week_write_txt)
        val alarm : TextView = findViewById(R.id.progress_item_click_alarm_write_txt)
        val zemcon : TextView = findViewById(R.id.progress_item_click_zemcon_write_txt)



        CoroutineScope(Dispatchers.IO).launch{
            val habit = viewModel.itemClick(id)

            status.setImageResource(R.drawable.progress)
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
        }
    }

    private fun button(id : Int) {
        val confirmButton : Button = findViewById(R.id.progress_item_click_confirm_btn)

        confirmButton.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                viewModel.update(id, 3)

                confirm(id)
            }
        }
    }

    private suspend fun confirm(id : Int) {
        val zem = viewModel.confirmClick(id)

        Log.e("zem", zem.toString())

        val intent = Intent(this, HabitMainActivity::class.java)
        intent.putExtra("tab", 1)
        intent.putExtra("zem", zem)
        startActivity(intent)
    }
}