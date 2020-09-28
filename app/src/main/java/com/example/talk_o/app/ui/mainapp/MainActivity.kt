package com.example.talk_o.app.ui.mainapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.talk_o.R
import com.example.talk_o.app.ui.fragments.ChatsFragment
import com.example.talk_o.app.ui.fragments.UsersFragment
import com.example.talk_o.app.ui.startup.StartActivity
import com.example.talk_o.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var reference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseAuth.getInstance().currentUser?.let {
            reference = FirebaseDatabase.getInstance().getReference("Users").child(it.uid)
            setUserData(reference)
        }

        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)

        viewPagerAdapter.addFragment(ChatsFragment(), "Chat")
        viewPagerAdapter.addFragment(UsersFragment(), "Usuários")

        viewPager.adapter = viewPagerAdapter
        tabLayout.setupWithViewPager(viewPager)
    }

    private fun setUserData(reference: DatabaseReference) {
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {

                val user = p0.getValue(User::class.java)
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true;
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.logout){
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, StartActivity::class.java))
            finish()
            return true
        }
        return false
    }

    class ViewPagerAdapter(
        fm: FragmentManager,
        private val fragments: ArrayList<Fragment> = ArrayList<Fragment>(),
        private val titles: ArrayList<String> = ArrayList<String>()
    ) : FragmentPagerAdapter(fm) {

        override fun getCount(): Int {
            return fragments.size
        }

        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        fun addFragment(fragment: Fragment, title: String){
            fragments.add(fragment)
            titles.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titles[position]
        }
    }
}