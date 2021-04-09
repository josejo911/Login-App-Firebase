package com.jjo.examen


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_auth.*
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AuthActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        auth = FirebaseAuth.getInstance()
        val currentuser = auth.currentUser
        if(currentuser != null){
            startActivity(Intent(this@AuthActivity, HomeActivity::class.java))
            finish()
        }
        LogIn()


    }

    private fun LogIn(){
        logInBtn.setOnClickListener {
            if(TextUtils.isEmpty((emailEditTxt.text.toString()))){
                emailEditTxt.setError("Please enter a correct Email")
                return@setOnClickListener
            }
            else if(TextUtils.isEmpty((passwordEditTxt.text.toString()))){
                passwordEditTxt.setError("Please enter a correct password")
                return@setOnClickListener
            }
            auth.signInWithEmailAndPassword(emailEditTxt.text.toString(), passwordEditTxt.text.toString())
                .addOnCompleteListener{
                    if(it.isSuccessful){
                        startActivity(Intent(this@AuthActivity, HomeActivity::class.java))
                        finish()

                    }else{
                        Toast.makeText(this@AuthActivity, "Log In failed, please try again! ", Toast.LENGTH_LONG).show()
                    }

                }
        }

        createUserBtn.setOnClickListener {
            startActivity(Intent(this@AuthActivity, SignUpActivity::class.java))
        }

    }


}