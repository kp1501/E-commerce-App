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
import com.example.e_commerceapplication.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.*

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val loginViewModel: LoginViewModel by viewModels()

    val PREF_DATA = "user_room"
    lateinit var sp : SharedPreferences
    lateinit var editor : SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setTitle(R.string.login_screen)

        sp = getSharedPreferences(PREF_DATA, MODE_PRIVATE)
        val first = sp.getBoolean("first",true)
//        val name = sp.getString("name","")

        login_email.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("TAG", "beforeTextChanged: ")
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("TAG", "onTextChanged: ")
            }
            override fun afterTextChanged(p0: Editable?) {
                if (Patterns.EMAIL_ADDRESS.matcher(p0.toString()).matches()){
                    email_layout.error = null
                }
            }
        })
        login_pass.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("TAG", "beforeTextChanged: ")
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("TAG", "onTextChanged: ")
            }
            override fun afterTextChanged(p0: Editable?) {
                if (p0.toString().length >= 6){
                    pass_layout.error = null
                }
            }
        })

        login_text.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }

        login_btn.setOnClickListener {
            if (checkInput()){
                val email = login_email.text.toString()
                val pass = login_pass.text.toString()

                checkFromDb(email, pass, first)
            }
        }
    }

    private fun checkFromDb(email: String, pass: String, first: Boolean) {
        val user = UserEntity(0, "", email, pass)

        GlobalScope.launch(Dispatchers.Main) {
            val login = loginViewModel.getUser(user)
            withContext(Dispatchers.Main) {
                if (login != null){
                    editor = sp.edit()
                    editor.putString("email", login.userEmail)
                    editor.putString("name",login.userName)
                    if (first){
                        editor.putBoolean("first", true)
                    }else{
                        editor.putBoolean("first", false)
                    }
                    editor.commit()
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    finish()
                }else{
                    Toast.makeText(this@LoginActivity, "User not found..!", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun checkInput(): Boolean {
        var valid : Boolean = false

        if (login_email.text!!.trim().toString().isEmpty()){
            email_layout.error = "Please enter email"
        } else if (!Patterns.EMAIL_ADDRESS.matcher(login_email.text.toString()).matches()){
            email_layout.error = "Please enter valid email"
        } else if (login_pass.text!!.trim().toString().isEmpty()){
            pass_layout.error = "Please enter password"
        } else if (login_pass.text!!.trim().toString().length < 6) {
            pass_layout.error = "Password must be more then 6 character"
        } else {
            valid = true
        }
        return valid
    }
}