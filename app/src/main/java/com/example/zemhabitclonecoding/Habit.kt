package com.example.zemhabitclonecoding

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habit_table")
data class Habit(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "statusNumber") val statusNumber: Int,
    @ColumnInfo(name = "imageNumber") val imageNumber: String,
    @ColumnInfo(name = "firstTitle") val firstTitle: String,
    @ColumnInfo(name = "secondTitle") val secondTitle: String,
    @ColumnInfo(name = "startDate") val startDate: String,
    @ColumnInfo(name = "endDate") val endDate: String,
    @ColumnInfo(name = "week") val week: String,
    @ColumnInfo(name = "time") val time: String,
    @ColumnInfo(name = "zemCount") val zemCount: Int
    )