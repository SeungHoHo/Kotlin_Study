package com.seungho.mvvmex.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.seungho.mvvmex.repository.ContactRepository
import com.seungho.mvvmex.room.Contact

class ContactViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ContactRepository(application)
    private val contacts = repository.getAll()

    fun getAll() : LiveData<List<Contact>> {
        return this.contacts
    }

    fun insert(contact: Contact) {
        repository.insert(contact)
    }

    fun delete(contact: Contact) {
        repository.delete(contact)
    }
}