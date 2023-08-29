package com.example.db.db

import com.example.db.models.Contact

interface MyDbInterface {

    fun addConcact(contact: Contact)
    fun getAllContacts():List<Contact>

    fun editContact(contact: Contact)
    fun deleteContact(contact: Contact)
}