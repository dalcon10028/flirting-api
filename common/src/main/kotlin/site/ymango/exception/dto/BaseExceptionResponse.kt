package site.ymango.exception.dto

import org.springframework.validation.FieldError
import site.ymango.exception.ErrorCode

data class BaseExceptionResponse(
    val code: ErrorCode,
    val message: String,
    val errors: List<FieldError>?,
)
