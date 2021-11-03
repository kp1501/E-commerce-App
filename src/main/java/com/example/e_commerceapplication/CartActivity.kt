package com.example.e_commerceapplication

import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import com.example.e_commerceapplication.adapter.CartAdapter
import com.example.e_commerceapplication.roomdb.cart.CartEntity
import com.example.e_commerceapplication.roomdb.cart.CartViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_cart.*
import java.io.ByteArrayOutputStream

@AndroidEntryPoint
class CartActivity : AppCompatActivity() {

    val PREF_DATA = "user_room"
    lateinit var sp : SharedPreferences

    private val cartViewModel : CartViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        setTitle(R.string.cart_screen)

        val actionbar: ActionBar? = supportActionBar
        actionbar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }

        sp = getSharedPreferences(PREF_DATA, AppCompatActivity.MODE_PRIVATE)
        val adapter = CartAdapter(cartViewModel)
        cart_recycler.adapter = adapter
        val address = sp.getString("address","")
        val currentId = sp.getInt("userid", 0)
        if (address != ""){
            cart_address.text = address
        }

        var total = 0

        val cartEntity = CartEntity(0,convertImage(getDrawable(R.drawable.shirt)),"","","","","",currentId,1,1)
        cartViewModel.allProducts(cartEntity).observe(this) {
            it.let { 
                adapter.submitList(it)
                total = 0
                for (i in it.indices){
                    total += it[i].cart_total.toInt()
                }
                cart_amount.text = total.toString()
            }
        }

    }
    private fun convertImage(drawable: Drawable?): ByteArray? {
        val bitmap = (drawable as BitmapDrawable).bitmap
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.WEBP, 25, stream)
        return stream.toByteArray()
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