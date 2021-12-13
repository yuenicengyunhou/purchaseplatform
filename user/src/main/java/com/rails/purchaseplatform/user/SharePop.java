package com.rails.purchaseplatform.user;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import com.rails.purchaseplatform.framwork.base.BasePop;
import com.rails.purchaseplatform.user.databinding.PopShareBinding;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SharePop extends BasePop<PopShareBinding> {

    @Override
    protected void initialize(Bundle bundle) {
        binding.tvDismiss.setOnClickListener(v -> dismiss());
        binding.tvToHome.setOnClickListener(v -> savePicToAlbum(R.drawable.pic_qr_code, getContext()));
        binding.ivIcon.setOnClickListener(v -> savePicToAlbum(R.drawable.pic_qr_code, getContext()));
    }

    private void savePicToAlbum(int picRes, Context context) {
        try {
            InputStream inputStream = context.getResources().openRawResource(picRes);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            byte[] buffer = new byte[1024];
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.MediaColumns.DISPLAY_NAME, "国铁商城.png");
                values.put(MediaStore.MediaColumns.MIME_TYPE, "image/png");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS);
                } else {
                    values.put(MediaStore.MediaColumns.DATA, "${Environment.getExternalStorageDirectory().path}/${Environment.DIRECTORY_DCIM}/$displayName");
                }
                ContentResolver contentResolver = context.getContentResolver();
                Uri uri = contentResolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values);
                if (null != uri) {
                    OutputStream outputStream = contentResolver.openOutputStream(uri);
                    if (null != outputStream) {
                        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
                        int bytes = bufferedInputStream.read(buffer);
                        while (bytes >= 0) {
                            bufferedOutputStream.write(buffer, 0, bytes);
                            bufferedOutputStream.flush();
                            bytes = bufferedInputStream.read(buffer);
                        }
                        bufferedOutputStream.close();
                        outputStream.close();
                        Toast.makeText(getContext(), "二维码已保存至相册", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
//            else {
//                File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
//                String absolutePath = directory.getAbsolutePath();
//                String path = absolutePath + File.separator + fileName;
//                FileOutputStream fileOutputStream = new FileOutputStream(path);
//                while (inputStream.read(buffer) != -1) {
//                    fileOutputStream.write(buffer);
//                }
//                fileOutputStream.close();
//
//            }
//            bufferedInputStream.close();
//            inputStream.close();
//            Toast.makeText(context, "文件已保存至文件管理中的Download文件夹", Toast.LENGTH_SHORT).show();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        dismiss();
    }
}
