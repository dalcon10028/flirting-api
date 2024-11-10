package site.ymango.common.annotation

import org.springframework.security.core.annotation.AuthenticationPrincipal

@MustBeDocumented
@AuthenticationPrincipal
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.ANNOTATION_CLASS)
annotation class Auth
