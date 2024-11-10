package site.ymango.exception

import com.fasterxml.jackson.databind.exc.MismatchedInputException
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.validation.ConstraintViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.security.core.AuthenticationException
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingRequestHeaderException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.*
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.servlet.resource.NoResourceFoundException
import site.ymango.exception.dto.*

private val logger = KotlinLogging.logger {}

@RestControllerAdvice
class ControllerExceptionAdvice {
    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleException(e: Exception): BaseExceptionResponse {
        logger.error(e) { e.localizedMessage }
        return e.toBaseException().toBaseExceptionResponse()
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BaseException::class)
    fun handleBaseException(e: BaseException): BaseExceptionResponse {
        logger.error(e) { e.localizedMessage }
        return e.toBaseExceptionResponse()
    }

    @ExceptionHandler(ConstraintViolationException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleConstraintViolationException(e: ConstraintViolationException): BaseExceptionResponse {
        logger.error(e) { e.localizedMessage }
        return e.toBaseException().toBaseExceptionResponse()
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): BaseExceptionResponse {
        logger.error(e) { e.localizedMessage }
        return e.toBaseException().toBaseExceptionResponse()
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleHttpMessageNotReadableException(e: HttpMessageNotReadableException): BaseExceptionResponse {
        logger.error(e) { e.localizedMessage }
        return when (val cause = e.cause) {
            is MissingKotlinParameterException -> {
                BaseException(ErrorCode.INVALID_INPUT_VALUE, "파라미터가 누락되었습니다.", listOf(FieldError(cause.path.joinToString("."), cause.path.last().fieldName, null, false, null, null, cause.message)), cause.stackTrace).toBaseExceptionResponse()
            }
            is MismatchedInputException -> {
                BaseException(ErrorCode.INVALID_INPUT_VALUE, "파라미터가 잘못되었습니다.", listOf(FieldError(cause.path.joinToString("."), cause.path.last().fieldName, cause.targetType, false, null, null, cause.message)), cause.stackTrace).toBaseExceptionResponse()
            }
            else -> {
                BaseException(ErrorCode.INVALID_INPUT_VALUE, cause?.message, null, cause?.stackTrace).toBaseExceptionResponse()
            }
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingRequestHeaderException::class)
    fun handleMissingRequestHeaderException(e: MissingRequestHeaderException): BaseExceptionResponse {
        logger.error(e) { e.localizedMessage }
        return BaseException(ErrorCode.INVALID_INPUT_VALUE, "${e.headerName} 헤더가 누락되었습니다.", null, e.stackTrace).toBaseExceptionResponse()
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException::class)
    fun handleMissingServletRequestParameterException(e: MissingServletRequestParameterException): BaseExceptionResponse {
        logger.error(e) { e.localizedMessage }
        return BaseException(ErrorCode.INVALID_INPUT_VALUE, "${e.parameterName} 파라미터가 누락되었습니다.", null, e.stackTrace).toBaseExceptionResponse()
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoResourceFoundException::class)
    fun handleNoResourceFoundException(e: NoResourceFoundException): BaseExceptionResponse {
        logger.error(e) { e.localizedMessage }
        return BaseException(ErrorCode.RESOURCE_NOT_FOUND, "존재하지 않는 리소스입니다.", null, e.stackTrace).toBaseExceptionResponse()
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException::class)
    fun handleAuthenticationException(e: AuthenticationException): BaseExceptionResponse {
        logger.error(e) { e.localizedMessage }
        return BaseException(ErrorCode.AUTHENTICATION_FAILED, "인증에 실패하였습니다.", null, e.stackTrace).toBaseExceptionResponse()
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun handleMethodArgumentTypeMismatchException(e: MethodArgumentTypeMismatchException): BaseExceptionResponse {
        logger.error(e) { e.localizedMessage }
        return BaseException(ErrorCode.INVALID_INPUT_VALUE, "파라미터가 잘못되었습니다.", listOf(FieldError(e.name, e.name, e.value, false, null, null, e.message)), e.stackTrace).toBaseExceptionResponse()
    }
}