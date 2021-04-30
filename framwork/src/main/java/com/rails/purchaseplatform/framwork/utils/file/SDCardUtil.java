package com.rails.purchaseplatform.framwork.utils.file;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;

import java.io.File;


/**
 * sd卡
 * author   wangchao
 * email    wangchao@chengantech.com
 * date     2017/11/30
 */
public class SDCardUtil {
    // SD卡的最小剩余容量大小1MB
    private final static long DEFAULT_LIMIT_SIZE = 1;

    private static SDCardUtil instance;
    private final Context context;

    public static SDCardUtil getInstance(Context context) {
        if (instance == null) {
            instance = new SDCardUtil(context);
        }
        return instance;
    }

    private SDCardUtil(Context context) {
        this.context = context;
    }


    /**
     * 判断SD卡是否可用
     *
     * @param context
     * @return
     */
    public static boolean isSDCardAvaiable(Context context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            if (getSDFreeSize() > DEFAULT_LIMIT_SIZE) {
                return true;
            } else {
                // ToastUtil.showToast(context, "SD卡容量不足，请检查");
                return false;
            }
        } else {
            // ToastUtil.showToast(context, "SD卡不存在或者不可用");
            return false;
        }
    }

    /**
     * 获取SDCard的剩余大小
     *
     * @return 多少MB
     */
    @SuppressWarnings("deprecation")
    public static long getSDFreeSize() {
        // 取得SD卡文件路径
        File path = Environment.getExternalStorageDirectory();
        StatFs sf = new StatFs(path.getPath());
        // 获取单个数据块的大小(Byte)
        long blockSize = sf.getBlockSize();
        // 空闲的数据块的数量
        long freeBlocks = sf.getAvailableBlocks();
        // 返回SD卡空闲大小
        return (freeBlocks * blockSize) / 1024 / 1024; // 单位MB
    }

    /**
     * 获取SD卡的总容量
     *
     * @return
     */
    @SuppressWarnings("deprecation")
    public static long getSDAllSize() {
        // 取得SD卡文件路径
        File path = Environment.getExternalStorageDirectory();
        StatFs sf = new StatFs(path.getPath());
        // 获取单个数据块的大小(Byte)
        long blockSize = sf.getBlockSize();
        // 获取所有数据块数
        long allBlocks = sf.getBlockCount();
        // 返回SD卡大小
        return (allBlocks * blockSize) / 1024 / 1024; // 单位MB
    }


    /**
     * 获取SDCard路径
     *
     * @return
     */
    public static File getSDCard(Context mContext) {
        if (isSDCardAvaiable(mContext)) {
            return Environment.getExternalStorageDirectory();
        }
        return null;
    }

    /**
     * 获取SDCard路径
     *
     * @return
     */
    public static String getSDCardPath(Context mContext) {
        if (isSDCardAvaiable(mContext)) {
            return Environment.getExternalStorageDirectory().getPath();
        }
        return "";
    }


}
