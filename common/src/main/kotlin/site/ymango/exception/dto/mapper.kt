package site.ymango.exception.dto

import jakarta.validation.ConstraintViolationException
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.FieldError
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import site.ymango.exception.*

fun Exception.toBaseException(): BaseException {
    return BaseException(ErrorCode.UNKNOWN_ERROR, "${this.javaClass.simpleName}: ${this.localizedMessage}", null, this.stackTrace)
}

fun ConstraintViolationException.toBaseException(): BaseException {
    return BaseException(ErrorCode.INVALID_INPUT_VALUE, this.constraintViolations.map { FieldError(it.propertyPath.toString(), it.propertyPath.toString().substringAfterLast("."), it.invalidValue, false, null, null, it.message) }, this.stackTrace)
}

fun MethodArgumentNotValidException.toBaseException(): BaseException {
    return BaseException(ErrorCode.INVALID_INPUT_VALUE, this.bindingResult.fieldErrors.map { FieldError(it.objectName, it.field, it.rejectedValue, it.isBindingFailure, it.codes, it.arguments, it.defaultMessage) }, this.stackTrace)
}

fun BaseException.toBaseExceptionResponse(): BaseExceptionResponse {
    return BaseExceptionResponse(this.code, this.errorMessage ?: this.code.message, this.errors)
}

fun HttpRequestMethodNotSupportedException.toBaseException(): BaseException {
    return BaseException(ErrorCode.INVALID_INPUT_VALUE, this.message, null, this.stackTrace)
}