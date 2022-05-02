package com.seungho.android.myapplication.myroomMemoapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.seungho.android.myapplication.mymemoapproom.EditMemoActivity
import com.seungho.android.myapplication.mymemoapproom.Entity.RoomMemo
import com.seungho.android.myapplication.mymemoapproom.Room.RoomHelper
import com.seungho.android.myapplication.mymemoapproom.databinding.ItemMemoBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MemoAdapter: RecyclerView.Adapter<MemoAdapter.Holder>() {
    var roomHelper: RoomHelper? = null
    var listData = mutableListOf<RoomMemo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemMemoBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return Holder(binding)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val roomMemo = listData.get(position)
        holder.setRoomMemo(roomMemo)
    }

    inner class Holder(val binding: ItemMemoBinding) : RecyclerView.ViewHolder(binding.root) {
        var mRoomMemo: RoomMemo? = null
            init {
                binding.deleteButton.setOnClickListener {
                        GlobalScope.launch {
                            roomHelper?.roomMemoDao()?.delete(mRoomMemo!!)
                            listData.remove(mRoomMemo)
                        }
                        notifyDataSetChanged()
                }
            }

        fun setRoomMemo(roomMemo: RoomMemo) {
            binding.captionTextView.text = roomMemo.caption
            binding.contentTextView.text = roomMemo.content
            binding.timeTextView.text = roomMemo.datetime

            this.mRoomMemo = roomMemo

            binding.editButton.setOnClickListener {
                val intent = Intent(itemView.context, EditMemoActivity::class.java)
                intent.putExtra("1", roomMemo.no.toString())
                intent.putExtra("2", binding.captionTextView.text)
                intent.putExtra("3", binding.contentTextView.text)
                intent.putExtra("4", binding.timeTextView.text)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                ContextCompat.startActivity(itemView.context, intent, null)

            }


        }
    }
}