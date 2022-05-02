package com.seungho.android.myapplication.mymemoapproom.Dao

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.seungho.android.myapplication.mymemoapproom.Entity.RoomMemo

@Dao
interface RoomMemoDao {
    @Query("select *from room_memo")
    suspend fun getAll(): List<RoomMemo>

    @Insert(onConflict = REPLACE)
    suspend fun insert(memo: RoomMemo)

    @Delete
    suspend fun delete(memo: RoomMemo)
}