package com.example.uploader.controllers

import com.example.uploader.dto.EditUserEmail
import com.example.uploader.dto.EditUserPassword
import com.example.uploader.repository.UserRepository
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.GetMapping
import javax.servlet.http.HttpServletResponse


@Controller
class UserController(val userRepository: UserRepository) {


    @GetMapping("/user")
    fun userPanel():String{
        return "user"
    }
    @GetMapping("/")
    @PreAuthorize("hasAnyAuthority('user')")
    fun viewHome(): String {
        return "index"
    }

    @GetMapping("/Contact")
    fun viewContact():String {

        return "info"
    }

    @GetMapping("/ContactAuthority")
    @PreAuthorize("hasAnyAuthority('admin')")
    fun viewContactAdmin():String {

        return "infoAdmin"
    }

    @GetMapping("/ContactUser")
    @PreAuthorize("hasAnyAuthority('user')")
    fun viewContactAuthority(request: HttpServletResponse): String {

        return "infoAuthority"
        }


    @GetMapping("/signUp")
    fun signUpGet():String{
        return "rejestracja"
    }

    @GetMapping("/login")
    fun signInGet():String{
        return "logowanie"
    }

    @GetMapping("/showUsers")
    //@PreAuthorize("hasAnyAuthority('admin')")
    fun showUsers(map: ModelMap): String {
        map.addAttribute("user", userRepository.findAll())
        return "admin"
    }


    @PostMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('admin')")
    fun deleteUser(@PathVariable id:Int): String {
        userRepository.delete(userRepository.findUserById(id))
        return "index"
    }


    @GetMapping("/getUser/{id}")
    @PreAuthorize("hasAnyAuthority('admin')")
    fun getUser(map: ModelMap,@PathVariable id:Int): String {
        map.addAttribute("user", userRepository.findUserById(id))

        return "getUser"
    }

    @PutMapping("/editPassword/{id}")
    @PreAuthorize("hasAnyAuthority('user')")
    fun editUserPassword(@RequestBody editUserPass: EditUserPassword){

        val oldPassword: String? = editUserPass.oldPassword
        val newPassword: String? = editUserPass.newPassword

        val dbUser = userRepository.findUserById(1)
        if (dbUser.password != oldPassword)
        throw UsernameNotFoundException("Podałeś złe stare hasło !")
        dbUser.password = newPassword
        userRepository.save(dbUser)
        }





    @PutMapping("/editEmail/{id}")
    @PreAuthorize("hasAnyAuthority('user')")
    fun editUserEmail(@RequestBody editUserEmail: EditUserEmail,@PathVariable id:Int){

        val newEmail = editUserEmail.newEmail
        val dbUser = userRepository.findUserById(id)

        dbUser.email=newEmail
        userRepository.save(dbUser)
    }
}










