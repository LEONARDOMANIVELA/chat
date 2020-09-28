package com.example.talk_o.app.ui.message

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.talk_o.R
import com.example.talk_o.data.model.Message
import com.example.talk_o.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_message.*

class MessageActivity : AppCompatActivity() {

    private lateinit var reference: DatabaseReference
    private lateinit var to: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        var userTo: String = ""

        intent.getStringExtra("userId")?.let {
            reference = FirebaseDatabase.getInstance().getReference("Users").child(it)
            userTo = it
        }

        sendImgButton.setOnClickListener(){
            if (sendText.text.toString() != "")
                FirebaseAuth.getInstance().currentUser?.let {
                    sendMessage(Message(userTo, it.uid, sendText.text.toString()))
                    sendText.setText("")
                }
        }


        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                val user = p0.getValue(User::class.java)

                supportActionBar?.title = "${user?.username}"

            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })
    }

    private fun sendMessage(message: Message){

        val reference = FirebaseDatabase.getInstance().reference

        reference.child("Chats").push().setValue(message)
    }

}