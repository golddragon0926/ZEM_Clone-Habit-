package com.example.zemhabitclonecoding

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import androidx.recyclerview.widget.RecyclerView


class HabitSubCreateFragment : Fragment() {
    private val subData = ArrayList<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_habit_sub_create, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var title = arguments?.getString("title")
        val number = arguments?.getString("position")

        initMainTitle(title!!, number!!)
        dataInsert(number!!)
        initRecyclerView(title!!, number!!)
    }
    private fun initMainTitle(title: String, number: String){
        val mainTitle : TextView = requireView().findViewById(R.id.habit_sub_mainTitle_txt)
        val image : ImageView = requireView().findViewById(R.id.habit_sub_icon_img)

        mainTitle.text = title

        when(number){
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

    private fun dataInsert(position: String){
        when(position) {
            "0" -> subData.apply {
                add("줄넘기 100번하기")
                add("계단 오르기")
                add("홈트레이닝 하기")
                add("철봉 매달리기")
                add("우유 3컴 마시기")
                add("편식 안하기")
                add("간식 조절하기")
                add("치실 하기")
                add("기타")
            }

            "1" -> subData.apply {
                add("한글책 읽기")
                add("영어책 읽기")
                add("영어 단어 외우기")
                add("수학 연산 풀기")
                add("학원 숙제 하기")
                add("공부 시간 지키기")
                add("기타")
            }

            "2" -> subData.apply {
                add("주변 어른에게 인사 잘하기")
                add("친구와 고운말 쓰기")
                add("소리지르지 않기")
                add("식사 인사하기")
                add("문안 인사하기")
                add("기타")
            }

            "3" -> subData.apply {
                add("일찍 자고 일찍 일어나기")
                add("스스로 등교 준비하기")
                add("학교 5분 전에 도착하기")
                add("학원 5분 전에 도착하기")
                add("쉬는 시간 지키기")
                add("기타")
            }

            "4" -> subData.apply {
                add("게임 시간 지키기")
                add("동영상 시청시간 지키기")
                add("정해진 시간에 SNS 하기")
                add("전화 예절 지키기")
                add("가족끼리 전화 끊을때 사랑해 말하기")
                add("기타")
            }

            "5" -> subData.apply {
                add("나만의 집안일 정하기")
                add("식사준비 돕기")
                add("다먹은 그릇 정리하기")
                add("책상 정리하기")
                add("이불 정돈하기")
                add("빨랫감 제자리 두기")
                add("외출복 걸어 두기")
                add("애완동물 산책시키기")
                add("기타")
            }
            "6" -> subData.apply {
                add("알림장 준비물 챙기기")
                add("필통 챙기기")
                add("일기 쓰기")
                add("예쁘게 글씨쓰기")
                add("기타")
            }
            "7" -> {
                val intent = Intent(context, HabitSubCreateActivity::class.java)
                intent.putExtra("mainTitle", "직접 입력")
                intent.putExtra("subTitle", "")
                intent.putExtra("img", "7")
                startActivity(intent)
                activity?.supportFinishAfterTransition()
            }
        }

    }

    private fun initRecyclerView(title: String, number: String){
        val subRecyclerView : RecyclerView = requireView().findViewById(R.id.habit_sub_RecyclerView_rv)
        subRecyclerView.layoutManager = LinearLayoutManager(context, VERTICAL ,false)
        val adapter = HabitSubCreateAdapter(subData)

        adapter.setItemClickListener(object : HabitSubCreateAdapter.OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                val intent = Intent(context, HabitSubCreateActivity::class.java)
                intent.apply {
                    putExtra("mainTitle", title)
                    putExtra("subTitle", subData[position])
                    putExtra("img", number)
                }
                startActivity(intent)
            }
        })
        subRecyclerView.adapter = adapter
    }


}