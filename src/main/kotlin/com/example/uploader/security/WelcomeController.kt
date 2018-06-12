package com.example.uploader.security

import org.slf4j.LoggerFactory
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component

import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.io.IOException

// handle 403 page
@Component
class MyAccessDeniedHandler : AccessDeniedHandler {

    @Throws(IOException::class, ServletException::class)
    override fun handle(httpServletRequest: HttpServletRequest,
                        httpServletResponse: HttpServletResponse,
                        e: AccessDeniedException) {

        val auth = SecurityContextHolder.getContext().authentication

        if (auth != null) {
            logger.info("User '" + auth.name
                    + "' attempted to access the protected URL: "
                    + httpServletRequest.requestURI)
        }

        httpServletResponse.sendRedirect(httpServletRequest.contextPath + "/403")

    }

    companion object {

        private val logger = LoggerFactory.getLogger(MyAccessDeniedHandler::class.java)
    }
}