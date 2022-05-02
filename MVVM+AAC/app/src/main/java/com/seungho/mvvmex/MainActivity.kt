package com.seungho.mvvmex

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.seungho.mvvmex.adpater.ContactAdapter
import com.seungho.mvvmex.databinding.ActivityMainBinding
import com.seungho.mvvmex.room.Contact
import com.seungho.mvvmex.viewmodel.ContactViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var contactViewModel: ContactViewModel
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val adapter = ContactAdapter({ contact ->
            val intent = Intent(this, InputActivity::class.java)
            intent.putExtra(InputActivity.EXTRA_CONTACT_NAME, contact.name)
            intent.putExtra(InputActivity.EXTRA_CONTACT_NUMBER, contact.number)
            intent.putExtra(InputActivity.EXTRA_CONTACT_ID, contact.id)
            startActivity(intent)
        }, { contact ->
            deleteDialog(contact)
        })

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.setHasFixedSize(true)

        contactViewModel = ViewModelProvider(this).get(ContactViewModel::class.java)
        contactViewModel.getAll().observe(this, Observer<List<Contact>> { contracts ->
            adapter.setContacts(contracts!!)
        })

        binding.btnAdd.setOnClickListener {
            val intent = Intent(this, InputActivity::class.java)
            startActivity(intent)
        }
    }

    private fun deleteDialog(contact: Contact) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("선택한 연락처 삭제")
            .setNegativeButton("아니요") { _, _ -> }
            .setPositiveButton("네") { _, _ ->
                contactViewModel.delete(contact)
            }
        builder.show()
    }
}