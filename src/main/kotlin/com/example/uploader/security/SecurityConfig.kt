package com.example.uploader.security



import com.example.uploader.dao.MyUserDetailsService
import com.example.uploader.dao.UserDao
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.access.AccessDeniedHandler
import javax.sql.DataSource

@Configuration

class SpringSecurityConfig : WebSecurityConfigurerAdapter() {

    @Autowired
    var accessDeniedHandler: AccessDeniedHandler? = null

    @Autowired
    var userDetailsService:MyUserDetailsService = MyUserDetailsService()

    @SuppressWarnings("deprecation")
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }



    // roles admin allow to access /admin/**
    // roles user allow to access /user/**
    // custom 403 access denied handler
    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {

        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/admin/**").hasAnyRole("admin")
                .antMatchers("/user/**").hasAnyRole("user")
                .antMatchers("/", "/index", "/info","/login","/signUp").permitAll()
                .anyRequest().authenticated()
                .and().formLogin().loginPage("/login").permitAll()
                .and().logout().permitAll()
                .and().exceptionHandling().accessDeniedHandler(accessDeniedHandler)
    }

    @Throws(Exception::class)
    protected override fun configure(auth: AuthenticationManagerBuilder?) {
        auth!!.userDetailsService(userDetailsService)
    }
}
