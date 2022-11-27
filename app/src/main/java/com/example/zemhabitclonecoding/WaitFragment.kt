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
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zemhabitclonecoding.databinding.FragmentWaitBinding

class WaitFragment : Fragment() {
    private lateinit var viewModel: HabitDataViewModel
    private lateinit var binding : FragmentWaitBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_wait, container, false)
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

    private fun initRV(){
        val waitRecyclerView : RecyclerView = requireView().findViewById(R.id.wait_recyclerView)
        val mAdapter = WaitAdapter(requireContext())

        waitRecyclerView.layoutManager = GridLayoutManager(context, 2)
        mAdapter.setItemClickListener(object : WaitAdapter.OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                val itemClick = viewModel.waitHabits.value?.get(position)
                val id = viewModel.waitHabits.value?.get(position)?.id

                Log.e("itemClick", itemClick.toString())
                Log.e("id", id.toString())
                val intent = Intent(context, WaitItemClickActivity::class.java)
                intent.putExtra("id", id)
                startActivity(intent)
            }
        })

        viewModel.waitHabits.observe(viewLifecycleOwner, Observer {
                habits -> habits?.let {
                    mAdapter.setHabits(it)

                    val image : ImageView = requireView().findViewById(R.id.habit_wait_img)
                    val txt : TextView = requireView().findViewById(R.id.habit_wait_txt)
                    if (viewModel.waitHabits.value?.size != 0){
                        image.isGone = true
                        txt.isGone = true
                    }
                    else{
                        image.isGone = false
                        txt.isGone = false
                    }
                }
        })

        waitRecyclerView.adapter = mAdapter
    }
}
