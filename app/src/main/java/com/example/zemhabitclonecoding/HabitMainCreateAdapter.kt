package com.example.zemhabitclonecoding

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HabitMainCreateAdapter(private val context: Context, private val data: ArrayList<HabitMainCreateData>) :
    RecyclerView.Adapter<HabitMainCreateAdapter.CustomViewHolder>() {
    private lateinit var itemClickListener : OnItemClickListener

    interface OnItemClickListener{
        fun onClick(v: View, position: Int)
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener){
        this.itemClickListener = onItemClickListener
    }

    class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val mainTitle: TextView = view.findViewById(R.id.habit_main_mainTitle_txt)
        private val subTitle: TextView = view.findViewById(R.id.habit_main_subTitle_txt)
        private val mainIcon: ImageView = view.findViewById(R.id.habit_main_icon_img)

        fun bind(createData: HabitMainCreateData) {
            mainTitle.text = createData.createMainTitle
            subTitle.text = createData.createSubTitle
            mainIcon.setImageResource(createData.createImage)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CustomViewHolder {
        var view : View = LayoutInflater.from(parent.context).inflate(R.layout.habit_create_main_item, parent, false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(data[position])
        holder.itemView.setOnClickListener{
            itemClickListener.onClick(it, position)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }


}