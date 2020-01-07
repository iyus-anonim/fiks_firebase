package com.example.pendudukdata

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val btnLogin = findViewById<Button>(R.id.login)

        btnLogin.setOnClickListener {
           val username = username.text.toString()
            val password= password.text.toString()

            if (username=="admin" && password=="admin123"){
                val intent=Intent(this@MainActivity,DashboardActivity::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(this@MainActivity,"email atau password salah",Toast.LENGTH_SHORT).show()
            }
        }



    }
}
