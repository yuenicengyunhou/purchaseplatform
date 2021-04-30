package com.rails.purchaseplatform.framwork.utils.file;

import android.content.Context;

import java.io.File;
import java.io.IOException;

/**
 * 文件缓存内部存储 一般存储数据库等信息
 * author wangchao
 * email  wangchao@chengantech.com
 * date   on 2018/1/9.
 */

public class FileInnerUtil {

    private static FileInnerUtil fileUtil;

    /**
     * @return
     */
    public static FileInnerUtil getInstance() {
        if (fileUtil == null) {
            fileUtil = new FileInnerUtil();
        }
        return fileUtil;
    }

    /**
     * 获取文件夹
     *
     * @param context
     * @param directory
     * @return
     */
    public File getDirectory(Context context, String directory) {
        File urlPath = null;
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(context.getFilesDir());
        stringBuffer.append(File.separator);
        stringBuffer.append(context.getPackageName());
        stringBuffer.append(File.separator);
        stringBuffer.append(directory);
        urlPath = new File(stringBuffer.toString());
        if (urlPath.exists())
            return urlPath;
        else
            createDirFile(urlPath.getAbsolutePath());
        return urlPath;
    }


    /**
     * 获取文件地址,如果不存在创建
     *
     * @param context
     * @param directory
     * @param fileName
     * @return
     */
    public File getFile(Context context, String directory, String fileName) {
        File urlPath = null;
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(context.getFilesDir());
        stringBuffer.append(File.separator);
        stringBuffer.append(directory);
        createDirFile(stringBuffer.toString());
        urlPath = new File(stringBuffer.toString(), fileName);
        urlPath = createNewFile(urlPath);
        return urlPath;
    }


    /**
     * 创建根目录
     *
     * @param path 目录路径
     */
    private void createDirFile(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    /**
     * 创建文件
     *
     * @param path 文件路径
     * @return 创建的文件
     */
    private File createNewFile(File path) {
        if (!path.exists()) {
            try {
                path.createNewFile();
            } catch (IOException e) {
                return null;
            }
        }
        return path;
    }
}
