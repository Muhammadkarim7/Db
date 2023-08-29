package com.example.db.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.content.contentValuesOf
import com.example.db.models.Contact

class MyDbHelper(context: Context):SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION), MyDbInterface {

    companion object{
        val DB_NAME="contact_db"
        val DB_VERSION=1

        val TABLE_NAME="contact_table"
        val CONTACT_ID="id"
        val CONTACT_NAME="name"
        val CONTACT_NUMBER="number"

    }

    override fun onCreate(p0: SQLiteDatabase?) {
        val query="create table $TABLE_NAME($CONTACT_ID integer not null primary key autoincrement, $CONTACT_NAME text not null, $CONTACT_NUMBER text not null)"
        p0?.execSQL(query)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }

    override fun addConcact(contact: Contact) {
        val database=writableDatabase
        val contentValues=ContentValues()
        contentValues.put(CONTACT_NAME,contact.name)
        contentValues.put(CONTACT_NUMBER,contact.number)
        database.insert(TABLE_NAME, null, contentValues)
        database.close()
    }

    override fun getAllContacts(): List<Contact> {
        val database=readableDatabase
        val list=ArrayList<Contact>()
        val query="select * from $TABLE_NAME"
        val cursor=database.rawQuery(query, null)

        if (cursor.moveToFirst()){
            do {
                val contact=Contact(
                    id = cursor.getInt(0),
                    name = cursor.toString(),
                    number = cursor.toString()
                )
                list.add(contact)
            }while (cursor.moveToNext())
        }
        return list
    }

    override fun editContact(contact: Contact) {
        val database=writableDatabase
        val contactValues= ContentValues()
        contactValues.put(CONTACT_ID, contact.id)
        contactValues.put(CONTACT_NAME, contact.name)
        contactValues.put(CONTACT_NUMBER, contact.number)

        database.update(TABLE_NAME, contactValues, "$CONTACT_ID=?", arrayOf(contact.id.toString()))
    }

    override fun deleteContact(contact: Contact) {
        val database=writableDatabase
        database.delete(TABLE_NAME, "$CONTACT_ID=?", arrayOf(contact.id.toString()))
    }
}