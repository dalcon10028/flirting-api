package site.ymango.exception

import org.springframework.validation.FieldError

class BaseException (
    val code: ErrorCode,
    val errorMessage: String?,
    val errors: List<FieldError>? = null,
    val trace: Array<StackTraceElement> = Thread.currentThread().stackTrace,
    val data: Any?,
): RuntimeException(errorMessage) {
    constructor(code: ErrorCode): this(code, code.message, null, Thread.currentThread().stackTrace, null)
    constructor(code: ErrorCode, errorMessage: String?): this(code, errorMessage, null, Thread.currentThread().stackTrace, null)
    constructor(code: ErrorCode, errorMessage: String?, errors: List<FieldError>? = null, data: Any?): this(code, errorMessage, errors, Thread.currentThread().stackTrace, data)
    constructor(code: ErrorCode, errorMessage: String?, data: Any?): this(code, errorMessage, null, Thread.currentThread().stackTrace, data)
    constructor(code: ErrorCode, errorMessage: String?, trace: Array<StackTraceElement>): this(code, errorMessage, null, trace, null)
    constructor(code: ErrorCode, errors: List<FieldError>?, trace: Array<StackTraceElement>): this(code, null, errors, trace, null)
}