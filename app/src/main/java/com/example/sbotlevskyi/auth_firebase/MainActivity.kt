package com.example.sbotlevskyi.auth_firebase

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }

    private val emailText by lazy { findViewById<TextView>(R.id.email_text) }
    private val uidText by lazy { findViewById<TextView>(R.id.uid_text) }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            emailText.text = "email = " + it.email
            uidText.text =   "  uid = " + it.uid

            val name = it.displayName
            val email = it.email
            val photoUrl = it.photoUrl

        }
    }


}
