package com.hoya.constants;

import java.io.File;

public interface BackupConstants {
    /**
     * 备份目录名称
     */
    static final String BACKUP_FOLDER_NAME = "hoya_backup";

    /**
     * 备份目录
     */
    static final String BACKUP_FOLDER = System.getProperty("user.home") + File.separator + BACKUP_FOLDER_NAME + File.separator;

    /**
     * 还原目录，默认就是备份目录
     */
    static final String RESTORE_FOLDER = BACKUP_FOLDER;

    /**
     * 日期格式
     */
    static final String DATE_FORMAT = "yyyy-MM-dd_HHmmss";

    /**
     * SQL拓展名
     */
    static final String SQL_EXT = ".sql";

    /**
     * 默认备份文件名
     */
    static final String BACKUP_FILE_NAME = "hoya" + SQL_EXT;

    /**
     * 默认备份还原目录名称
     */
    static final String DEFAULT_BACKUP_NAME = "backup";

    /**
     * 默认备份还原文件
     */
    static final String DEFAULT_RESTORE_FILE = BACKUP_FOLDER + DEFAULT_BACKUP_NAME + File.separator + BACKUP_FILE_NAME;
}
