package com.seungho.android.myapplication.mymemoapproom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.seungho.android.myapplication.mymemoapproom.Entity.RoomMemo
import com.seungho.android.myapplication.mymemoapproom.Room.RoomHelper
import com.seungho.android.myapplication.mymemoapproom.databinding.ActivityMainBinding
import com.seungho.android.myapplication.myroomMemoapp.adapter.MemoAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    var roomHelper: RoomHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        roomHelper = Room.databaseBuilder(applicationContext, RoomHelper::class.java, "room_memo")
            .build()

        val adapter = MemoAdapter()
        adapter.roomHelper = roomHelper
        GlobalScope.launch(Dispatchers.IO) {
            adapter.listData.addAll(roomHelper?.roomMemoDao()?.getAll()?: listOf())
        }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        binding.btnCreateMemo.setOnClickListener {
            val intent = Intent(this, CreateMeMoActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            startActivity(intent)
        }

        val title = intent.getStringExtra("title")?: ""
        val content = intent.getStringExtra("content") ?: ""
        val time = intent.getStringExtra("date")?: ""

        if (title.isNotEmpty()) {
            val memo = RoomMemo(null, title, content, time)
            GlobalScope.launch(Dispatchers.IO) {
                roomHelper?.roomMemoDao()?.insert(memo)
                adapter.listData.clear()
                adapter.listData.addAll(roomHelper?.roomMemoDao()?.getAll() ?: listOf())
                adapter.notifyDataSetChanged()
            }
        }

        val editNo = intent.getStringExtra("editNo")?.toLong()
        val editTitle = intent.getStringExtra("editTitle")?: ""
        val editContent = intent.getStringExtra("editContent")?: ""
        val editTime = intent.getStringExtra("editDate")?: ""

        if (editTitle.isNotEmpty()) {
            val editMemo = RoomMemo(editNo, editTitle, editContent, editTime)
            GlobalScope.launch(Dispatchers.IO) {
                roomHelper?.roomMemoDao()?.insert(editMemo)
                adapter.listData.clear()
                adapter.listData.addAll(roomHelper?.roomMemoDao()?.getAll() ?: listOf())
                adapter.notifyDataSetChanged()
            }
        }
    }
}