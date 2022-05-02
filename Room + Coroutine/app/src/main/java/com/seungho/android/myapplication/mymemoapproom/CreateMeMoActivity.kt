package com.seungho.android.myapplication.mymemoapproom

import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.seungho.android.myapplication.mymemoapproom.databinding.ActivityCreateMemoBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CreateMeMoActivity: AppCompatActivity() {

    private val binding by lazy { ActivityCreateMemoBinding.inflate(layoutInflater) }

    val positiveButtonClick = { dialogInterface: DialogInterface , i: Int ->
        finish()
    }

    val negativeButtonClick = { dialogInterface: DialogInterface , i: Int ->

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.saveButton.setOnClickListener {
            if (binding.captionMemoEditTextView.text.isEmpty() || binding.contentEditTextView.text.isEmpty()) {
                Toast.makeText(this, "제목과 내용을 모두 입력해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val createTitle = binding.captionMemoEditTextView.text.toString()
            val createContent = binding.contentEditTextView.text.toString()
            val sdf = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")
            val createNow = sdf.format(formatter).toString()

            Toast.makeText(this, "메모를 작성하였습니다.", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("title", createTitle)
            intent.putExtra("content", createContent)
            intent.putExtra("date", createNow)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
        }

        binding.backPressButton.setOnClickListener {
            if (binding.captionMemoEditTextView.text.isNotEmpty() || binding.contentEditTextView.text.isNotEmpty()) {
                val builder = AlertDialog.Builder(this)
                    .setTitle("메모 삭제")
                    .setMessage("저장하지 않고 돌아가시겠습니까?")
                    .setPositiveButton("네", positiveButtonClick )
                    .setNegativeButton("아니요", negativeButtonClick)
                    .show()
            } else {
                finish()
            }
        }

    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
            .setTitle("메모 삭제")
            .setMessage("생성하지 않고 돌아가시겠습니까?")
            .setPositiveButton("네", positiveButtonClick)
            .setNegativeButton("아니요", negativeButtonClick)
            .show()
    }
}