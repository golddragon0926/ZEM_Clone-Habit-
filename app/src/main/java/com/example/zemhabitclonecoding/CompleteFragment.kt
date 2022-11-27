package com.example.zemhabitclonecoding

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zemhabitclonecoding.databinding.FragmentCompleteBinding
import com.example.zemhabitclonecoding.databinding.FragmentWaitBinding


class CompleteFragment : Fragment() {
    private lateinit var viewModel: HabitDataViewModel
    private lateinit var binding : FragmentCompleteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_complete, container, false)
        activity?.let {
            viewModel = ViewModelProvider(it).get(HabitDataViewModel::class.java)
            binding.viewModel = viewModel
            binding.lifecycleOwner = this
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRV()
    }

    private fun initRV() {
        val completeRecyclerView : RecyclerView = requireView().findViewById(R.id.complete_recyclerView)
        val mAdapter = CompleteAdapter(requireContext())

        completeRecyclerView.layoutManager = GridLayoutManager(context, 2)
        mAdapter.setItemClickListener(object : CompleteAdapter.OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                val itemClick = viewModel.completeHabits.value?.get(position)
                val id = viewModel.completeHabits.value?.get(position)?.id

                Log.e("itemClick", itemClick.toString())
                Log.e("id", id.toString())
                val intent = Intent(context, CompleteItemClickActivity::class.java)
                intent.putExtra("id", id)
                startActivity(intent)
            }
        })



        completeRecyclerView.adapter = mAdapter

        viewModel.completeHabits.observe(viewLifecycleOwner, Observer {
                habits -> habits?.let {
            mAdapter.setHabits(it)

            val image : ImageView = requireView().findViewById(R.id.habit_complete_img)
            val txt : TextView = requireView().findViewById(R.id.habit_complete_txt)
            if (viewModel.completeHabits.value?.size != 0){
                image.isGone = true
                txt.isGone = true
            }
            else{
                image.isGone = false
                txt.isGone = false
            }

        }
        })
    }
}