package com.example.e_commerceapplication

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.e_commerceapplication.adapter.ProductAdapter
import com.example.e_commerceapplication.roomdb.UserEntity
import com.example.e_commerceapplication.roomdb.cart.CartEntity
import com.example.e_commerceapplication.roomdb.cart.CartViewModel
import com.example.e_commerceapplication.roomdb.product.ProductEntity
import com.example.e_commerceapplication.roomdb.product.ProductViewModel
import com.example.e_commerceapplication.viewmodel.LoginViewModel
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.header_layout.view.*
import kotlinx.coroutines.*
import java.io.ByteArrayOutputStream

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val loginViewModel: LoginViewModel by viewModels()
    private val productViewModel: ProductViewModel by viewModels()
    private val cartViewModel : CartViewModel by viewModels()

    private lateinit var mDrawerLayout: DrawerLayout
    val PREF_DATA = "user_room"
    lateinit var sp : SharedPreferences
    lateinit var editor : SharedPreferences.Editor
    var currentId : Int = 0
    var currentName : String = ""
    var currentEmail : String = ""
    var cartQty = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mDrawerLayout = findViewById(R.id.drawer_layout)
        sp = getSharedPreferences(PREF_DATA, MODE_PRIVATE)
        editor = sp.edit()
        val adapter = ProductAdapter(cartViewModel,this,productViewModel)
        main_recycler.adapter = adapter
        val user = UserEntity(0,"",sp.getString("email","").toString(),"")
        val first = sp.getBoolean("first", false)
        currentName = sp.getString("name","").toString()
        currentEmail = sp.getString("email","").toString()

        val id = loginViewModel.getId(user)
        currentId = id

        editor.putInt("userid", currentId)
        editor.commit()
        Toast.makeText(this@MainActivity, "$currentId", Toast.LENGTH_SHORT).show()

        if (first){
            var product = ProductEntity(0,convertImage(AppCompatResources.getDrawable(this,R.drawable.shoe)),"Shoes","1100","Shoes for men","shoes",currentId,0)
            productViewModel.insertProduct(product)
            product = ProductEntity(0,convertImage(AppCompatResources.getDrawable(this,R.drawable.shoes)),"Shoes","1600","Shoes for men","shoes",currentId,0)
            productViewModel.insertProduct(product)
            product = ProductEntity(0,convertImage(AppCompatResources.getDrawable(this,R.drawable.shirt)),"Shirt","1300","Shirt for men","shirt",currentId,0)
            productViewModel.insertProduct(product)
            product = ProductEntity(0,convertImage(AppCompatResources.getDrawable(this,R.drawable.tech)),"Tech","1200","Tech for men","tech",currentId,0)
            productViewModel.insertProduct(product)
            product = ProductEntity(0,convertImage(AppCompatResources.getDrawable(this,R.drawable.tshirt)),"T-shirt","1900","T-shirt for men","t-shirt",currentId,0)
            productViewModel.insertProduct(product)
            editor.putBoolean("first", false)
            editor.commit()
        }

        val productEntity = ProductEntity(0,convertImage(AppCompatResources.getDrawable(this,R.drawable.tshirt)),"T-shirt","1900","T-shirt for men","t-shirt",currentId,0)
        productViewModel.allProducts(productEntity).observe(this@MainActivity) {
            it.let { adapter.submitList(it)}
        }

        val cartEntity = CartEntity(0,convertImage(AppCompatResources.getDrawable(this,R.drawable.shirt)),"","","","","",currentId,1,1)
        cartViewModel.allProducts(cartEntity).observe(this@MainActivity) {
            it.let {
                cartQty = it.size
                invalidateOptionsMenu()
            }
        }

        val actionbar: ActionBar? = supportActionBar
        actionbar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.menu_foreground)
        }

        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener {
            it.isChecked = true
            mDrawerLayout.closeDrawers()

            when (it.itemId){
                R.id.nav_add_item -> {
                    startActivity(Intent(this@MainActivity, AddActivity::class.java))
                }
                R.id.nav_wishlist -> {
                    startActivity(Intent(this@MainActivity, WishlistActivity::class.java))
                }
                R.id.nav_address -> {
                    startActivity(Intent(this@MainActivity, AddressActivity::class.java))
                }
                R.id.nav_logout -> {
                    editor.putString("email", "")
                    editor.putBoolean("first", false)
                    editor.commit()
                    startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                    finish()
                }
            }
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.cart_item, menu)
        val menuItem = menu.findItem(R.id.cart_item)
        val actionView = menuItem.actionView
        val cartText = actionView.findViewById<TextView>(R.id.cart_badge_tv)
        cartText.text = cartQty.toString()
        actionView.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.cart_item -> {
                startActivity(Intent(this, CartActivity::class.java))
                true
            }
            android.R.id.home -> {
                mDrawerLayout.openDrawer(GravityCompat.START)
                mDrawerLayout = findViewById(R.id.drawer_layout)
                mDrawerLayout.header_tv1.text = currentName
                mDrawerLayout.header_tv2.text = currentEmail
                true
            }else -> super.onOptionsItemSelected(item)
        }
    }

    private fun convertImage(drawable: Drawable?): ByteArray? {
        val bitmap = (drawable as BitmapDrawable).bitmap
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.WEBP, 25, stream)
        return stream.toByteArray()
    }
}