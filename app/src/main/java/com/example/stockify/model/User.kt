package com.example.stockify.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = "user"
)
data class User(
    @PrimaryKey(autoGenerate = true )var id : Long = 0,
    var name:String,
    var email:String,
    var password: String
){
    constructor(name: String, email: String, password: String) : this(
    0,
        name,
        email,
        password
    )
}
