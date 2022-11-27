package com.example.zemhabitclonecoding

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized
import kotlinx.coroutines.launch


@Database(entities = [Habit :: class, HabitReviseData :: class], version = 2, exportSchema = false)
abstract class HabitDatabase : RoomDatabase(){
    abstract fun habitDao() : HabitDao

    companion object{
        @Volatile
        private var INSTANCE : HabitDatabase? = null
        @OptIn(InternalCoroutinesApi::class)
        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): HabitDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HabitDatabase::class.java,
                    "habit_database"
                ).addCallback(HabitDatabaseCallback(scope))
                    .fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class HabitDatabaseCallback(
        private val scope: CoroutineScope
    ):RoomDatabase.Callback(){

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                homeDatabase(database.habitDao())
                }
            }
        }
        suspend fun homeDatabase(habitData: HabitDao){
            habitData.insert(Habit(1, 2, "6", "스스로 시간 관리", "약속모드 1시간 아껴 쓰기", "기본 습관", "", "매일", "없음", 15))
        }
    }
}