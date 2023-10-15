package com.example.mohirdev.fragment_main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mohirdev.databinding.FragmentProfilBinding
import com.example.mohirdev.models.Mentor
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso


class Profil : Fragment() {

    lateinit var storage: FirebaseFirestore
    private  val TAG = "Profil"
    lateinit var binding: FragmentProfilBinding
    lateinit var listMentor: ArrayList<Mentor>
    val auth = FirebaseAuth.getInstance()
    val currentUser: FirebaseUser? = auth.currentUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentProfilBinding.inflate(layoutInflater)
        storage=FirebaseFirestore.getInstance()
        listMentor= ArrayList()
        var name=""
        var email=""
        var photo=""
        if (currentUser != null) {
            val uid = currentUser.uid
            val email = currentUser.email
            val displayName = currentUser.displayName
            val photoUrl = currentUser.photoUrl

            // TODO: Use the user's data as needed
            // For example, display the user's name and email
            if (displayName != null) {
                binding.name.text=displayName
            }
            if (email != null) {
                binding.email.text=email
            }
            if (photoUrl != null) {
                Picasso.get().load(photoUrl).into(binding.profileImg)
            }

        } else {
            // No user is currently signed in
            // TODO: Handle the case where no user is signed in
        }



        return binding.root
    }

}