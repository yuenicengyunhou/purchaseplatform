package com.rails.purchaseplatform.framwork.utils.file;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.IOException;

/**
 * 文件缓存的文件
 */
public class FileExtraUtil {

    public static final String BASE_CACHE_FILE = Environment.getExternalStorageDirectory().getAbsolutePath();

    //图片文件缓存
    public static final String IMAGE_CACHE = "imgCache";
    //数据文件缓存
    public static final String FILE_CACHE = "fileCache";
    //语音文件缓存
    public static final String RECORD_CACHE = "recordCache";
    public static final String IMAGE_TEMP = "imgTemp";

    private static FileExtraUtil fileUtil = null;

    /**
     * @return
     */
    public static FileExtraUtil getInstance() {
        if (fileUtil == null) {
            fileUtil = new FileExtraUtil();
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
        if (isSdcardExist()) {
            String path = BASE_CACHE_FILE + File.separator + context.getPackageName() + File.separator + directory;
            urlPath = new File(path);
            if (urlPath.exists())
                return urlPath;
            else
                createDirFile(urlPath.getAbsolutePath());
        }
        return urlPath;
    }

    /**
     * 获取隐藏文件夹目录
     *
     * @param context
     * @param directory
     * @return
     */
    public File getHideDirectory(Context context, String directory) {
        File urlPath = null;
        if (isSdcardExist()) {
            String path = BASE_CACHE_FILE + File.separator + context.getPackageName() + File.separator + "." + directory;
            urlPath = new File(path);
            if (urlPath.exists())
                return urlPath;
        }
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
    public File getFile(Activity context, String directory, String fileName) {
        File urlPath = null;
        if (isSdcardExist()) {
            String file = BASE_CACHE_FILE + File.separator + context.getPackageName() + File.separator + directory;
            createDirFile(file);
            urlPath = createNewFile(new File(file, fileName));
        }

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
    public File getFile(Fragment context, String directory, String fileName) {
        File urlPath = null;
            if (isSdcardExist()) {
                String file = BASE_CACHE_FILE + File.separator + context.getActivity().getPackageName() + File.separator + directory;
                createDirFile(file);
                urlPath = createNewFile(new File(file, fileName));
            }
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
            if (isSdcardExist()) {
                String file = BASE_CACHE_FILE + File.separator + context.getPackageName() + File.separator + directory;
                createDirFile(file);
                urlPath = createNewFile(new File(file, fileName));
            }
        return urlPath;
    }


    /**
     * 判断文件是否存在
     *
     * @param context
     * @param directory
     * @param fileName
     * @return
     */
    public File getFilepath(Context context, String directory, String fileName) {
        File urlPath = null;
        if (isSdcardExist()) {
            String file = BASE_CACHE_FILE + File.separator + context.getPackageName() + File.separator + directory;
            createDirFile(file);
            urlPath = new File(file, fileName);
            if (urlPath.exists())
                return urlPath;
            else
                return null;
        }
        return null;
    }


    /**
     * 删除整个文件夹以及中所用文件
     *
     * @param file
     */
    public static void delFile(File file) {
        if (!file.exists())
            return;
        if (file.isFile()) {
            file.delete();
            return;
        }
        if (file.isDirectory()) {
            File[] childFile = file.listFiles();
            if (childFile == null || childFile.length == 0) {
                file.delete();
                return;
            }
            for (File f : childFile) {
                if (f.isFile() && f.isDirectory())
                    f.delete();
                delFile(f);
            }
            file.delete();
        }
    }

    /**
     * 判断SD是否可以
     *
     * @return
     */
    private boolean isSdcardExist() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
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


    /**
     * 判断是否是图片文件
     *
     * @param fileName
     * @return
     */
    private static boolean isImageFile(String fileName) {
        String fileEnd = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
        if (fileEnd.equalsIgnoreCase("jpg")) {
            return true;
        } else if (fileEnd.equalsIgnoreCase("png")) {
            return true;
        } else if (fileEnd.equalsIgnoreCase("bmp")) {
            return true;
        } else if (fileEnd.equalsIgnoreCase("jpeg")) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 获取文件夹大小
     *
     * @param f
     * @return
     * @throws Exception
     */
    public long getFileSize(File f) throws Exception// 取得文件夹大小
    {
        long size = 0;
        File flist[] = f.listFiles();
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getFileSize(flist[i]);
            } else {
                size = size + flist[i].length();
            }
        }
        return size;
    }

    /**
     * 获取文件的大小
     *
     * @param filePath
     * @return
     */
    public long getFileSize(String filePath) {
        if (TextUtils.isEmpty(filePath))
            return 0;
        File file = new File(filePath);
        return file.length();
    }
}