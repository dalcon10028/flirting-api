package site.ymango.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import site.ymango.auth.component.TokenProvider

@Component
class JwtAuthenticationFilter(
    private val jwtTokenProvider: TokenProvider,
    private val userDetailsService: UserDetailsService,
) : OncePerRequestFilter(
) {
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val authHeader: String = request.getHeader(HttpHeaders.AUTHORIZATION) ?: return filterChain.doFilter(request, response)

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response)
            return
        }

        val token: String = authHeader.extractToken()

        val userId: String = jwtTokenProvider.extractUserId(token)

        if (userId.isNotEmpty() && SecurityContextHolder.getContext().authentication == null) {
            val userDetails: UserDetails = userDetailsService.loadUserByUsername(userId)

            if (jwtTokenProvider.isValidToken(token, userDetails)) {
                updateContext(userDetails, request)
                request.setAttribute("X-FLIRTING-USER-ID", userId)
            }

            filterChain.doFilter(request, response)
        }
    }

    private fun updateContext(foundedUser: UserDetails, request: HttpServletRequest) {
        val authentication = UsernamePasswordAuthenticationToken(
            foundedUser,
            null,
            foundedUser.authorities
        )

        authentication.details = WebAuthenticationDetailsSource().buildDetails(request)

        SecurityContextHolder.getContext().authentication = authentication
    }

    private fun String.extractToken(): String = this.substringAfter("Bearer ")
}