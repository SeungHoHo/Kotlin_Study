package com.seungho.mvvmex

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.seungho.mvvmex.room.Contact
import com.seungho.mvvmex.room.ContactDao

@Database(entities = [Contact::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun contactDao() : ContactDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                    AppDatabase::class.java, "contact")
                        .fallbackToDestructiveMigration() //데이터베이스가 갱신될 때 기존의 테이블을 버리고 새로 사용하도록 설정
                        .build()
                }
            }
            return INSTANCE
        }
    }
}