package com.example.mohirdev

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class Login : AppCompatActivity() {
    private var showIcon=false
    private val RC_SIGN_IN = 9001
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        var username:EditText=findViewById(R.id.Username_Et)
        var password:EditText=findViewById(R.id.password)
        var paswordIcon:ImageView=findViewById(R.id.show_hide_password)
        var signUpBtn:TextView=findViewById(R.id.signUpBtn)
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        paswordIcon.setOnClickListener {
            if (showIcon){
                showIcon=false
                password.inputType=InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                paswordIcon.setImageResource(R.drawable.show)
            }else{
                password.inputType=InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                paswordIcon.setImageResource(R.drawable.hide)
                showIcon=true
            }
            password.setSelection(password.length())
        }

        signUpBtn.setOnClickListener {
            startActivity(Intent(this,Register::class.java))
        }

        val signInButton = findViewById<RelativeLayout>(R.id.signInWithGoogle)
        signInButton.setOnClickListener { signIn() }

        auth = FirebaseAuth.getInstance()

        // Check if user is already authenticated
        val currentUser = auth.currentUser
        if (currentUser != null) {
            // User is already authenticated, proceed to main activity
            startActivity(Intent(this,MainActivity::class.java))
            finish() // Finish the login activity to prevent going back to it
        }
    }

    private fun signIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(this, gso)
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account?.idToken)
            } catch (e: ApiException) {
                // Google sign-in failed
                // TODO: Handle error here
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String?) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Authentication successful
                    val user = auth.currentUser
                    startActivity(Intent(this,MainActivity::class.java))
                    // Start your main activity
                    finish() // Finish the login activity to prevent going back to it
                } else {
                    // Authentication failed
                    // TODO: Handle error here
                }
            }
    }
}