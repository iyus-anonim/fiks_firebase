package com.example.pendudukdata

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterViewAnimator
import android.widget.ImageButton
import android.widget.ListView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*
import android.widget.Adapter as Adapter

class DashboardActivity : AppCompatActivity() {
    lateinit var ref : DatabaseReference
    lateinit var list : MutableList<pendudukdata>
    lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)


        val BtnADD = findViewById<FloatingActionButton>(R.id.BtnADD)
        ref= FirebaseDatabase.getInstance().getReference("pendudukdata")

        list = mutableListOf()
        listView = findViewById(R.id.LVPenduduk)

        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()){
                    list.clear()
                    for (h in p0.children){
                        val user = h.getValue(pendudukdata::class.java)
//                        user?.let { list.add(it) }

                        user?.let { list.add(it) }
                    }
                    val adapter = Adapter_List(this@DashboardActivity, R.layout.pendudukdatas, list)
                    listView.adapter = adapter

                }

            }
        })

        BtnADD.setOnClickListener {

            val intent=Intent(this@DashboardActivity,CreateActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        (menuInflater).also {
            it.inflate(R.menu.menu,menu)
        }
        return true

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId==R.id.ItAbout){
            val intent=Intent(this@DashboardActivity,AboutActivity::class.java)
            startActivity(intent)
        }else{
            startActivity(Intent(this@DashboardActivity,MainActivity::class.java))

        }
        return super.onOptionsItemSelected(item)
    }
}
