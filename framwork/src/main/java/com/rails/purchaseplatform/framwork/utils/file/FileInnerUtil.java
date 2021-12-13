package com.rails.purchaseplatform.framwork.utils.file;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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

    /**
     * 保存图片到本地
     */
//    public static void savePicToAlbum(int picRes, Context context, String mimeType) {
//        try {
//            InputStream inputStream = context.getResources().openRawResource(picRes);
//            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
//            byte[] buffer = new byte[1024];
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//                ContentValues values = new ContentValues();
//                values.put(MediaStore.MediaColumns.DISPLAY_NAME, "国铁商城.png");
//                values.put(MediaStore.MediaColumns.MIME_TYPE, mimeType);
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//                    values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DCIM);
//                } else {
//                    values.put(MediaStore.MediaColumns.DATA, "${Environment.getExternalStorageDirectory().path}/${Environment.DIRECTORY_DCIM}/$displayName");
//                }
//                ContentResolver contentResolver = context.getContentResolver();
//                Uri uri = contentResolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values);
//                if (null != uri) {
//                    OutputStream outputStream = contentResolver.openOutputStream(uri);
//                    if (null != outputStream) {
//                        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
//                        int bytes = bufferedInputStream.read(buffer);
//                        while (bytes >= 0) {
//                            bufferedOutputStream.write(buffer, 0, bytes);
//                            bufferedOutputStream.flush();
//                            bytes = bufferedInputStream.read(buffer);
//                        }
//                        bufferedOutputStream.close();
//                        outputStream.close();
//                    }
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
////            else {
////                File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
////                String absolutePath = directory.getAbsolutePath();
////                String path = absolutePath + File.separator + fileName;
////                FileOutputStream fileOutputStream = new FileOutputStream(path);
////                while (inputStream.read(buffer) != -1) {
////                    fileOutputStream.write(buffer);
////                }
////                fileOutputStream.close();
////
////            }
////            bufferedInputStream.close();
////            inputStream.close();
////            Toast.makeText(context, "文件已保存至文件管理中的Download文件夹", Toast.LENGTH_SHORT).show();
////        } catch (FileNotFoundException e) {
////            e.printStackTrace();
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
//    }

}
