package com.example.zemhabitclonecoding

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

class Repository(mDatabase: HabitDatabase) {
    private val habitDao = mDatabase.habitDao()
    var progressHabits : LiveData<List<Habit>> = habitDao.selectAll(2)
    var completeHabits : LiveData<List<Habit>> = habitDao.selectAll(3)
    var waitHabits : LiveData<List<Habit>> = habitDao.waitAll(1, 4)

    companion object{
        private var sInstance : Repository? = null
        fun getInstance(database: HabitDatabase): Repository{
            return sInstance
                ?: synchronized(this){
                val instance = Repository(database)
                sInstance = instance
                instance
                }
        }
    }

    suspend fun insert(habit: Habit){
        habitDao.insert(habit)
    }

    suspend fun itemClick(id: Int) : Habit{
        return habitDao.getItemClick(id)
    }

    suspend fun deleteClick(id : Int){
        habitDao.deleteClick(id)
    }

    suspend fun updateClick(id : Int, status : Int){
        habitDao.updateStatus(id, status)
    }

    suspend fun deleteAll(){
        habitDao.deleteAll()
    }

    suspend fun confirmClick(id : Int) : Int{
        return habitDao.confirmClick(id)
    }

    suspend fun reviseInsert(habitReviseData : HabitReviseData){
        habitDao.reviseInsert(habitReviseData)
    }

    suspend fun reviseDB(id : Int) : HabitReviseData{
        return habitDao.reviseAll(id)
    }
    suspend fun reviseUpdate(id: Int, statusNum : Int, week : String, time : String, zemCount : Int){
        habitDao.reviseUpdate(id, statusNum, week, time, zemCount)
    }

    suspend fun deleteRevise(id : Int){
        habitDao.deleteRevise(id)
    }
}