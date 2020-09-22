package com.hoya.utils;

import java.io.*;

public class MySqlBackupRestoreUtils {

    /**
     * 备份数据库
     *
     * @param host             主机地址
     * @param username         数据库用户名
     * @param password         数据库密码
     * @param backupFolderPath 备份路径
     * @param filename         备份文件名
     * @param database         备份数据库
     * @return
     * @throws Exception
     */
    public static boolean backup(String host, String username, String password, String backupFolderPath, String filename,
                                 String database) throws Exception {
        File backupFolderFile = new File(backupFolderPath);
        if (!backupFolderFile.exists()) {
            // 如果目录不存在则创建
            backupFolderFile.mkdirs();
        }

        if (!backupFolderPath.endsWith(File.separator) && !backupFolderPath.endsWith("/")) {
            backupFolderPath = backupFolderPath + File.separator;
        }

        // 拼接命令行的命令
        String backupFilePath = backupFolderPath + filename;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("mysqldump --opt ").append(" --add-drop-database ").append(" --add-drop-table ");
        stringBuilder.append(" -h").append(host).append(" -u").append(username).append(" -p").append(password);
        stringBuilder.append(" --result-file=").append(backupFilePath).append(" --default-character-set=utf8 ").append(database);
        String mysqlCmd = stringBuilder.toString();

        // 调用外部执行 exe 文件的 Java API
        String[] cmd = getCommand(mysqlCmd);
        Process process = Runtime.getRuntime().exec(cmd);
        int errCode = process.waitFor();
        if (errCode != 0) {
            return false;
        }

        return true;
    }

    /**
     * 备份数据库
     *
     * @param restoreFilePath 数据库备份的脚本路径
     * @param host            主机地址
     * @param username        数据库用户名
     * @param password        数据库密码
     * @param database        备份数据库
     * @return
     * @throws Exception
     */
    public static boolean restore(String restoreFilePath, String host, String username, String password, String database)
            throws Exception {
        File restoreFile = new File(restoreFilePath);
        if (!restoreFile.exists()) return false;

        if (restoreFile.isDirectory()) {
            for (File file : restoreFile.listFiles()) {
                if (file.exists() && file.getPath().endsWith(".sql")) {
                    restoreFilePath = file.getAbsolutePath();
                    break;
                }
            }
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("mysql -h").append(host).append(" -u").append(username).append(" -p").append(password);
        stringBuilder.append(" ").append(database).append(" < ").append(restoreFilePath);
        String mysqlCmd = stringBuilder.toString();

        String[] cmd = getCommand(mysqlCmd);
        Process process = Runtime.getRuntime().exec(cmd);
        int errCode = process.waitFor();
        if (errCode != 0) {
            return false;
        }

        return true;
    }

    private static String[] getCommand(String command) {
        String os = System.getProperty("os.name");
        String shell = "/bin/bash";
        String c = "-c";
        if (os.toLowerCase().startsWith("win")) {
            shell = "cmd";
            c = "/c";
        }
        String[] cmd = {shell, c, command};
        return cmd;
    }

    public static void main(String[] args) throws Exception {
        String host = "localhost";
        String userName = "root";
        String password = "root";
        String database = "test";

        System.out.println("开始备份");
        String backupFolderPath = "E:/dev/";
        String fileName = "test.sql";
        if (false == backup(host, userName, password, backupFolderPath, fileName, database)) {
            System.out.println("备份失败");
            return;
        }

        System.out.println("备份成功");

        System.out.println("开始还原");
        String restoreFilePath = "E:/dev/";
        if (false == restore(restoreFilePath, host, userName, password, database)) {
            System.out.println("还原失败");
        }

        System.out.println("还原成功");

    }

}

