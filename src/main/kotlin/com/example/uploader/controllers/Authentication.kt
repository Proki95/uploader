package com.example.uploader.controllers

import com.example.uploader.dao.RanksDao
import com.example.uploader.dao.UserDao
import com.example.uploader.repository.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
class Authentication (val userRepository: UserRepository){





    /*
    1.Pobieram dane Requesta
    2.Pobieram istniejącego usera po loginie
    3.Jeśli user nie istnieje - wyjątek
    3.5. Jeżeli user istnieje, ale hasło się nie zgadza - wyjątek
    4.Jeśli user istnieje - sprawdzam hasło usera i z requesta są takie same
    5.Generuje token używająć JWTHelper.
    6.Zwaram token ( wrzucam go do headera ) jeżeli został spełniony punkt 4.
     */
    @PostMapping("/login")
    fun login(@RequestParam("username")login: String,@RequestParam("password")password: String): String {

        val login: String? = login
        val password: String? = password

        val dbUser = userRepository.findByName(login!!)

        if(!dbUser.isPresent)
            throw UsernameNotFoundException("Nie istnieje użytkownik o loginie : " + login)
        if(dbUser.get().password!=password)
            throw UsernameNotFoundException("Podane hasło jest nie poprawne ! ") // zmienić

        return "redirect:/user"


    }

    //
    // ZROBIONE
    //
    /*
    1. tworze nowy userDao
    2.przypisuje do niego dane z requesta
    3. Tworzę obiekt roli - przypisuje do niego dane z już istniejącej
    4. przypisuje stworzony obiekt roli do nowego konta
    5. zapis do bazy danych
     */

    @PostMapping("/signUp")
    fun signUp(@RequestParam("login") login:String, @RequestParam("password") password:String, @RequestParam("password2") password2:String, @RequestParam("email") email:String): String {

        if(password!= password2)
            throw UsernameNotFoundException("Hasła różnią się !")
        val newRole = RanksDao().also {
            it.id = 2
            it.Name = "user"
        }
        val passwordEncoder:BCryptPasswordEncoder = BCryptPasswordEncoder()

        val encodePass = passwordEncoder.encode(password)
        val newAccount = UserDao().also {
            it.login = login
            it.password = encodePass
            it.password = encodePass
            it.email = email
            it.roles = setOf(newRole)
        }


        val savedAccount = userRepository.save(newAccount)


        return "redirect:/signIn"

    }

    /*
    @PostMapping("/signIn")
    fun signIn(@RequestParam("login")login: String,@RequestParam("password")password: String): ModelAndView {

        val login: String? = login
        val password: String? = password

        val dbUser = userRepository.findByName(login!!)

        if(!dbUser.isPresent)
            throw UsernameNotFoundException("Nie istnieje użytkownik o loginie : " + login)
        if(dbUser.get().password!=password)
            throw UsernameNotFoundException("Podane hasło jest nie poprawne ! ") // zmienić

        val convertedUser = jwtHelper.convertUserAccountToJwtString(dbUser.get())

        val foobar = jwtHelper.getHeaders(convertedUser)

        val response = ResponseEntity<Void>(foobar,HttpStatus.OK)

        return ModelAndView("infoAuthority","login",response)

    }
    */

    /*
@PostMapping("/signIn")
fun signIn(@RequestBody signInRequest: SignInRequest): ResponseEntity<Void> {

    val login: String? = signInRequest.login
    val password: String? = signInRequest.password

    val dbUser = userRepository.findByName(login!!)

    if(!dbUser.isPresent)
        throw UsernameNotFoundException("Nie istnieje użytkownik o loginie : " + login)
    if(dbUser.get().password!=password)
        throw UsernameNotFoundException("Podane hasło jest nie poprawne ! ") // zmienić

    val convertedUser = jwtHelper.convertUserAccountToJwtString(dbUser.get())

    val foobar = jwtHelper.getHeaders(convertedUser)

    val response = ResponseEntity<Void>(foobar,HttpStatus.OK)

    return response

}


*/
    /*
@PostMapping("/signUp")
fun signUp(@RequestBody signUpRequest: SignUpRequest): UserDao {

    if(signUpRequest.password!= signUpRequest.password2)
        throw UsernameNotFoundException("Hasła różnią się !")
    val newRole = RanksDao().also {
        it.id = 2
        it.Name = "user"
    }

    val newAccount = UserDao().also {
        it.login = signUpRequest.login
        it.password = signUpRequest.password
        it.email = signUpRequest.email
        it.roles = setOf(newRole)
    }

    val savedAccount = userRepository.save(newAccount)


    return savedAccount
}
*/



}