package com.example.e_commerceapplication

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.activity.viewModels
import com.example.e_commerceapplication.roomdb.UserEntity
import com.example.e_commerceapplication.viewmodel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_register.*

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {

    private val registerViewModel: RegisterViewModel by viewModels()

    val PREF_DATA = "user_room"
    lateinit var sp : SharedPreferences
    lateinit var editor : SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        setTitle(R.string.register_screen)

        sp = getSharedPreferences(PREF_DATA, MODE_PRIVATE)

        register_email.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("TAG", "beforeTextChanged: ")
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("TAG", "onTextChanged: ")
            }
            override fun afterTextChanged(p0: Editable?) {
                if (Patterns.EMAIL_ADDRESS.matcher(p0.toString()).matches()){
                    email_layout_r.error = null
                }
            }
        })
        register_pass.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("TAG", "beforeTextChanged: ")
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("TAG", "onTextChanged: ")
            }
            override fun afterTextChanged(p0: Editable?) {
                if (p0.toString().length >= 6){
                    pass_layout_r.error = null
                }
            }
        })
        register_name.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("TAG", "beforeTextChanged: ")
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("TAG", "onTextChanged: ")
            }
            override fun afterTextChanged(p0: Editable?) {
                if (p0.toString().isNotBlank()){
                    name_layout_r.error = null
                }
            }
        })

        register_btn.setOnClickListener {
            if (checkInput()){
                val name = register_name.text.toString()
                val email = register_email.text.toString()
                val pass = register_pass.text.toString()

                insertDb(name, email,pass)
            }
        }

        register_text.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
        }
    }

    private fun insertDb(name: String, email: String, pass: String) {
        val user = UserEntity(0, name, email, pass)
        registerViewModel.insert(user)
        Toast.makeText(this, "User Registered..!", Toast.LENGTH_SHORT).show()
        editor = sp.edit()
        editor.putString("email", email)
        editor.putString("name", name)
        editor.putBoolean("first", true)
        editor.commit()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun checkInput(): Boolean {
        var valid : Boolean = false

        if (register_email.text!!.trim().toString().isEmpty()){
            email_layout_r.error = "Please enter email"
        }else if (!Patterns.EMAIL_ADDRESS.matcher(register_email.text.toString()).matches()){
            email_layout_r.error = "Please enter valid email"
        }else if (register_pass.text!!.trim().toString().isEmpty()){
            pass_layout_r.error = "Please enter password"
        }else if (register_pass.text!!.trim().toString().length < 6) {
            pass_layout_r.error = "Password must be more then 6 character"
        }else if(register_name.text!!.trim().toString().isEmpty()){
            name_layout_r.error = "Please enter name"
        }else {
            valid = true
        }
        return valid
    }
}