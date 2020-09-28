package com.example.talk_o.app.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.talk_o.R
import com.example.talk_o.app.ui.mainapp.MainActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        title = "Login"

        auth = FirebaseAuth.getInstance()

        btnLogin.setOnClickListener {
            val username = txtEmail.text.toString()
            val password = txtPassword.text.toString()

            if (username.isEmpty() || password.isEmpty()){
                Toast.makeText(this, "Preencha os campos", Toast.LENGTH_LONG).show()
            }
            else if (password.length < 6){
                Toast.makeText(this, "A senha precisa ter no minimo seis caracteres", Toast.LENGTH_LONG).show()
            }
            else{
                auth.signInWithEmailAndPassword(username, password).addOnCompleteListener {
                    if (it.isSuccessful){
                        intent = Intent(this, MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(intent)
                        finish()
                    }
                    else{
                        Toast.makeText(this, "Falha ao fazer Login", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}