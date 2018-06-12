package com.example.uploader.repository

import com.example.uploader.dao.UserDao
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface UserRepository: JpaRepository<UserDao, String>{

    @Query( """
        select u
        from UserDao u
        where u.login = :login

                    """)
    fun findByName2(login:String):Optional<UserDao>
    @Query( """
        select u
        from UserDao u
        where u.login = :login

                    """)
    fun findByName(@Param("login")login:String):Optional<UserDao>

    @Query( """
        select u
        from UserDao u
        where u.login = :login

                    """)
    fun getPass(@Param("login")login:String):Optional<UserDao>



    @Query( """
        select u
        from UserDao u
        where u.id = :id

                    """)
    fun findUserById(@Param("id") id: Int):UserDao

    @Query( """
        DELETE
        from UserDao u
        where u.id = :id

                    """)
    fun deletedUserById(@Param("id") id: Int):UserDao

    @Query( """
        select u
        from UserDao u
        where u.password = :password

                    """)
    fun checkPassword(@Param("password")password:String):UserDao


    /*
    @Query( """
        select id
        from UserDao
        where login = :login

                    """)
    fun findIdByName(@Param("id")id:String):Long


    @Query( """
         Select a.id_rank
         from a RanksDao
         join b UserDao
         ON a.id_usr = b.id

                    """)
    fun findRolesByName(@Param("rank")rank:String):String

    */
    @Query( """
         Select email
         from UserDao
         where email = :email

                    """)
    fun findByEmail(@Param("email")email:String): Optional<UserDao>
/*

    @Query( """
         Insert Into userx(password)
         Values('password')
         where id = :id

                    """)
    fun changePassword(@Param("password")password:String):String

    @Query( """
         Inser Into userx(email)
         Values('email')
         where id = :id

                    """)
    fun changeEmail(@Param("email")email:String):String
    */
}