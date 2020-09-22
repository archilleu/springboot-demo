package com.hoya.controller;

import com.hoya.config.BackupDataSourceProperties;
import com.hoya.constants.BackupConstants;
import com.hoya.core.exception.*;
import com.hoya.service.MysqlBackupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class MysqlBackupController {

    @Autowired
    private BackupDataSourceProperties properties;

    @Autowired
    private MysqlBackupService mysqlBackupService;

    @GetMapping("/backup")
    public HttpResult backup() {
        String backupFolderName = BackupConstants.DEFAULT_BACKUP_NAME + "_" +
                (new SimpleDateFormat(BackupConstants.DATE_FORMAT)).format(new Date());
        String backupFolderPath = BackupConstants.BACKUP_FOLDER + backupFolderName + File.separator;
        String fileName = BackupConstants.BACKUP_FILE_NAME;
        try {
            boolean success = mysqlBackupService.backup(properties.getHost(), properties.getUsername(),
                    properties.getPassword(), backupFolderPath, fileName, properties.getDatabase());
            if (false == success) {
                throw new AppExceptionServerError("数据库备份失败");
            }
        }catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppExceptionServerError(e.getMessage());
        }

        return HttpResult.OK;
    }

    @GetMapping("/restore")
    public HttpResult restore(@RequestParam String name) {
        String restoreFilePath = BackupConstants.RESTORE_FOLDER + name;
        try {
            boolean success = mysqlBackupService.restore(restoreFilePath, properties.getHost(), properties.getUsername(),
                    properties.getPassword(), properties.getDatabase());
            if (false == success) {
                throw new AppExceptionServerError("数据库备份失败");
            }
        }catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppExceptionServerError(e.getMessage());
        }

        return HttpResult.OK;
    }

    @GetMapping("/findRecords")
    public HttpResult findRecords() {
        List<Map<String, Object>> backupRecords = new ArrayList<>();
        File restoreFolder = new File(BackupConstants.RESTORE_FOLDER);
        if (restoreFolder.exists()) {
            for (File file : restoreFolder.listFiles()) {
                Map<String, Object> backup = new HashMap<>();
                backup.put("name", file.getName());
                backup.put("time", file.lastModified());
                backupRecords.add(backup);
            }
        }

        backupRecords.sort((o1, o2) -> {
            Long l1 = (Long) o1.get("time");
            Long l2 = (Long) o2.get("time");
            return (int) (l2 - l1);
        });

        return new HttpResult(backupRecords);
    }

    @GetMapping("/delete")
    public HttpResult delete(@RequestParam String name) {
        String restoreFilePath = BackupConstants.BACKUP_FOLDER + name;
        try {
            File file = new File(restoreFilePath);
            FileSystemUtils.deleteRecursively(file);
        } catch (Exception e) {
            throw new AppExceptionNotFound("备份文件没有找到");
        }

        return HttpResult.OK;
    }

}
