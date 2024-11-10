package site.ymango.auth.v1

import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*
import site.ymango.auth.service.AuthenticationService
import site.ymango.auth.v1.dto.*
import site.ymango.common.annotation.AppHeader
import site.ymango.common.model.FlirtingHeader
import site.ymango.auth.v1.dto.RegisterRequest


@RestController
@RequestMapping("/v1/auth")
class AuthController(
    private val authService: AuthenticationService,
) {
    @PostMapping("/authenticate")
    fun authenticate(
        @Valid @RequestBody authenticateRequest: AuthenticationRequest,
        @AppHeader header: FlirtingHeader,
    ): AuthenticationResponse = authService.authenticate(authenticateRequest, header.deviceId)

    @PostMapping("/refresh")
    fun refresh(
        @Valid @RequestBody refreshRequest: RefreshRequest
    ): RefreshResponse = authService.refresh(refreshRequest.refreshToken)

    @PostMapping("/register")
    fun register(
        @AppHeader header: FlirtingHeader,
        @Valid @RequestBody registerRequest: RegisterRequest
    ): AuthenticationResponse = authService.register(registerRequest, header.deviceId)
}