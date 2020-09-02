package com.hoya.admin.util;

import com.hoya.admin.model.BaseModel;
import com.hoya.core.exception.AppExceptionBadRequest;

import java.util.List;

public class ParameterCheck {
    /**
     * 对象不能为空检查
     * @param objects
     * @throws AppExceptionBadRequest
     */
    public static void modelNotNull(Object... objects) throws AppExceptionBadRequest {
        for (Object object : objects) {
            if (null == object) {
                throw new AppExceptionBadRequest("参数不能为空");
            }
        }
    }

    /**
     * Model的id不能为空检查
     * @param records
     * @throws AppExceptionBadRequest
     */
    public static void modelIdNotNull(List<? extends BaseModel> records) throws AppExceptionBadRequest {
        for (BaseModel baseModel : records) {
            if (null == baseModel.getId()) {
                throw new AppExceptionBadRequest("id不能为空");
            }
        }
    }
}
