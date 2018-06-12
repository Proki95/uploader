package com.example.uploader.repository

import com.example.uploader.dao.UserDao
import org.springframework.data.jpa.repository.JpaRepository

interface AuthenticationRepository : JpaRepository <UserDao, String>