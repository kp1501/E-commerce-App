package com.example.e_commerceapplication

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import com.example.e_commerceapplication.adapter.ProductAdapter
import com.example.e_commerceapplication.roomdb.cart.CartViewModel
import com.example.e_commerceapplication.roomdb.product.ProductEntity
import com.example.e_commerceapplication.roomdb.product.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_wishlist.*
import java.io.ByteArrayOutputStream

@AndroidEntryPoint
class WishlistActivity : AppCompatActivity() {

    private val productViewModel: ProductViewModel by viewModels()
    private val cartViewModel : CartViewModel by viewModels()

    val PREF_DATA = "user_room"
    lateinit var sp : SharedPreferences
    var currentId : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wishlist)
        setTitle(R.string.wishlist_screen)

        val actionbar: ActionBar? = supportActionBar
        actionbar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }

        sp = getSharedPreferences(PREF_DATA, MODE_PRIVATE)
        currentId = sp.getInt("userid",0)

        val adapter = ProductAdapter(cartViewModel,this,productViewModel)
        wishlist_recycler.adapter = adapter

        val suggestedAdapter = ProductAdapter(cartViewModel,this,productViewModel)
        wishlist_suggested_recycler.adapter = suggestedAdapter

        val productEntity = ProductEntity(0,convertImage(getDrawable(R.drawable.tshirt)),"T-shirt","1900","T-shirt for men","t-shirt",currentId,1)
        productViewModel.allWishList(productEntity).observe(this) {
            it.let { adapter.submitList(it)}
        }
        val productSuggested = ProductEntity(0,convertImage(getDrawable(R.drawable.tshirt)),"T-shirt","1900","T-shirt for men","t-shirt",currentId,0)
        productViewModel.allWishList(productSuggested).observe(this) {
            it.let { suggestedAdapter.submitList(it) }
        }
        wishlist_cart_tv.setOnClickListener {
            startActivity(Intent(this,CartActivity::class.java))
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