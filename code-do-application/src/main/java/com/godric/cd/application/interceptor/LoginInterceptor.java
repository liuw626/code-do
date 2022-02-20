package com.godric.cd.application.interceptor;

import com.godric.cd.constant.CodeDoConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();

        boolean passed = session.getAttribute(CodeDoConstant.SESSION_KEY_USER_OPEN_ID) != null;

        if (!passed) {
            log.info("login interceptor not passed, user need login, url:{}", request.getRequestURI());
            request.getRequestDispatcher("/login/error/user").forward(request, response);
            return false;
        }

        return true;
    }
}
