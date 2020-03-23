package com.haoya.demo.app.api.v1.sys.api.v1.admin.sys;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.haoya.demo.app.exception.AppException;
import com.haoya.demo.app.exception.AppExceptionFound;
import com.haoya.demo.app.sys.entity.SysRole;
import com.haoya.demo.app.sys.entity.SysUser;
import com.haoya.demo.app.sys.entity.SysUserRole;
import com.haoya.demo.app.sys.repository.SysRoleRepository;
import com.haoya.demo.app.sys.repository.SysUserRepository;
import com.haoya.demo.app.sys.repository.SysUserRoleRepository;
import com.haoya.demo.common.utils.PageVO;
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
            @RequestParam(required = false) String nickname) {

        if (null == page) {
            page = 0;
            limit = Integer.MAX_VALUE;
        } else {
            --page;   //page从1开始，数据库从0开始
        }

        try {
            PageRequest pageable = PageRequest.of(page, limit);
            if (StringUtils.isEmpty(username) && StringUtils.isEmpty(nickname)) {
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
    public SysUser add(@RequestBody SysUser sysUser) {
        sysUser.setCreateTime(new Timestamp(System.currentTimeMillis()));
        try {
            sysUser.setPassword(bCryptPasswordEncoder.encode(sysUser.getPassword()));
            return sysUserRepository.save(sysUser);
        } catch (DataIntegrityViolationException e) {
            throw new AppExceptionFound("用户已存在");
        } catch (Exception e) {
            throw AppException.ServerError;
        }
    }

    @PostMapping("/modify")
    public void modify(@RequestBody SysUser sysUser) {
        try {
            SysUser old = sysUserRepository.findById(sysUser.getUserId()).get();
            sysUser.setCreateTime(old.getCreateTime());
            sysUser.setPassword(sysUser.getPassword());
            sysUser.setRoles(old.getRoles());
            sysUserRepository.save(sysUser);
        } catch (DataIntegrityViolationException e) {
            throw new AppExceptionFound("角色已存在");
        } catch (Exception e) {
            throw AppException.ServerError;
        }
    }

    @GetMapping("/{id}.json")
    public SysUser get(@PathVariable(name = "id") BigInteger id) {
        try {
            return sysUserRepository.findById(id).get();
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

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
}
