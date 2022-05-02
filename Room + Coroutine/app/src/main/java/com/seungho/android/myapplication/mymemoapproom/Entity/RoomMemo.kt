package com.seungho.android.myapplication.mymemoapproom.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "room_memo")
class RoomMemo {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    var no: Long? = null

    @ColumnInfo
    var caption: String = ""

    @ColumnInfo
    var content: String = ""

    @ColumnInfo(name = "date")
    var datetime: String = ""

    constructor(no: Long?, caption: String, content: String, datetime: String) {
        this.no = no
        this.caption = caption
        this.content = content
        this.datetime = datetime
    }
}