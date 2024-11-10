package site.ymango.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import site.ymango.common.intercepter.HeaderArgumentResolver

@Configuration
class WebConfig : WebMvcConfigurer {
     override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
         resolvers.add(HeaderArgumentResolver())
     }
}