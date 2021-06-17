package com.rails.purchaseplatform.common.contentProviderr;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.Nullable;

public class SmsObserver extends ContentObserver {

    private final Context mContext;
    private String mPlatformPhone;
    private String mVerifyCode;
    private VerifyCodeListener verifyCodeListener;

    public void setVerifyCodeListener(VerifyCodeListener verifyCodeListener) {
        this.verifyCodeListener = verifyCodeListener;
    }

    /**
     * Creates a content observer.
     *
     * @param handler The handler to run {@link #onChange} on, or null if none.
     */
    public SmsObserver(Handler handler, Context context,String platformPhone,String verifyCode) {
        super(handler);
        this.mContext = context;
        this.mPlatformPhone = platformPhone;
        this.mVerifyCode = verifyCode;
    }

    @Override
    public void onChange(boolean selfChange, @Nullable Uri uri) {
        super.onChange(selfChange, uri);
        if (null == uri) {
            return;
        }
        if (uri.toString().equals("content://sms/raw")) {
            return;
        }
        Uri inboxUri = Uri.parse("content://sms/inbox");
        Cursor cursor = mContext.getContentResolver().query(inboxUri, null, null, null, "date desc");
        if (null != cursor) {
            if (cursor.moveToFirst()) {
                String address = cursor.getString(cursor.getColumnIndex("address"));
                String body = cursor.getString(cursor.getColumnIndex("body"));
                Log.e("WQ", "----" + address + "       body===" + body);
                if (address.equals(mPlatformPhone) && body.contains(mVerifyCode)) {
                    if (null != verifyCodeListener) {
                        verifyCodeListener.onSuccess();
                    }
                }
            }
            cursor.close();
        }
    }

    public interface VerifyCodeListener{
        void onSuccess();
    }
}
