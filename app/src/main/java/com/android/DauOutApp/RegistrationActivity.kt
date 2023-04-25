package com.android.DauOutApp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_registration.*

class RegistrationActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    var databaseReference :  DatabaseReference? = null
    var database: FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")

        register()
    }

    private fun register() {


        registerButton.setOnClickListener {

                if(TextUtils.isEmpty(name_Input.text.toString())) {
                    name_Input.setError("Please enter first name ")
                    return@setOnClickListener
                } else if(TextUtils.isEmpty(user_name_Input.text.toString())) {
                    name_Input.setError("Please enter last name ")
                    return@setOnClickListener
                }else if(TextUtils.isEmpty(email_Input.text.toString())) {
                    name_Input.setError("Please enter user name ")
                    return@setOnClickListener
                }else if(TextUtils.isEmpty(password_Input.text.toString())) {
                    name_Input.setError("Please enter password ")
                    return@setOnClickListener
                }


            auth.createUserWithEmailAndPassword(email_Input.text.toString(), password_Input.text.toString())
                .addOnCompleteListener {
                    if(it.isSuccessful) {
                        val currentUser = auth.currentUser
                        val currentUSerDb = databaseReference?.child((currentUser?.uid!!))
                        currentUSerDb?.child("firstname")?.setValue(name_Input.text.toString())
                        currentUSerDb?.child("lastname")?.setValue(user_name_Input.text.toString())

                        Toast.makeText(this@RegistrationActivity, "Registration Success. ", Toast.LENGTH_LONG).show()
                        finish()

                    } else {
                        Toast.makeText(this@RegistrationActivity, "Registration failed, please try again! ", Toast.LENGTH_LONG).show()
                    }
                }
        }

        login.setOnClickListener {
            startActivity(Intent(this@RegistrationActivity, LoginActivity::class.java))
            finish()
        }
    }
}