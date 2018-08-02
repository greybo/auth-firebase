package com.example.sbotlevskyi.auth_firebase

import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AuthActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    private val btnLogIn by lazy { findViewById<Button>(R.id.button_login) }
    private val btnSignUp by lazy { findViewById<Button>(R.id.button_signUp) }
    private val emailText by lazy { findViewById<TextInputEditText>(R.id.text_email) }
    private val passwordText by lazy { findViewById<TextInputEditText>(R.id.text_password) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        mAuth = FirebaseAuth.getInstance()

        val currentUser = mAuth.currentUser
        nextActivity(currentUser)

        btnLogIn.setOnClickListener {
            signIn(emailText.text.toString(), passwordText.text.toString())
        }

        btnSignUp.setOnClickListener {
            signUp(emailText.text.toString(), passwordText.text.toString())
        }
    }

    fun signUp(email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(Constants.TAG, "createUserWithEmail:success")
                        val user = mAuth.currentUser
                        nextActivity(user)
                    } else {
                        Log.w(Constants.TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(this@AuthActivity, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                    }
                }
    }

    fun signIn(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(Constants.TAG, "signInWithEmail:success")
                        val user = mAuth.currentUser
                        nextActivity(user)
                    } else {
                        Log.w(Constants.TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(this@AuthActivity, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                    }
                }
    }

    fun hasSigned(): Boolean {
        var emailVerified: Boolean = false
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val name = it.displayName
            val email = it.email
            val photoUrl = it.photoUrl
            emailVerified = it.isEmailVerified
            val uid = it.uid
        }
        return emailVerified
    }

    private fun nextActivity(user: FirebaseUser?) {
        user?.uid?.let {
            Toast.makeText(this, "Successful", Toast.LENGTH_LONG).show()
            MainActivity.start(this@AuthActivity)
        }
    }

}