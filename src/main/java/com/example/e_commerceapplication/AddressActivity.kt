package com.example.e_commerceapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import com.example.e_commerceapplication.adapter.AddressAdapter
import com.example.e_commerceapplication.roomdb.address.AddressEntity
import com.example.e_commerceapplication.roomdb.address.AddressViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_address.*

@AndroidEntryPoint
class AddressActivity : AppCompatActivity() {

    private val addressViewModel: AddressViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address)
        setTitle(R.string.address_screen)

        val actionbar: ActionBar? = supportActionBar
        actionbar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }

        val adapter = AddressAdapter(addressViewModel,this)
        address_recycler.adapter = adapter

        addressViewModel.allAddress.observe(this) {
            it.let {
                adapter.submitList(it)
            }
        }

        address_fab_fragment.setOnClickListener {
            startActivity(Intent(this, CurrentActivity::class.java))
        }

        BottomSheetBehavior.from(bottom_sheet).apply {
            peekHeight = 0
            address_fab.setOnClickListener {
                this.state = BottomSheetBehavior.STATE_EXPANDED

                address_add_btn.setOnClickListener {
                    val address = address_text.text.toString()
                    if (inputCheck(address)){
                        val addressEntity = AddressEntity(0,address_text.text.toString())
                        addressViewModel.insertAddress(addressEntity)
                        this.state = BottomSheetBehavior.STATE_COLLAPSED
                        Toast.makeText(this@AddressActivity, "Address added successfully", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this@AddressActivity, "Please enter address..!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun inputCheck(address: String): Boolean {
        return !(TextUtils.isEmpty(address) && address.isBlank())
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {
                this.finish()
                true
            }else -> super.onOptionsItemSelected(item)
        }
    }
}