package com.haoya.demo.app.api.v1.admin.sys;

import com.haoya.demo.app.exception.AppException;
import com.haoya.demo.app.exception.AppExceptionFound;
import com.haoya.demo.app.sys.entity.SysParam;
import com.haoya.demo.app.sys.entity.SysRole;
import com.haoya.demo.app.sys.repository.SysParamRepository;
import com.haoya.demo.common.utils.PageVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/admin/sys/param")
public class AdminSysParamAPIController {

    /**
     * 获取角色表格
     * @param page 分页从1开始
     * @param limit 每页条数
     * @param domain 搜索条件域名，支持SQL模糊匹配
     * @param title 搜索条件标题，支持SQL模糊匹配
     * @return
     */
    @GetMapping("/list.json")
    public PageVO<SysParam> list(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) String domain,
            @RequestParam(required = false) String title) {

        if (null == page) {
            page = 0;
            limit = Integer.MAX_VALUE;
        } else {
            --page;   //page从1开始，数据库从0开始
        }

        try {
            PageRequest pageable = PageRequest.of(page, limit);
            if (StringUtils.isEmpty(domain) && StringUtils.isEmpty(title)) {
                return new PageVO<>(sysParamRepository.findAll(pageable));
            } else {
                if (StringUtils.isEmpty(domain)) {
                    domain = "%";
                }
                if (StringUtils.isEmpty(title)) {
                    title = "%";
                }

                return new PageVO<>(sysParamRepository.findByDomainLikeAndTitleLike(domain, title, pageable));
            }
        } catch (Exception e) {
            throw AppException.ServerError;
        }
    }

    @PostMapping("/add.json")
    public SysParam add(@RequestBody SysParam sysParam) {
        try {
            return sysParamRepository.save(sysParam);
        } catch (DataIntegrityViolationException e) {
            throw new AppExceptionFound("域名已存在");
        } catch (Exception e) {
            throw AppException.ServerError;
        }
    }

    @PostMapping("/modify")
    public void modify(@RequestBody SysParam sysParam) {
        try {
            sysParamRepository.save(sysParam);
        } catch (DataIntegrityViolationException e) {
            throw new AppExceptionFound("域名已存在");
        } catch (Exception e) {
            throw AppException.ServerError;
        }
    }

    @GetMapping("/{id}.json")
    public SysParam get(@PathVariable(name = "id") BigInteger id) {
        Optional<SysParam> sysParamOptional = sysParamRepository.findById(id);
        if(!sysParamOptional.isPresent()) {
            throw AppException.NotFound;
        }

        return sysParamOptional.get();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable(name = "id") BigInteger id) {
        try {
            sysParamRepository.deleteById(id);
        } catch (Exception e) {
            throw AppException.NotFound;
        }
    }

    @Autowired
    private SysParamRepository sysParamRepository;
}
