package com.seungho.android.myapplication.mymemoapproom

import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.seungho.android.myapplication.mymemoapproom.databinding.ActivityEditMemoBinding
import com.seungho.android.myapplication.myroomMemoapp.adapter.MemoAdapter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class EditMemoActivity: AppCompatActivity() {

    val adapter = MemoAdapter()
    private val binding by lazy { ActivityEditMemoBinding.inflate(layoutInflater) }

    val positiveButtonClick = { dialogInterface: DialogInterface, i: Int ->
        finish()
    }

    val negativeButtonClick = { dialogInterface: DialogInterface, i: Int -> }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val no = intent.getStringExtra("1")
        binding.captionMemoEditTextView.setText("${intent.getStringExtra("2")}")
        binding.contentEditTextView.setText("${intent.getStringExtra("3")}")

        binding.backPressButton.setOnClickListener {
            if (binding.captionMemoEditTextView.text.isNotEmpty() || binding.contentEditTextView.text.isNotEmpty()) {
                val builder = AlertDialog.Builder(this)
                    .setTitle("수정 취소")
                    .setMessage("수정하지 않고 돌아가시겠습니까?")
                    .setPositiveButton("네", positiveButtonClick )
                    .setNegativeButton("아니요", negativeButtonClick)
                    .show()
            } else {
                finish()
            }
        }


        binding.editButton.setOnClickListener {
            if (binding.captionMemoEditTextView.text.isEmpty() || binding.contentEditTextView.text.isEmpty()) {
                Toast.makeText(this, "제목과 내용을 모두 입력해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val editTitle = binding.captionMemoEditTextView.text.toString()
            val editContent = binding.contentEditTextView.text.toString()
            val sdf = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")
            val editNow = sdf.format(formatter).toString()

            Toast.makeText(this, "메모를 수정하였습니다.", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("editNo", no)
            intent.putExtra("editTitle", editTitle)
            intent.putExtra("editContent", editContent)
            intent.putExtra("editDate", editNow)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
        }
    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
            .setTitle("수정 취소")
            .setMessage("수정하지 않고 돌아가시겠습니까?")
            .setPositiveButton("네", positiveButtonClick)
            .setNegativeButton("아니요", negativeButtonClick)
            .show()
    }
}