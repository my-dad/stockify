package com.example.stockify.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.stockify.model.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User) : Long

    @Query("SELECT * FROM user where email = :email")
    suspend fun getUser(email: String): User

    @Query("SELECT COUNT(*) FROM user WHERE email = :email")
    suspend fun getUserCountByEmail(email: String): Int




}