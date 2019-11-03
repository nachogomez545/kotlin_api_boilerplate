package com.rest.api.security

import com.google.api.client.repackaged.com.google.common.base.Strings
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseToken
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import java.util.ArrayList
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication

@Component
class FirebaseAuthenticationTokenFilter : OncePerRequestFilter() {

    private val loggy = LoggerFactory.getLogger(FirebaseAuthenticationTokenFilter::class.java)
    private val TOKEN_HEADER = "Authorization"

    /**
     *
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Throws(ServletException::class, IOException::class)
    protected override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        loggy.debug("doFilter:: authenticating...")

        val authToken = request.getHeader(TOKEN_HEADER)

        if (Strings.isNullOrEmpty(authToken)) {
            filterChain.doFilter(request, response)
            return
        }

        try {
            val authentication = validateAuthentication(authToken)
            SecurityContextHolder.getContext().setAuthentication(authentication)
            loggy.debug("doFilter():: successfully authenticated.")
        } catch (ex: Exception) {
            response.status = HttpServletResponse.SC_UNAUTHORIZED
            loggy.debug("Fail to authenticate.", ex)
        }

        filterChain.doFilter(request, response)
    }

    /**
     *
     * @param authToken Firebase access token string
     * @return the computed result
     * @throws Exception
     */
    @Throws(Exception::class)
    private fun validateAuthentication(authToken: String): Authentication {
        val authentication: Authentication

        val firebaseToken = authenticateFirebaseToken(authToken)
        loggy.debug(firebaseToken.toString())
        authentication = UsernamePasswordAuthenticationToken(firebaseToken, authToken, listOf())

        return authentication
    }

    /**
     * @param authToken Firebase access token string
     * @return the computed result
     * @throws Exception
     */
    @Throws(Exception::class)
    private fun authenticateFirebaseToken(authToken: String): FirebaseToken {
        val app = FirebaseAuth.getInstance().verifyIdTokenAsync(authToken)

        return app.get()
    }

    override fun destroy() {
        loggy.debug("destroy():: invoke")
    }

}