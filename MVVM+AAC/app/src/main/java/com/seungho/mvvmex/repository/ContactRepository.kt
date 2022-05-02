package com.seungho.mvvmex.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.seungho.mvvmex.AppDatabase
import com.seungho.mvvmex.room.Contact

class ContactRepository(application: Application) {

    private val contactDatabase = AppDatabase.getInstance(application)!!
    private val contactDao = contactDatabase.contactDao()
    private val contacts: LiveData<List<Contact>> = contactDao.getAll()

    fun getAll(): LiveData<List<Contact>> {
        return contacts
    }

    fun insert(contact: Contact) {
        try {
            val thread = Thread(kotlinx.coroutines.Runnable {
                contactDao.insert(contact) })
                thread.start()
        } catch (e : Exception) { }
    }

    fun delete(contact: Contact) {
        try {
            val thread = Thread(kotlinx.coroutines.Runnable {
                contactDao.delete(contact)
            })
            thread.start()
        } catch (e : Exception) { }
    }

}