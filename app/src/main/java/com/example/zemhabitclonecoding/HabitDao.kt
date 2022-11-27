package com.example.zemhabitclonecoding

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface HabitDao {
    @Query("SELECT * FROM habit_table")
    fun getAll() : LiveData<List<Habit>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(habit: Habit)

    @Query("UPDATE habit_table SET statusNumber = :status WHERE id = :id")
    suspend fun updateStatus(id : Int, status : Int)

    @Query("DELETE FROM habit_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM habit_table WHERE statusNumber = :status")
    fun selectAll(status: Int) : LiveData<List<Habit>>

    @Query("SELECT * FROM habit_table WHERE statusNumber = :status OR statusNumber = :statusNum ORDER BY statusNumber DESC" )
    fun waitAll(status: Int, statusNum: Int) : LiveData<List<Habit>>

    @Query("SELECT * FROM habit_table WHERE id = :id")
    suspend fun getItemClick(id : Int) : Habit

    @Query("DELETE FROM habit_table WHERE id = :id")
    suspend fun deleteClick(id : Int)

    @Query("SELECT zemCount FROM habit_table WHERE id = :id")
    suspend fun confirmClick(id : Int) : Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun reviseInsert(habitReviseData: HabitReviseData)

    @Query("SELECT * FROM habit_revise_table WHERE id = :id")
    suspend fun reviseAll(id : Int) : HabitReviseData

    @Query("UPDATE habit_table SET statusNumber = :statusNum, week = :week, time = :time, zemCount = :zemCount WHERE id = :id")
    suspend fun reviseUpdate(id: Int, statusNum : Int, week : String, time : String, zemCount : Int)

    @Query("DELETE FROM habit_revise_table WHERE id = :id")
    suspend fun deleteRevise(id : Int)
}