package com.hoya.service.impl;

import com.hoya.service.MysqlBackupService;
import com.hoya.utils.MySqlBackupRestoreUtils;
import org.springframework.stereotype.Service;

@Service
public class MysqlBackupServiceImpl implements MysqlBackupService {
    @Override
    public boolean backup(String host, String username, String password, String backupFolderPath,
                          String filename, String database) throws Exception {

        return MySqlBackupRestoreUtils.backup(host, username, password, backupFolderPath, filename, database);
    }

    @Override
    public boolean restore(String restoreFilePath, String host, String username,
                           String password, String database) throws Exception {

        return MySqlBackupRestoreUtils.restore(restoreFilePath, host, username, password, database);
    }
}
