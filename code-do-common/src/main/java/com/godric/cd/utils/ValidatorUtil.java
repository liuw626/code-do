package com.godric.cd.utils;

import com.godric.cd.exception.BizErrorEnum;
import com.godric.cd.exception.BizException;
import org.apache.commons.lang3.StringUtils;

public class ValidatorUtil {

//    public static void checkAdminPermission() {
//        Object loginUserInfoObj = SessionUtil.getSessionAttribute(CodeDoConstant.SESSION_KEY_USER_OPEN_ID);
//        if (loginUserInfoObj == null) {
//            throw new BizException(BizErrorEnum.NEED_LOGIN);
//        }
//
//        LoginUserVO loginUser = (LoginUserVO) loginUserInfoObj;
//
//        if (loginUser.getAdmin() == null || !loginUser.getAdmin()) {
//            throw new BizException(BizErrorEnum.NEED_ADMIN_PERMISSION);
//        }
//    }

    public static void checkParamNotNull(Object param, String paramName) {
        if (param instanceof String && StringUtils.isBlank((String)param)) {
            throw new BizException(BizErrorEnum.PARAM_CANNOT_NULL, paramName);
        }
        if (param == null) {
            throw new BizException(BizErrorEnum.PARAM_CANNOT_NULL, paramName);
        }
    }

}
