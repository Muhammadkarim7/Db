package com.example.db.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.db.databinding.RvItemBinding
import com.example.db.models.Contact

class MyRvAdapter(val list:ArrayList<Contact>, val rvAction:RvAction):RecyclerView.Adapter<MyRvAdapter.Vh>() {

    inner class Vh(val rvItem:RvItemBinding):ViewHolder(rvItem.root) {
        fun onBind(contact:Contact){
            rvItem.name.text=contact.name
            rvItem.number.text=contact.number
            rvItem.delete.setOnClickListener {
                rvAction.rvDeleteClick(contact)
            }
            rvItem.edit.setOnClickListener {
                rvAction.rvEditClick(contact)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(RvItemBinding.inflate(LayoutInflater.from(parent.context),parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position])
    }
    interface RvAction{
        fun rvDeleteClick(contact: Contact)
        fun rvEditClick(contact: Contact)

    }
}