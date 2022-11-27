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
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CompleteAdapter(private val context: Context) : RecyclerView.Adapter<CompleteAdapter.CustomViewHolder>() {

    private var habits = emptyList<Habit>()

    private lateinit var itemClickListener : OnItemClickListener

    interface OnItemClickListener{
        fun onClick(v: View, position: Int)
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener){
        this.itemClickListener = onItemClickListener
    }

    class CustomViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        private val status : ImageView = view.findViewById(R.id.complete_habit_status)
        private val title : TextView = view.findViewById(R.id.complete_habit_title)
        private val week : TextView = view.findViewById(R.id.complete_habit_week)
        private val date : TextView = view.findViewById(R.id.complete_habit_date)
        private val icon : ImageView = view.findViewById(R.id.complete_habit_icon)

        fun onBind(habit: Habit) {
            val iconNumber = habit.imageNumber

            status.setImageResource(R.drawable.complete)

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.habit_complete_item, parent, false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.onBind(habits[position])
        holder.itemView.setOnClickListener{
            itemClickListener.onClick(it, position)
        }

        val praiseBtn : Button = holder.itemView.findViewById(R.id.complete_habit_praise_btn)
        praiseBtn.setOnClickListener {
            val praiseView = Dialog(context)
            praiseView.setContentView(R.layout.cheer_dialog)

            praiseView.show()
            praiseView.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            var textView : TextView = praiseView.findViewById(R.id.cheer_title)
            textView.text = "칭찬 메세지 보내기"

            val confirm : Button = praiseView.findViewById(R.id.cheer_dialog_confirm)
            val cancelButton : TextView = praiseView.findViewById(R.id.cheer_dialog_x)

            val cheerRecyclerView : RecyclerView = praiseView.findViewById(R.id.cheer_recyclerView)
            val mAdapter = CheerAdapter(context)
            cheerRecyclerView.layoutManager = GridLayoutManager(context, 2)

            var data = ArrayList<Int>()
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
                        praiseView.dismiss()

                        val reviseConfirmView = Dialog(context)
                        reviseConfirmView.setContentView(R.layout.cheer_comfirm_dialog)

                        reviseConfirmView.show()
                        reviseConfirmView.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                        var praiseTextView : TextView = reviseConfirmView.findViewById(R.id.cheer_confirm_title)
                        var imageView : ImageView = reviseConfirmView.findViewById(R.id.cheer_confirm_imageView)

                        praiseTextView.text = "칭찬 메시지를 보냈습니다."
                        imageView.setBackgroundResource(data[temp])
                        Handler(Looper.getMainLooper()).postDelayed({reviseConfirmView.cancel()}, 2000)
                        praiseBtn.isEnabled = false
                    }
                }
            })
            cancelButton.setOnClickListener {
                praiseView.dismiss()
            }
            cheerRecyclerView.adapter = mAdapter
        }
    }

    override fun getItemCount(): Int {
        return habits.size
    }

    internal fun setHabits(habits: List<Habit>){
        this.habits = habits
        notifyDataSetChanged()
    }
}