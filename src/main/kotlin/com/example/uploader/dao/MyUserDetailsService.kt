package com.example.uploader.dao

import com.example.uploader.repository.UserRepository
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
class MyUserDetailsService: UserDetailsService {
    val userRepository: UserRepository? = null

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository!!.findByName(username)
        val dbUser = user.get().login
        var buildUser:User.UserBuilder?=null
        if(dbUser==null){
            throw UsernameNotFoundException("Brak użytkownika")
        }
            val auth:GrantedAuthority = SimpleGrantedAuthority(user.get().roles.toString())
            val userDetails:UserDetails=   User(user.get().login,user.get().password, Arrays.asList(auth))
            buildUser = org.springframework.security.core.userdetails.User.withUsername(username)
            buildUser!!.password(BCryptPasswordEncoder().encode(user.get().password))
            buildUser.roles(user.get().roles.toString())
            return buildUser.build()
            //return userDetails
        }

    }

