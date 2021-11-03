package com.example.e_commerceapplication

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import com.example.e_commerceapplication.roomdb.product.ProductEntity
import com.example.e_commerceapplication.roomdb.product.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_add.*
import java.io.ByteArrayOutputStream

@AndroidEntryPoint
class AddActivity : AppCompatActivity() {

    companion object {
        private const val RequestPermissionCode = 1
    }

    private val productViewModel: ProductViewModel by viewModels()

    val OS = Integer.valueOf(android.os.Build.VERSION.SDK)
    var imagePath : Any = ""
    val PREF_DATA = "user_room"
    lateinit var sp : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        setTitle(R.string.add_screen)

        val actionbar: ActionBar? = supportActionBar
        actionbar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }

        sp = getSharedPreferences(PREF_DATA, MODE_PRIVATE)
        val currentId = sp.getInt("userid", 0)
        add_name.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("TAG", "beforeTextChanged: ")
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("TAG", "onTextChanged: ")
            }
            override fun afterTextChanged(p0: Editable?) {
                if (p0.toString().isNotBlank()) {
                    add_name_layout.error = null
                }
            }
        })
        add_amount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("TAG", "beforeTextChanged: ")
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("TAG", "onTextChanged: ")
            }
            override fun afterTextChanged(p0: Editable?) {
                if (p0.toString().isNotBlank()) {
                    add_amount_layout.error = null
                }
            }
        })
        add_description.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("TAG", "beforeTextChanged: ")
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("TAG", "onTextChanged: ")
            }
            override fun afterTextChanged(p0: Editable?) {
                if (p0.toString().isNotBlank()) {
                    add_description_layout.error = null
                }
            }
        })
        add_category.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("TAG", "beforeTextChanged: ")
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("TAG", "onTextChanged: ")
            }
            override fun afterTextChanged(p0: Editable?) {
                if (p0.toString().isNotBlank()) {
                    add_category_layout.error = null
                }
            }
        })

        add_iv.setOnClickListener {
            checkPermission()
        }

        add_item_btn.setOnClickListener {
            if (checkInput()){
                val image = add_iv.drawable
                val name = add_name.text.toString()
                val amount = add_amount.text.toString()
                val category = add_category.text.toString()
                val description = add_description.text.toString()

                if (image is android.graphics.drawable.VectorDrawable){
                    Toast.makeText(this, "PLease choose image.", Toast.LENGTH_SHORT).show()
                }else {
                    val productEntity =
                        ProductEntity(0, convertImage(add_iv.drawable), name, amount, description, category, currentId, 0)
                    productViewModel.insertProduct(productEntity)
                    Toast.makeText(this, "Product added successfully..", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun checkPermission() {
        val permissionArray = arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        ActivityCompat.requestPermissions(this, permissionArray, RequestPermissionCode)
    }

    private fun selectImage() {
        val options = arrayOf<CharSequence>("Take Photo", "Choose from Gallery", "Cancel")
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Add Photo!")
        builder.setItems(options, DialogInterface.OnClickListener { dialog, item ->
            when {
                options[item] == "Take Photo" -> {
                    if (OS > 26 ){
                        Toast.makeText(this, "$OS", Toast.LENGTH_SHORT).show()
                        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI.toString())
//                    intent.putExtra("circleCrop", "true")
                        intent.putExtra("crop", true)
                        intent.putExtra("aspectX", 0)
                        intent.putExtra("aspectY", 0)
                        intent.putExtra("outputX", 100)
                        intent.putExtra("outputY", 100)
                        intent.putExtra("return-data", true)
                        startActivityForResult(intent, 1)
                    }else {
                        Toast.makeText(this, "$OS", Toast.LENGTH_SHORT).show()
                        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI.toString())
//                    intent.putExtra("circleCrop", "true")
                        intent.putExtra("crop", "true")
                        intent.putExtra("aspectX", 0)
                        intent.putExtra("aspectY", 0)
                        intent.putExtra("outputX", 100)
                        intent.putExtra("outputY", 100)
                        intent.putExtra("return-data", true)
                        startActivityForResult(intent, 1)
                    }
                }
                options[item] == "Choose from Gallery" -> {
                    val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    startActivityForResult(intent, 2)
                }
                options[item] == "Cancel" -> {
                    dialog.dismiss()
                }
            }
        })
        builder.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK){
            when (requestCode) {
                1 -> {
                    Glide.with(this)
                        .load(data!!.extras!!.get("data"))
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .into(add_iv)
                    imagePath = data!!.extras!!.get("data")!!
                }
                2 -> {
                    val selectedImage: Uri = data!!.data!!
                    add_iv.setImageURI(selectedImage)
                    imagePath = data!!.data!!
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == RequestPermissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED && grantResults[2] == PackageManager.PERMISSION_GRANTED){
                selectImage()
            } else {
                Toast.makeText(this, "Permission Denied..!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkInput(): Boolean {
        var valid : Boolean = false

        when {
            add_name.text!!.trim().toString().isEmpty() -> {
                add_name_layout.error = "Name required.!"
            }
            add_amount.text!!.trim().toString().isEmpty() -> {
                add_amount_layout.error = "Amount required.!"
            }
            add_category.text!!.trim().toString().isEmpty() -> {
                add_category_layout.error = "Category required.!"
            }
            add_description.text!!.trim().toString().isEmpty() -> {
                add_description_layout.error = "Description required.!"
            }
            else -> {
                valid = true
            }
        }
        return valid
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