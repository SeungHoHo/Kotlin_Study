package com.seungho.android.myapplication.mymemoapproom.Room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.seungho.android.myapplication.mymemoapproom.Dao.RoomMemoDao
import com.seungho.android.myapplication.mymemoapproom.Entity.RoomMemo

@Database(entities = arrayOf(RoomMemo::class), version = 1, exportSchema = false )
abstract class RoomHelper: RoomDatabase() {
    abstract fun roomMemoDao(): RoomMemoDao
}