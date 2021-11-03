package com.example.e_commerceapplication.adapter

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerceapplication.R
import com.example.e_commerceapplication.roomdb.address.AddressEntity
import com.example.e_commerceapplication.roomdb.address.AddressViewModel

class AddressAdapter(private val addressViewModel: AddressViewModel,private val context: Context) :ListAdapter<AddressEntity, AddressAdapter.ViewHolder>(AddressComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressAdapter.ViewHolder {
        return ViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: AddressAdapter.ViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current,addressViewModel,context)
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private val address = itemView.findViewById<TextView>(R.id.address_item_text)
        private val setAddress = itemView.findViewById<TextView>(R.id.address_item_set)
        private val deleteAddress = itemView.findViewById<TextView>(R.id.address_item_remove)
        val PREF_DATA = "user_room"
        lateinit var sp : SharedPreferences
        lateinit var editor : SharedPreferences.Editor

        fun bind(current: AddressEntity,addressViewModel: AddressViewModel,context: Context) {

            sp = context.getSharedPreferences(PREF_DATA, AppCompatActivity.MODE_PRIVATE)
            editor = sp.edit()
            address.text = current.address

            setAddress.setOnClickListener {
                editor.putString("address", current.address)
                editor.commit()
                Toast.makeText(context, "Address set successfully.", Toast.LENGTH_SHORT).show()
            }

            deleteAddress.setOnClickListener {
                addressViewModel.deleteAddress(current)
            }
        }
        companion object{
            fun create(parent: ViewGroup): AddressAdapter.ViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.address_item, parent, false)
                return AddressAdapter.ViewHolder(view)
            }
        }
    }

    class AddressComparator : DiffUtil.ItemCallback<AddressEntity>() {
        override fun areItemsTheSame(oldItem: AddressEntity, newItem: AddressEntity): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: AddressEntity, newItem: AddressEntity): Boolean {
            return oldItem.address == newItem.address
        }
    }
}
