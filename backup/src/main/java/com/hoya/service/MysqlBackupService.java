package com.hoya.service;

public interface MysqlBackupService {
    /**
     * 备份数据库
     * @param host
     * @param username
     * @param password
     * @param backupFolderPath
     * @param filename
     * @param database
     * @return
     * @throws Exception
     */
    boolean backup(String host, String username, String password, String backupFolderPath,
                   String filename, String database) throws Exception;

    /**
     * 还原数据库
     * @param restoreFilePath
     * @param host
     * @param username
     * @param password
     * @param database
     * @return
     * @throws Exception
     */
    boolean restore(String restoreFilePath, String host, String username,
                    String password, String database) throws Exception;
}
