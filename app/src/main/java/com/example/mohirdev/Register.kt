package com.example.mohirdev

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.concurrent.TimeUnit

class Register : AppCompatActivity() {
    private var isshow = false
    private var conisshow = false
    private lateinit var auth: FirebaseAuth
    private val TAG = "Register"
    private var storedVerificationId: String? = ""
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        var email: EditText = findViewById(R.id.email)
        var name: EditText = findViewById(R.id.name)
        var surname: EditText = findViewById(R.id.surname)
        var phone: EditText = findViewById(R.id.phone)
        var password: EditText = findViewById(R.id.password)
        var con_password: EditText = findViewById(R.id.conPassword)
        var password_Icon: ImageView = findViewById(R.id.show_hide_password)
        var con_password_Icon: ImageView = findViewById(R.id.conShow_hide_password)
        var signUpBtn: AppCompatButton = findViewById(R.id.sign_up_btn)
        var signIn: TextView = findViewById(R.id.signInBtn)




        auth = Firebase.auth
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)

        password_Icon.setOnClickListener {
            if (isshow) {
                isshow = false
                password.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                password_Icon.setImageResource(R.drawable.show)
            } else {
                password.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                password_Icon.setImageResource(R.drawable.hide)
                isshow = true
            }
            password.setSelection(password.length())
        }
        con_password_Icon.setOnClickListener {
            if (conisshow) {
                conisshow = false
                con_password.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                con_password_Icon.setImageResource(R.drawable.show)
            } else {
                con_password.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                con_password_Icon.setImageResource(R.drawable.hide)
                conisshow = true
            }
            con_password.setSelection(con_password.length())
        }

        // [END phone_auth_callbacks]

        signUpBtn.setOnClickListener {
            var getMobileText = phone.text.toString()
            var getemailText = email.text.toString()
            val options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(getMobileText) // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(this) // Activity (for callback binding)
                .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
                .build()
            PhoneAuthProvider.verifyPhoneNumber(options)
            var intent = Intent(this, OTPVerification::class.java)
            intent.putExtra("phone", getMobileText)
            intent.putExtra("email", getemailText)
            startActivity(intent)
        }
    }

    private fun startPhoneNumberVerification(phoneNumber: String) {
        // [START start_phone_auth]
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
        // [END start_phone_auth]
    }

    private fun verifyPhoneNumberWithCode(verificationId: String?, code: String) {
        // [START verify_with_code]
        val credential = PhoneAuthProvider.getCredential(verificationId!!, code)
        // [END verify_with_code]
    }
    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    // [START resend_verification]
    private fun resendVerificationCode(
        phoneNumber: String,
        token: PhoneAuthProvider.ForceResendingToken?,
    ) {
        val optionsBuilder = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this) // (optional) Activity for callback binding
            // If no activity is passed, reCAPTCHA verification can not be used.
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
        if (token != null) {
            optionsBuilder.setForceResendingToken(token) // callback's ForceResendingToken
        }
        PhoneAuthProvider.verifyPhoneNumber(optionsBuilder.build())
    }
    // [END resend_verification]

    // [START sign_in_with_phone]
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")

                    val user = task.result?.user
                } else {
                    // Sign in failed, display a message and update the UI
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                    // Update UI
                }
            }
    }
    // [END sign_in_with_phone]

    private fun updateUI(user: FirebaseUser? = auth.currentUser) {
    }

    companion object {
        private const val TAG = "PhoneAuthActivity"
    }

}