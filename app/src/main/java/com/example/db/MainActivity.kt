package com.example.db

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.db.adapter.MyRvAdapter
import com.example.db.databinding.ActivityMainBinding
import com.example.db.databinding.DialogItemBinding
import com.example.db.db.MyDbHelper
import com.example.db.models.Contact

class MainActivity : AppCompatActivity(), MyRvAdapter.RvAction {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var myRvAdapter: MyRvAdapter
    private lateinit var list:ArrayList<Contact>
    private lateinit var myDbHelper:MyDbHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        myDbHelper=MyDbHelper(this)
        list= ArrayList()
        list.addAll(myDbHelper.getAllContacts())
        myRvAdapter= MyRvAdapter(list, this)
        binding.addBtn.setOnClickListener {

            val dialog=AlertDialog.Builder(this)
                .create()
            val dialogItemBinding=DialogItemBinding.inflate(layoutInflater)
            dialog.setView(dialogItemBinding.root)

            dialogItemBinding.save.setOnClickListener {
                val contact=Contact(
                    name = dialogItemBinding.name.text.toString(),
                    number = dialogItemBinding.number.text.toString()
                )
                list.add(contact)
                myDbHelper.addConcact(contact)
                myRvAdapter.notifyDataSetChanged()
                dialog.cancel()
            }
            dialog.show()
        }
    }

    override fun rvDeleteClick(contact: Contact) {
        myDbHelper.deleteContact(contact)
        list.remove(contact)
        myRvAdapter.notifyDataSetChanged()
    }

    override fun rvEditClick(contact: Contact) {
        val dialog=AlertDialog.Builder(this).create()
        val dialogItem=DialogItemBinding.inflate(layoutInflater)
        dialog.setView(dialogItem.root)
        dialogItem.name.setText(contact.name)
        dialogItem.number.setText(contact.number)

        dialogItem.save.setOnClickListener {
            val contact=Contact(
                id = contact.id,
                name = dialogItem.name.text.toString(),
                number = dialogItem.number.text.toString()
            )
            myDbHelper.editContact(contact)
            list.clear()
            list.addAll(myDbHelper.getAllContacts())
            myRvAdapter.notifyDataSetChanged()

            dialog.dismiss()
        }
        dialog.show()
    }
}