package com.seungho.android.myapplication.puppyrecyclerview

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import com.seungho.android.myapplication.puppyrecyclerview.R
import com.seungho.android.myapplication.puppyrecyclerview.ui.theme.PuppyRecyclerViewTheme

class ProfileActivity : AppCompatActivity() {

    private val puppy: Puppy by lazy {
        intent?.getSerializableExtra(PUPPY_ID) as Puppy
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PuppyRecyclerViewTheme {
                ProfileScreen(puppy = puppy)
            }
        }
    }

    companion object {
        private const val PUPPY_ID = "puppy_id"
        fun newIntent(context: Context, puppy: Puppy) =
            Intent(context, ProfileActivity::class.java).apply {
                putExtra(PUPPY_ID, puppy)
            }
    }
}



