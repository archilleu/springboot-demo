package com.haoya.demo.app.api.v1.admin.sys;

import com.haoya.demo.app.exception.AppException;
import com.haoya.demo.app.sys.entity.SysDept;
import com.haoya.demo.app.sys.repository.SysDeptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/sys/dept")
public class AdminSysDeptAPIController {

    @GetMapping("/list.json")
    public List<SysDept> list(@RequestParam(required=false)String name) {
        if(StringUtils.isEmpty(name))
            return sysDeptRepository.findAll(new Sort(Sort.Direction.ASC, "orderNum"));

        return sysDeptRepository.findByNameLike(name);
    }

    @PostMapping("/add.json")
    public SysDept add(@RequestBody SysDept sysDept) {
        return sysDeptRepository.save(sysDept);
    }

    @PostMapping("/modify")
    public void modify(@RequestBody SysDept sysDept) {
        sysDeptRepository.save(sysDept);
    }

    @GetMapping("/{id}.json")
    public SysDept get(@PathVariable(name="id") BigInteger id) {
        try {
            return sysDeptRepository.findById(id).get();
        } catch (Exception e) {
            throw AppException.NotFound;
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable(name="id") BigInteger id) {
        sysDeptRepository.deleteDeptAndChild(id);
    }

    @Autowired
    private SysDeptRepository sysDeptRepository;
}
