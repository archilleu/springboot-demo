package com.hoya.admin.model.sys;

import com.hoya.admin.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=true)
public class SysUser extends BaseModel {

    @NotNull
    private String name;

    @NotNull
    private String nickName;

    private String avatar;

    @NotNull
    private String password;

    private String salt;

    private String email;

    private String mobile;

    private Byte status;

    private Long deptId;

    private Byte delFlag;

}
