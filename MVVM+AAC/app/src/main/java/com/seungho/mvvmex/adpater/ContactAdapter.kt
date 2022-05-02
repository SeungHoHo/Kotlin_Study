package com.seungho.mvvmex.adpater

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.seungho.mvvmex.databinding.ItemContactBinding
import com.seungho.mvvmex.room.Contact

class ContactAdapter(val contactItemClick: (Contact) -> Unit, val contactItemLongClick: (Contact) -> Unit)
    : RecyclerView.Adapter<ContactAdapter.ViewHolder>(){
        private var contacts: List<Contact> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactAdapter.ViewHolder {
        return ViewHolder(ItemContactBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ContactAdapter.ViewHolder, position: Int) {
        holder.bind(contacts[position])
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    inner class ViewHolder(private val binding : ItemContactBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(contact : Contact) {
            binding.initialTextView.text = contact.initial.toString()
            binding.itemNameTextView.text = contact.name
            binding.itemNumberTextView.text = contact.number

            binding.root.setOnClickListener {
                contactItemClick(contact)
            }

            binding.root.setOnLongClickListener {
                contactItemLongClick(contact)
                true
            }
        }
    }

    fun setContacts(contacts: List<Contact>) {
        this.contacts = contacts
        notifyDataSetChanged()
    }
}