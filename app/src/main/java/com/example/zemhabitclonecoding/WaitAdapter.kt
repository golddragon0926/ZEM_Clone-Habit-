package com.example.zemhabitclonecoding

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import org.jetbrains.annotations.NotNull

class WaitAdapter(private val context: Context) :
    RecyclerView.Adapter<WaitAdapter.HabitViewHolder>() {

    private var habits = emptyList<Habit>()
    private lateinit var itemClickListener : OnItemClickListener

    interface OnItemClickListener{
        fun onClick(v: View, position: Int)
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener){
        this.itemClickListener = onItemClickListener
    }

    class HabitViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val waitStatus : ImageView = view.findViewById(R.id.habit_wait_state)
        private val waitTitle : TextView = view.findViewById(R.id.habit_wait_title)
        private val waitWeek : TextView = view.findViewById(R.id.habit_wait_week)
        private val waitIcon : ImageView = view.findViewById(R.id.habit_wait_icon)


        fun onBind(habit: Habit) {
            val statusNumber = habit.statusNumber
            val iconNumber = habit.imageNumber

            if (statusNumber == 1){
                waitStatus.setImageResource(R.drawable.registration)
            }
            else if (statusNumber == 4){
                waitStatus.setImageResource(R.drawable.revise)
            }

            when (iconNumber){
                "0" -> {
                    waitIcon.setImageResource(R.drawable.heart)
                }
                "1" -> {
                waitIcon.setImageResource(R.drawable.pencil)
                }
                "2" -> {
                waitIcon.setImageResource(R.drawable.talk)
                }
                "3" -> {
                waitIcon.setImageResource(R.drawable.time)
                }
                "4" -> {
                waitIcon.setImageResource(R.drawable.phone)
                }
                "5" -> {
                waitIcon.setImageResource(R.drawable.house)
                }
                "6" -> {
                waitIcon.setImageResource(R.drawable.school)
                }
                "7" -> {
                waitIcon.setImageResource(R.drawable.etc)
                }
            }
            waitTitle.text = habit.secondTitle
            waitWeek.text = habit.week

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.habit_wait_item, parent, false)
        return HabitViewHolder(view)
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        holder.itemView.setOnClickListener{
            itemClickListener.onClick(it, position)
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