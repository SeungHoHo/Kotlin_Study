package com.seungho.livedataex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.seungho.livedataex.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.viewmodel = viewModel

        // observe live data
        viewModel.countText.observe(this, Observer {
            // 여기서 it은 LiveData로 선언된 countText 가 변경되었을 때 전달되는 값(String 형)
            binding.countTextView.text = it
        })

        viewModel.init()

        //UI Update
        binding.btnIncreaseCount.setOnClickListener {
            viewModel.clickButton()
        }
    }
}