package site.ymango.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import site.ymango.filter.JwtAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val authenticationProvider: AuthenticationProvider
) {
    @Bean
    fun filterChain(http: HttpSecurity, jwtAuthenticationFilter: JwtAuthenticationFilter): DefaultSecurityFilterChain {
        http
            .csrf { it.disable() }
            .authorizeHttpRequests {
                it.requestMatchers("/admin/**").permitAll()
                    it.requestMatchers("/actuator/**").permitAll() // TODO: 보안 처리
//                it.requestMatchers("/admin/**").hasRole("ADMIN")
                    .requestMatchers("/v1/auth/**").permitAll()
                    .requestMatchers("/v1/verifications/**").permitAll()
                    .requestMatchers("/v1/users").permitAll()
                    .requestMatchers("/v1/codes").permitAll()
                    .anyRequest().fullyAuthenticated()

            }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authenticationProvider(authenticationProvider)
            .exceptionHandling {
                it.authenticationEntryPoint { _, response, _ -> response.sendError(401, "Unauthorized") }
            }
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }
}