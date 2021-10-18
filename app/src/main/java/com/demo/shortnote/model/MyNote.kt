package com.demo.shortnote.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_my_notes")
data class MyNote (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "note_id")
    var id:Int,

    @ColumnInfo(name = "note_title")
    var title:String,

    @ColumnInfo(name = "note_details")
    var details:String,

    @ColumnInfo(name = "note_date_time")
    var dateTime:String

    )