package com.dailyview.api.configuration

import org.springframework.stereotype.Component
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest

@Component
class RequestFilter : Filter {
    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        val httpServletRequest = (request as HttpServletRequest)
        val wrapper = CustomServletRequestWrapper(request)
        chain?.doFilter(wrapper, response)
    }
}
