package com.example.zemhabitclonecoding

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.core.os.HandlerCompat.postDelayed
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.coroutines.coroutineContext

class ProgressAdapter(private val context: Context) :
    RecyclerView.Adapter<ProgressAdapter.CustomViewHolder>() {

    private var habits = emptyList<Habit>()
    private lateinit var itemClickListener : OnItemClickListener

    interface OnItemClickListener{
        fun onClick(v: View, position: Int)
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener){
        this.itemClickListener = onItemClickListener
    }

    class CustomViewHolder(view : View) : RecyclerView.ViewHolder(view){
        private val status : ImageView = view.findViewById(R.id.progress_habit_state)
        private val title : TextView = view.findViewById(R.id.progress_habit_title)
        private val week : TextView = view.findViewById(R.id.progress_habit_week)
        private val date : TextView = view.findViewById(R.id.progress_habit_date)
        private val icon : ImageView = view.findViewById(R.id.progress_habit_icon)


        fun onBind(habit: Habit) {
            val statusNumber = habit.statusNumber
            val iconNumber = habit.imageNumber
            if (statusNumber == 2){
                status.setImageResource(R.drawable.progress)
            }
            else{
                status.setImageResource(R.drawable.registration)
            }
            when (iconNumber){
                "0" -> {
                    icon.setImageResource(R.drawable.heart)
                }
                "1" -> {
                    icon.setImageResource(R.drawable.pencil)
                }
                "2" -> {
                    icon.setImageResource(R.drawable.talk)
                }
                "3" -> {
                    icon.setImageResource(R.drawable.time)
                }
                "4" -> {
                    icon.setImageResource(R.drawable.phone)
                }
                "5" -> {
                    icon.setImageResource(R.drawable.house)
                }
                "6" -> {
                    icon.setImageResource(R.drawable.school)
                }
                "7" -> {
                    icon.setImageResource(R.drawable.etc)
                }
            }
            title.text = habit.secondTitle
            week.text = habit.week
            date.text = habit.startDate

        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.habit_progress_item, parent, false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.itemView.setOnClickListener{
            itemClickListener.onClick(it, position)
        }

        val cheerBtn : Button = holder.itemView.findViewById(R.id.progress_habit_cheer_btn)
        cheerBtn.setOnClickListener {
            val cheerView = Dialog(context)
            cheerView.setContentView(R.layout.cheer_dialog)

            cheerView.show()
            cheerView.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            val cheerRecyclerView : RecyclerView = cheerView.findViewById(R.id.cheer_recyclerView)
            val mAdapter = CheerAdapter(context)


            cheerRecyclerView.layoutManager = GridLayoutManager(context, 2)

            val data = ArrayList<Int>()
            data.apply {
                data.add(R.drawable.one)
                data.add(R.drawable.two)
                data.add(R.drawable.three)
                data.add(R.drawable.four)
                data.add(R.drawable.five)
                data.add(R.drawable.six)
                data.add(R.drawable.seven)
                data.add(R.drawable.eight)
                data.add(R.drawable.nine)
                data.add(R.drawable.ten)
            }

            val confirm : Button = cheerView.findViewById(R.id.cheer_dialog_confirm)
            val cancelButton : TextView = cheerView.findViewById(R.id.cheer_dialog_x)

            mAdapter.setCheer(data)

            mAdapter.setItemClickListener(object : CheerAdapter.OnItemClickListener{
                override fun onClick(v: View, position: Int) {
                    var temp = mAdapter.getSelectItem()
                    Log.e("temp", temp.toString())

                    if (temp == -1){
                        confirm.setBackgroundResource(R.drawable.dim_button)
                        confirm.isEnabled = false
                    }
                    else{
                        confirm.setBackgroundResource(R.drawable.confirm_button_shape)
                        confirm.isEnabled = true
                    }
                    confirm.setOnClickListener {
                        cheerView.dismiss()

                        val cheerConfirmView = Dialog(context)
                        cheerConfirmView.setContentView(R.layout.cheer_comfirm_dialog)

                        cheerConfirmView.show()
                        cheerConfirmView.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                        var imageView : ImageView = cheerConfirmView.findViewById(R.id.cheer_confirm_imageView)
                        imageView.setBackgroundResource(data[temp])
                        Handler(Looper.getMainLooper()).postDelayed({cheerConfirmView.cancel()}, 2000)
                        cheerBtn.isEnabled = false
                    }
                }
            })
            cancelButton.setOnClickListener {
                cheerView.dismiss()
            }
            cheerRecyclerView.adapter = mAdapter
        }
        holder.onBind(habits[position])
    }

    override fun getItemCount(): Int {
        return habits.size
    }

    internal fun setHabits(habits: List<Habit>){
        this.habits = habits
        notifyDataSetChanged()
    }

}