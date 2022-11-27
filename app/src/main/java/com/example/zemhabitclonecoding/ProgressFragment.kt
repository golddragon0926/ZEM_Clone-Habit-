package com.example.zemhabitclonecoding

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isGone
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zemhabitclonecoding.databinding.FragmentProgressBinding
import com.example.zemhabitclonecoding.databinding.FragmentWaitBinding


class ProgressFragment : Fragment() {
    private lateinit var viewModel: HabitDataViewModel
    private lateinit var binding : FragmentProgressBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_progress, container, false)
        activity?.let{
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
        val progressRecyclerView : RecyclerView = requireView().findViewById(R.id.progress_recyclerView)
        val mAdapter = ProgressAdapter(requireContext())

        progressRecyclerView.layoutManager = GridLayoutManager(context, 2)
        mAdapter.setItemClickListener(object : ProgressAdapter.OnItemClickListener {

            override fun onClick(v: View, position: Int) {
                val id = viewModel.progressHabits.value?.get(position)?.id

                val intent = Intent(context, ProgressItemClickActivity::class.java)
                intent.putExtra("id", id)
                startActivity(intent)
            }
        })
        progressRecyclerView.adapter = mAdapter

        viewModel.progressHabits.observe(viewLifecycleOwner, Observer {
                habits -> habits?.let {
            mAdapter.setHabits(it)

            val image : ImageView = requireView().findViewById(R.id.habit_progress_img)
            val txt : TextView = requireView().findViewById(R.id.habit_progress_txt)
            if (viewModel.progressHabits.value?.size != 0){
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
