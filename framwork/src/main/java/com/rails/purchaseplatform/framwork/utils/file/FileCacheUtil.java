package com.rails.purchaseplatform.framwork.utils.file;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * JSON数据缓存序列化
 */
public class FileCacheUtil {

    public static final String FILE_EXTENSION = ".cache";
    public static final String DEF_DIR = "cache";

    private static FileCacheUtil manager;
    private String fileName;
    private Context mContext;

    private FileCacheUtil(Context context) {
        this.mContext = context;
    }

    public static FileCacheUtil getInstance(Context context) {
        if (manager == null) {
            manager = new FileCacheUtil(context.getApplicationContext());
        }
        return manager;
    }

    /**
     * 写入默认路径文件中
     *
     * @param object
     * @param filename
     */
    public void writeObject(Object object, String filename) {
        File file = FileInnerUtil.getInstance().getFile(mContext, DEF_DIR, filename + FILE_EXTENSION);
        if (file != null && file.exists()) {
            file.delete();
        }
        if (file == null)
            file = FileExtraUtil.getInstance().getFile(mContext, DEF_DIR, filename + FILE_EXTENSION);

        if (object != null) {
            try {
                FileOutputStream fileWriter = new FileOutputStream(file);
                ObjectOutputStream outputStream = new ObjectOutputStream(fileWriter);
                outputStream.writeObject(object);
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    /**
     * 写入自定义文件夹中
     *
     * @param object
     */
    public void writeObject(Object object, File file) {
        if (file != null)
            if (object != null) {
                try {
                    file.createNewFile();
                    FileOutputStream fileWriter = new FileOutputStream(file);
                    ObjectOutputStream outputStream = new ObjectOutputStream(fileWriter);
                    outputStream.writeObject(object);
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

    }

    /**
     * 写入自定义文件夹中
     *
     * @param object
     * @param dir
     * @param filename
     */
    public void writeObject(Object object, String dir, String filename) {
        File file = FileExtraUtil.getInstance().getFile(mContext, dir, filename + FILE_EXTENSION);
        if (file != null && file.exists()) {
            file.delete();
        }
        if (file == null)
            file = FileExtraUtil.getInstance().getFile(mContext, DEF_DIR, filename + FILE_EXTENSION);
        if (object != null) {
            try {
                file.createNewFile();
                FileOutputStream fileWriter = new FileOutputStream(file);
                ObjectOutputStream outputStream = new ObjectOutputStream(fileWriter);
                outputStream.writeObject(object);
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 读取文件json
     *
     * @param filename 文件名称
     * @return
     */
    public Object readObject(String filename) {
        File filePath = FileInnerUtil.getInstance().getFile(mContext, DEF_DIR, filename + FILE_EXTENSION);
        if (filePath == null || !filePath.exists()) {
            return null;
        }
        FileInputStream fileInputStream = null;
        ObjectInputStream inputStream = null;
        try {
            fileInputStream = new FileInputStream(filePath);
            inputStream = new ObjectInputStream(fileInputStream);
            Object lists = inputStream.readObject();
            inputStream.close();
            return lists;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 删除本地缓存
     */
    public void delFile(String filename) {
        File filePath = FileExtraUtil.getInstance().getFilepath(mContext, DEF_DIR, filename + FILE_EXTENSION);
        if (filePath == null || !filePath.exists()) {
            return;
        } else
            filePath.delete();
    }


    /**
     * 读取文件json
     *
     * @param file 文件路径
     * @return
     */
    public Object readUrlObject(File file) {
        if (file == null || !file.exists()) {
            return null;
        }
        FileInputStream fileInputStream = null;
        ObjectInputStream inputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            inputStream = new ObjectInputStream(fileInputStream);
            Object lists = inputStream.readObject();
            inputStream.close();
            return lists;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 读取文件json
     *
     * @param url 文件名称
     * @return
     */
    public Object readUrlObject(String url) {
        File filePath = new File(url);
        if (filePath == null || !filePath.exists()) {
            return null;
        }
        FileInputStream fileInputStream = null;
        ObjectInputStream inputStream = null;
        try {
            fileInputStream = new FileInputStream(filePath);
            inputStream = new ObjectInputStream(fileInputStream);
            Object lists = inputStream.readObject();
            inputStream.close();
            return lists;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
