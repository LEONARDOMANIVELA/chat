package com.example.talk_o.app.ui.fragments.adapter

import android.content.Context
import android.content.Intent
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.talk_o.R
import com.example.talk_o.app.ui.message.MessageActivity
import com.example.talk_o.data.model.User
import kotlinx.android.synthetic.main.user_item.view.*

class UserAdapter(
    private val context: Context,
    private val users: List<User>
): RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.user_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = users[position]
        holder.bindView(user, context)
    }

    override fun getItemCount(): Int = users.size

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        fun bindView(user: User, context: Context){
            if (user.imgUrl == "default"){
                itemView.itemListImg.setImageResource(R.mipmap.ic_launcher_round)
            }else {
                Glide.with(context).load(user.imgUrl).into(itemView.itemListImg)
            }
            itemView.itemListUsername.text = user.username
            itemView.setOnClickListener(object : View.OnClickListener{
                override fun onClick(p0: View?) {
                    val intent = Intent(context, MessageActivity::class.java)
                    intent.putExtra("userId", user.id)
                    context.startActivity(intent)
                }

            })
        }
    }
}