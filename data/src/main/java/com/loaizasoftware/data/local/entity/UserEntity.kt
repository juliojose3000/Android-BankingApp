package com.loaizasoftware.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.loaizasoftware.domain.models.User

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val email: String, // assuming email is unique
    val name: String,
    val lastname: String,
    val password: String
)

fun User.toEntity() = UserEntity(
    name = name,
    lastname = lastname,
    email = email,
    password = password
)

fun UserEntity.toDomain() = User(
    name = name,
    lastname = lastname,
    email = email,
    password = password
)
