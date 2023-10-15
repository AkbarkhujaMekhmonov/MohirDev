package com.example.mohirdev.models

class Mentor {
    var ID:Int=0
    var age:Int=0
    var experience:String?=null
    var image:String?=null
    var name:String?=null
    var surname:String?=null
    var specialty:String?=null
    var about:String?=null



    constructor()
    constructor(
        ID: Int,
        age: Int,
        experience: String?,
        image: String?,
        name: String?,
        surname: String?,
        specialty: String?,
        about: String?
    ) {
        this.ID = ID
        this.age = age
        this.experience = experience
        this.image = image
        this.name = name
        this.surname = surname
        this.specialty = specialty
        this.about = about
    }
}