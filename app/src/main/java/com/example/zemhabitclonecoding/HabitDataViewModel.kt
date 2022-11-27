package com.example.zemhabitclonecoding

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class HabitDataViewModel(application: Application) : AndroidViewModel(application){
    var repository : Repository = Repository(HabitDatabase.getDatabase(application, viewModelScope))
    var waitHabits : LiveData<List<Habit>> = repository.waitHabits
    var progressHabits : LiveData<List<Habit>> = repository.progressHabits
    var completeHabits : LiveData<List<Habit>> = repository.completeHabits

    fun insert(habit: Habit) = viewModelScope.launch(Dispatchers.IO){
        repository.insert(habit)
    }

    suspend fun itemClick(id : Int): Habit {
        return repository.itemClick(id)
    }

    suspend fun delete(id: Int) {
        repository.deleteClick(id)
    }

    suspend fun update(id : Int, status : Int){
        repository.updateClick(id, status)
    }

    suspend fun deleteClick(){
        repository.deleteAll()
    }

    suspend fun confirmClick(id : Int) : Int{
        return repository.confirmClick(id)
    }

    suspend fun reviseInsert(reviseData: HabitReviseData) = viewModelScope.launch(Dispatchers.IO){
        repository.reviseInsert(reviseData)
    }

    suspend fun reviseDB(id : Int) : HabitReviseData{
        return repository.reviseDB(id)
    }

    suspend fun reviseUpdate(id: Int, statusNum: Int, week: String, time: String, zemCount: Int){
        repository.reviseUpdate(id, statusNum, week, time, zemCount)
    }

    suspend fun deleteRevise(id : Int){
        repository.deleteRevise(id)
    }
}