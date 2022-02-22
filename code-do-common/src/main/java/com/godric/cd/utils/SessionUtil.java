package com.godric.cd.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.godric.cd.constant.CodeDoConstant;
import com.godric.cd.exception.BizErrorEnum;
import com.godric.cd.exception.BizException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class SessionUtil {

    /**
     * 获取request
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return requestAttributes == null ? null : requestAttributes.getRequest();
    }

    /**
     * 获取session
     */
    public static HttpSession getSession() {
        HttpServletRequest request = getRequest();
        return request == null ? null : request.getSession(false);
    }

    /**
     * 获取真实路径
     */
    public static String getRealRootPath() {
        HttpServletRequest request = getRequest();
        return request == null ? "" : request.getServletContext().getRealPath("/");
    }

    /**
     * 获取ip
     */
    public static String getIp() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        return servletRequestAttributes == null ? null : servletRequestAttributes.getRequest().getRemoteAddr();
    }

    /**
     * 获取session中的Attribute
     */
    public static Object getSessionAttribute(String name) {
        HttpServletRequest request = getRequest();
        return request == null ? null : request.getSession().getAttribute(name);
    }

    /**
     * 设置session的Attribute
     */
    public static void setSessionAttribute(String name, Object value) {
        HttpServletRequest request = getRequest();
        if (request != null) {
            request.getSession().setAttribute(name, value);
        }
    }

    /**
     * 获取request中的Attribute
     */
    public static Object getRequestAttribute(String name) {
        HttpServletRequest request = getRequest();
        return request == null ? null : request.getAttribute(name);
    }

    /**
     * 设置request的Attribute
     */
    public static void setRequestAttribute(String name, Object value) {
        HttpServletRequest request = getRequest();
        if (request != null) {
            request.setAttribute(name, value);
        }
    }

    /**
     * 获取上下文path
     */
    public static String getContextPath() {
        HttpServletRequest request = getRequest();
        return request == null ? null : request.getContextPath();
    }

    /**
     * 删除session中的Attribute
     */
    public static void removeSessionAttribute(String name) {
        HttpServletRequest request = getRequest();
        if (request != null) {
            request.getSession().removeAttribute(name);
        }
    }

    public static Long getUid() {
        Object uidObj = getSessionAttribute(CodeDoConstant.SESSION_KEY_UID);
        if (uidObj == null) {
            throw new BizException(BizErrorEnum.NEED_LOGIN);
        }
        return (Long) uidObj;
    }


}
