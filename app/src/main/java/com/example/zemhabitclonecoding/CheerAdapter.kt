package com.example.zemhabitclonecoding

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView


class CheerAdapter(private val context: Context) : RecyclerView.Adapter<CheerAdapter.CustomViewHolder>(){
    private lateinit var imageData : ArrayList<Int>
    private lateinit var itemClickListener : OnItemClickListener
    private var selected = ArrayList<Int>()
    private lateinit var cardViewSave : CardView

    interface OnItemClickListener{
        fun onClick(v: View, position: Int)
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener){
        this.itemClickListener = onItemClickListener
    }

    class CustomViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val imageView : ImageView = view.findViewById(R.id.cheer_item_image)
        val cardView : CardView = view.findViewById(R.id.cheer_item_cardView)
        fun onBind(image: Int) {
            imageView.setImageResource(image)
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.cheer_item, parent, false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.onBind(imageData[position])
        holder.itemView.setOnClickListener{
            Log.e("TAG", "position = $position")
            if (selected.isEmpty()){
                Log.e("TAG", "empty")
                selected.add(position)
                holder.cardView.setBackgroundResource(R.drawable.cheer_click)
                cardViewSave = holder.cardView
            }
            else if (selected[0] == position){
                Log.e("TAG", "cancel")
                selected.clear()
                holder.cardView.setBackgroundResource(R.drawable.cheer_unclick)
            }
            else{
                cardViewSave.setBackgroundResource(R.drawable.cheer_unclick)

                selected.clear()
                selected.add(position)
                cardViewSave = holder.cardView
                holder.cardView.setBackgroundResource(R.drawable.cheer_click)
            }
            itemClickListener.onClick(it, position)
        }

    }


    override fun getItemCount(): Int {
        return imageData.size
    }

    internal fun setCheer(cheerIcon: ArrayList<Int>){
        this.imageData = cheerIcon
        notifyDataSetChanged()
    }

    internal fun getSelectItem(): Int {
        return if (selected.isEmpty()){
            -1
        } else{
            selected[0]
        }
    }
}