package com.example.talk_o.app.ui.startup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.talk_o.R
import com.example.talk_o.app.ui.login.LoginActivity
import com.example.talk_o.app.ui.mainapp.MainActivity
import com.example.talk_o.app.ui.register.RegisterActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity : AppCompatActivity() {

    override fun onStart() {
        super.onStart()
        FirebaseAuth.getInstance().currentUser?.let {
            intent = Intent( this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }?:run {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        btnLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}