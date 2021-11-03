package com.example.e_commerceapplication

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setTitle(R.string.detail_screen)

        val actionbar: ActionBar? = supportActionBar
        actionbar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }

        val intent = intent
        val image = intent.getByteArrayExtra("image")
        val name = intent.getStringExtra("name")
        val amount = intent.getStringExtra("amount")
        val category = intent.getStringExtra("category")
        val description = intent.getStringExtra("description")

        detail_iv.setImageBitmap(getImage(image))
        detail_name.text = name
        detail_amount.text = amount
        detail_category.text = category
        detail_description.text = description
    }

    private fun getImage(byteArray: ByteArray?): Bitmap {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray!!.size)
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