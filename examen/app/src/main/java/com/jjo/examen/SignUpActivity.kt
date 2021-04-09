package com.jjo.examen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_auth.*
import kotlinx.android.synthetic.main.activity_auth.createUserBtn
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.util.regex.Pattern

class SignUpActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    var databaseReference : DatabaseReference? = null
    var database: FirebaseDatabase? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")
        SignUp()

    }


    private fun SignUp(){

        val REG = "^(\\+91[\\-\\s]?)?[0]?(91)?[789]\\d{9}\$"
        var PATTERN: Pattern = Pattern.compile(REG)
        fun CharSequence.isPhoneNumber() : Boolean = PATTERN.matcher(this).find()

        createUserBtn.setOnClickListener{
            if(TextUtils.isEmpty(nameEditTxt.text.toString())){
                nameEditTxt.setError("Please enter your name")
                return@setOnClickListener
            }else if(TextUtils.isEmpty(emailEditInput.text.toString())){
                emailEditInput.setError("Please enter your email")
                return@setOnClickListener

            }else if(TextUtils.isEmpty(passwordEditInput.text.toString())){
                passwordEditInput.setError("Please enter a password")
                return@setOnClickListener

            }else if(!phoneEditInput.text.toString().isPhoneNumber()){
                phoneEditInput.setError("Please a valid phone number")
                return@setOnClickListener
            }


            auth.createUserWithEmailAndPassword(emailEditInput.text.toString(), passwordEditInput.text.toString())
                .addOnCompleteListener{
                    if(it.isSuccessful){
                        val currentUser = auth.currentUser
                        val currentUserDb = databaseReference?.child((currentUser?.uid!!))
                        currentUserDb?.child("nombreTxt")?.setValue(nameEditTxt.text.toString())
                        currentUserDb?.child("mailTxt")?.setValue(emailEditInput.text.toString())
                        currentUserDb?.child("phoneTxt")?.setValue(phoneEditInput.text.toString())
                        Toast.makeText(this@SignUpActivity, "Registration completed ", Toast.LENGTH_LONG).show()
                        finish()

                    }else{
                        Toast.makeText(this@SignUpActivity, "Registration failed, please try again! ", Toast.LENGTH_LONG).show()
                    }
                }
        }


    }
}