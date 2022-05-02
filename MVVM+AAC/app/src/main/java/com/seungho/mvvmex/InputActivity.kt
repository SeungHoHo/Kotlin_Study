package com.seungho.mvvmex

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.seungho.mvvmex.databinding.ActivityInputBinding
import com.seungho.mvvmex.room.Contact
import com.seungho.mvvmex.viewmodel.ContactViewModel

class InputActivity : AppCompatActivity() {

    private val binding by lazy { ActivityInputBinding.inflate(layoutInflater) }
    private lateinit var contactViewModel: ContactViewModel
    private var id: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        contactViewModel = ViewModelProvider(this).get(ContactViewModel::class.java)

        if (intent != null && intent.hasExtra(EXTRA_CONTACT_NAME) && intent.hasExtra(
                EXTRA_CONTACT_NUMBER) && intent.hasExtra(EXTRA_CONTACT_ID)) {
            binding.nameEditTextView.setText(intent.getStringExtra(EXTRA_CONTACT_NAME))
            binding.numberEditTextView.setText(intent.getStringExtra(EXTRA_CONTACT_NUMBER))
            id = intent.getLongExtra(EXTRA_CONTACT_ID, -1)
        }

        binding.btnDone.setOnClickListener {
            val name = binding.nameEditTextView.text.toString().trim()
            val number = binding.numberEditTextView.text.toString()

            if (name.isEmpty() || number.isEmpty()) {
                Toast.makeText(this, "이름이나 번호를 입력해주세요", Toast.LENGTH_SHORT).show()
            } else {
                val initial = name[0].toUpperCase()
                val contact = Contact(id, name, number, initial)
                contactViewModel.insert(contact)
                finish()
            }
        }
    }

    companion object {
        const val EXTRA_CONTACT_NAME = "EXTRA_CONTACT_NAME"
        const val EXTRA_CONTACT_NUMBER = "EXTRA_CONTACT_NUMBER"
        const val EXTRA_CONTACT_ID = "EXTRA_CONTACT_ID"
    }
}