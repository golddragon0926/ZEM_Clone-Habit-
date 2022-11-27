package com.example.zemhabitclonecoding

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


class HabitMainCreateFragment : Fragment() {
    val data = ArrayList<HabitMainCreateData>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.e("TAG", "onCreateView")

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_habit_main_create, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("TAG", "onViewCreated")
        dataInsert()
        initRecyclerView()
    }

    private fun initRecyclerView(){
        Log.e("TAG", "initRecyclerView")

        var habitCreateRecyclerView: RecyclerView = requireView().findViewById(R.id.habit_main_create_rv)
        habitCreateRecyclerView.layoutManager = GridLayoutManager(context, 2)
        var adapter = context?.let { HabitMainCreateAdapter(it, data) }
        adapter!!.setItemClickListener(object : HabitMainCreateAdapter.OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                var createTitle = data[position].createMainTitle
                createTitle += data[position].createSubTitle

                parentFragmentManager.beginTransaction().replace(R.id.habit_create_frame, HabitSubCreateFragment().apply {
                    arguments = Bundle().apply {
                        putString("title", createTitle)
                        putString("position", position.toString())
                    }
                }).addToBackStack(null).commit()
            }
        })
        habitCreateRecyclerView.adapter = adapter
    }

    private fun dataInsert(){
        data.clear()
        data.apply {
            add(HabitMainCreateData("건강한 ", "몸", R.drawable.heart))
            add(HabitMainCreateData("꾸준한 ", "공부", R.drawable.pencil))
            add(HabitMainCreateData("긍정적인 ", "언어 사용", R.drawable.talk))
            add(HabitMainCreateData("스스로 ", "시간 관리", R.drawable.time))
            add(HabitMainCreateData("올바른 ", "스마트폰 사용", R.drawable.phone))
            add(HabitMainCreateData("부지런한 ", "집안 생활", R.drawable.house))
            add(HabitMainCreateData("똑똑한 ", "학교 생활", R.drawable.school))
            add(HabitMainCreateData("", "직접 입력", R.drawable.etc))
        }
        Log.e("TAG", "data.size = ${data.size}")
    }
}