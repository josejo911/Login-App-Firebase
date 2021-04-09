package com.jjo.examen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    var databaseReference : DatabaseReference? = null
    var database: FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")

        LoadProfile()
    }

    private fun LoadProfile(){
        val user = auth.currentUser
        val userReference = databaseReference?.child(user?.uid!!)

        mailTxt.text = "User Email:  " + user.email

        userReference?.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                nombreTxt.text = "User name:  " + snapshot.child("nombreTxt").value.toString()
                phoneTxt.text = "Phone:  " + snapshot.child("phoneTxt").value.toString()

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        logoutBtn.setOnClickListener{
            auth.signOut()
            startActivity(Intent(this@HomeActivity, AuthActivity::class.java))
            finish()
        }

    }
}