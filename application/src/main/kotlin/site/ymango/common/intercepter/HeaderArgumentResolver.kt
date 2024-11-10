package site.ymango.common.intercepter

import org.springframework.core.MethodParameter
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import site.ymango.common.annotation.AppHeader
import site.ymango.common.model.FlirtingHeader
import site.ymango.exception.BaseException
import site.ymango.exception.ErrorCode

class HeaderArgumentResolver : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.parameterType == FlirtingHeader::class.java && parameter.hasParameterAnnotation(AppHeader::class.java)
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any {
        webRequest.getHeader("X-FLIRTING-DEVICE-ID")?.let {
            return FlirtingHeader(it)
        }
        throw BaseException(ErrorCode.NOTNULL_PARAMETER, "X-FLIRTING-DEVICE-ID")
    }
}