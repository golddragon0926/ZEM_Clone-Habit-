package com.example.zemhabitclonecoding

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habit_revise_table")
data class HabitReviseData(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "reviseWeek") val reviseWeek: String,
    @ColumnInfo(name = "reviseTime") val reviseTime: String,
    @ColumnInfo(name = "reviseZemCount") val reviseZemCount: Int
)
