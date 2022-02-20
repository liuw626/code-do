//package com.godric.cd.application.interceptor;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//@Slf4j
//public class AdminInterceptor implements HandlerInterceptor {
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        HttpSession session = request.getSession();
//
//        Object userInfoObj = session.getAttribute(RunkingConstant.SESSION_KEY_USER_INFO);
//        if (userInfoObj == null) {
//            log.info("admin interceptor not passed, user info is null, url:{}", request.getRequestURI());
//            request.getRequestDispatcher("/login/error/salesman").forward(request, response);
//            return false;
//        }
//
//        LoginUserVO loginUser = (LoginUserVO) userInfoObj;
//
//        if (loginUser.getAdmin() != null && loginUser.getAdmin()) {
//            return true;
//        }
//
//        log.info("admin interceptor not passed, user have no admin permission, url:{}, username:{}", request.getRequestURI(), loginUser.getUsername());
//        request.getRequestDispatcher("/login/error/admin").forward(request, response);
//        return false;
//    }
//}
