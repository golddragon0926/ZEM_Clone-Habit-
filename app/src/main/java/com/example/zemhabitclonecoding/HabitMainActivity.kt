package com.example.zemhabitclonecoding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.zemhabitclonecoding.databinding.ActivityMainHabitBinding
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HabitMainActivity : AppCompatActivity() {
    private val viewModel : HabitDataViewModel by viewModels()

    companion object{
        var totalZemcon = 50
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainHabitBinding>(
            this,
            R.layout.activity_main_habit
        )
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        initZemcon()
        initTab()
    }

    private fun initZemcon() {
        val zemcon : TextView = findViewById(R.id.zemconCount)
        val getZem : Int = intent.getIntExtra("zem", 0)
        Log.e("getzem", getZem.toString())
        totalZemcon += getZem
        zemcon.text = totalZemcon.toString()
    }

    private fun initTab() {
        val progressTabCount : TextView = findViewById(R.id.progress_item_count_txt)
        val completeTabCount : TextView = findViewById(R.id.complete_item_count_txt)
        val waitTabCount : TextView = findViewById(R.id.wait_item_count_txt)

        viewModel.progressHabits.observe(this, Observer {
                habits -> habits?.let {
            progressTabCount.text = viewModel.progressHabits.value?.size.toString()
        }})

        viewModel.completeHabits.observe(this, Observer {
                habits -> habits?.let {
            completeTabCount.text = viewModel.completeHabits.value?.size.toString()
        }})

        viewModel.waitHabits.observe(this, Observer {
                habits -> habits?.let {
            waitTabCount.text = viewModel.waitHabits.value?.size.toString()
        }})

        //초기화
        val button : Button = findViewById(R.id.restart_btn)
        button.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                viewModel.deleteClick()
            }
        }

        val tab : Int = intent.getIntExtra("tab", 0)
        Log. e("tab", tab.toString())

        when (tab) {
            2 -> {
                changeFragment(WaitFragment())
                tabClick(tab)
            }
            1 -> {
                changeFragment(CompleteFragment())
                tabClick(tab)
            }
            else -> {
                changeFragment(ProgressFragment())
                tabClick(tab)
            }
        }
        tabClick(tab)
        fabButton()
    }

    private fun tabClick(num : Int){
        val tabLayout: TabLayout = findViewById(R.id.habit_tab)

        if(num == 2){
            tabLayout.getTabAt(num)?.select()
        }
        if(num == 1){
            tabLayout.getTabAt(num)?.select()
        }

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab!!.position){
                    0 -> {
                        changeFragment(ProgressFragment())
                    }
                    1 -> {
                        changeFragment(CompleteFragment())
                    }
                    2 -> {
                        changeFragment(WaitFragment())
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
    }

    private fun changeFragment(fragment: Fragment){
        val selectFragment: Fragment = fragment
        selectFragment.let{
            supportFragmentManager.beginTransaction().replace(R.id.habit_frameLayout, selectFragment).commit()
        }
    }

    private fun fabButton(){
        val fab: View = findViewById(R.id.fab_button)
        fab.setOnClickListener{
            startActivity(Intent(applicationContext, HabitMainCreateActivity::class.java))
        }
    }
}