package com.example.talk_o.app.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.talk_o.R
import com.example.talk_o.app.ui.mainapp.MainActivity
import com.example.talk_o.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var reference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        title = "Registrar"

        auth = FirebaseAuth.getInstance()
        reference = FirebaseDatabase.getInstance().reference



        btnRegister.setOnClickListener {
            val username = txtUsuario.text.toString()
            val email = txtEmail.text.toString()
            val password = txtPassword.text.toString()

            if (username.isEmpty() || email.isEmpty() || password.isEmpty()){
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_LONG).show()
            }
            else if (password.length < 6) {
                Toast.makeText(this, "A senha precisa ter no minimo seis caracteres", Toast.LENGTH_LONG).show()
            }
            else
                registerUser(username, email, password)
        }

    }

    private fun registerUser(username: String, email: String, password: String ){
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { it ->
            if(it.isSuccessful){
                val firebaseUser = auth.currentUser
                val userId = firebaseUser?.uid

                userId?.let {it1->
                    reference = FirebaseDatabase.getInstance().getReference("Users").child(
                        it1)
                }

                val user = User(userId.toString(), username, "default")
//                var hashMap = HashMap<String, String>()
//                hashMap["id"] = userId.toString()
//                hashMap["username"] = username
//                hashMap["imgURL"] = "default"

                reference.setValue(user).addOnCompleteListener {task ->
                    if (task.isSuccessful){
                        intent = Intent(this, MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        finish()
                    }
                }
            }else{
                Toast.makeText(this, "Falha ao criar Usuário", Toast.LENGTH_LONG).show()
            }
        }
    }
}