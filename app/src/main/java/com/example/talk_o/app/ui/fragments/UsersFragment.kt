package com.example.talk_o.app.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.talk_o.R
import com.example.talk_o.app.ui.fragments.adapter.UserAdapter
import com.example.talk_o.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_users.*

class UsersFragment : Fragment() {

    private lateinit var userAdapter : UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        context?.let { readUsers(it) }

        return inflater.inflate(R.layout.fragment_users, container, false)
    }

    private fun readUsers(context: Context){
        val reference = FirebaseDatabase.getInstance().getReference("Users")
        var userId : String? = null

        FirebaseAuth.getInstance().currentUser?.let {
            userId = it.uid
        }


        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {

                val list: ArrayList<User> = ArrayList()

                p0.children.forEach {
                    it.getValue(User::class.java)?.let { user ->
                        if (user.id != userId)
                            list.add(user)
                    }
                }
                recyclerView.layoutManager = LinearLayoutManager(context)
                userAdapter = UserAdapter(context, list)
                recyclerView.adapter = userAdapter
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })
    }

}