package com.haoya.demo.app.api.v1.admin.sys;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.haoya.demo.app.exception.AppException;
import com.haoya.demo.app.exception.AppExceptionFound;
import com.haoya.demo.app.sys.entity.*;
import com.haoya.demo.app.sys.entity.VO.SysUserVO;
import com.haoya.demo.app.sys.repository.*;
import com.haoya.demo.common.utils.PageVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/sys/user")
public class AdminSysUserAPIController {

    @GetMapping("/list.json")
    public PageVO<SysUser> list(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String nickname,
            @RequestParam(required = false) BigInteger deptId) {

        if (null == page) {
            page = 0;
            limit = Integer.MAX_VALUE;
        } else {
            --page;   //page从1开始，数据库从0开始
        }

        try {
            PageRequest pageable = PageRequest.of(page, limit);
            if (StringUtils.isEmpty(username) && StringUtils.isEmpty(nickname)) {
                if(null != deptId) {
                    return new PageVO<>(sysUserRepository.findByDeptId(deptId, pageable));
                }

                return new PageVO<>(sysUserRepository.findAll(pageable));
            } else {
                if (StringUtils.isEmpty(username)) {
                    username = "%";
                }
                if (StringUtils.isEmpty(nickname)) {
                    nickname = "%";
                }

                return new PageVO<>(sysUserRepository.findByUsernameLikeAndNicknameLike(username, nickname, pageable));
            }
        } catch (Exception e) {
            throw AppException.ServerError;
        }
    }

    @PostMapping("/add.json")
    public SysUser add(@RequestBody SysUserVO sysUserVO) {
        try {
            SysUser sysUser = new SysUser();
            sysUser.setUsername(sysUserVO.getUsername());
            sysUser.setNickname(sysUserVO.getNickname());
            sysUser.setPassword(bCryptPasswordEncoder.encode(sysUserVO.getPassword()));
            sysUser.setEmail(sysUserVO.getEmail());
            sysUser.setMobile(sysUserVO.getMobile());
            sysUser.setStatus(sysUserVO.getStatus());
            sysUser.setCreateTime(new Timestamp(System.currentTimeMillis()));
            sysUserRepository.save(sysUser);

            SysDept sysDept = sysUserVO.getSysDept();
            if(null != sysDept) {
                try {
                    sysDeptUserRepository.save(new SysDeptUser(sysDept.getDeptId(), sysUser.getUserId()));
                } catch (DataIntegrityViolationException e) {
                } catch (Exception e) {
                    throw e;
                }
            }

            return sysUser;
        } catch (DataIntegrityViolationException e) {
            throw new AppExceptionFound("角色已存在");
        } catch (Exception e) {
            throw AppException.ServerError;
        }
    }

    @PostMapping("/modify")
    public void modify(@RequestBody SysUserVO sysUserVO) {
        try {
            SysUser old = sysUserRepository.findById(sysUserVO.getUserId()).get();
            old.setUsername(sysUserVO.getUsername());
            old.setNickname(sysUserVO.getNickname());
            old.setEmail(sysUserVO.getEmail());
            old.setMobile(sysUserVO.getMobile());
            old.setStatus(sysUserVO.getStatus());
            sysUserRepository.save(old);

            SysDept sysDept = sysUserVO.getSysDept();
            if(null != sysDept) {
                try {
                    sysDeptUserRepository.save(new SysDeptUser(sysDept.getDeptId(), sysUserVO.getUserId()));
                } catch (DataIntegrityViolationException e) {
                } catch (Exception e) {
                    throw e;
                }
            }
        } catch (DataIntegrityViolationException e) {
            throw new AppExceptionFound("角色已存在");
        } catch (Exception e) {
            throw AppException.ServerError;
        }
    }

    @GetMapping("/{id}.json")
    public SysUserVO get(@PathVariable(name = "id") BigInteger id) {
        try {
            SysUser sysUser = sysUserRepository.findById(id).get();
            SysDept sysDept = sysDeptRepository.findByUserId(sysUser.getUserId());
            return new SysUserVO(sysUser, sysDept, null);
        } catch (Exception e) {
            throw AppException.NotFound;
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable(name = "id") BigInteger id) {
        try {
            sysUserRepository.deleteById(id);
        } catch (Exception e) {
            throw AppException.NotFound;
        }
    }

    @GetMapping("/roles")
    public String roles(@RequestParam(required = true)BigInteger userId) {
        PageRequest pageable = PageRequest.of(0, Integer.MAX_VALUE);
        JSONObject json = (JSONObject)JSONObject.toJSON(new PageVO<>(sysRoleRepository.findAll(pageable)));
        JSONArray list = json.getJSONArray("list");

        List<SysUserRole> userRoles = sysUserRoleRepository.findByUserId(userId);
        for(SysUserRole userRole : userRoles) {
            for(int i=0; i< list.size(); i++) {
                JSONObject temp = list.getJSONObject(i);
                if(temp.getBigInteger("roleId") == userRole.getRoleId()) {
                    temp.put("LAY_CHECKED", true);
                }
            }
        }

        return json.toJSONString();
    }

    @PostMapping(value = "/roles", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void saveRoles(
            @RequestParam(required = true)BigInteger userId,
            @RequestBody(required = true)List<SysRole> roles) {
        sysUserRoleRepository.modifyUserRole(userId, roles);
    }

    @Autowired
    private SysUserRepository sysUserRepository;

    @Autowired
    private SysRoleRepository sysRoleRepository;

    @Autowired
    private SysUserRoleRepository sysUserRoleRepository;

    @Autowired
    private SysDeptUserRepository sysDeptUserRepository;

    @Autowired
    private SysDeptRepository sysDeptRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
}
