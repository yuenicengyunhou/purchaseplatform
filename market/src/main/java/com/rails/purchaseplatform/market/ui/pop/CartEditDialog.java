package com.rails.purchaseplatform.market.ui.pop;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.rails.purchaseplatform.common.utils.ToastUtil;
import com.rails.purchaseplatform.market.R;
import com.rails.purchaseplatform.market.databinding.DialogMarketMumberBinding;


/**
 * 两个按钮dialog
 * author :Created by sk.
 * date : 2016/11/2.
 */

public class CartEditDialog {


    DialogMarketMumberBinding bind;

    private Dialog dialog;

    private Context context;
    private String title;
    private String msg;
    private String leftBtn;
    private String rightBtn;
    private int leftColor;
    private int rightColor;
    private DialogListener dialogListener;

    private CartEditDialog(Builder builder) {
        this.context = builder.context;
        this.title = builder.title;
        this.msg = builder.msg;
        this.leftBtn = builder.leftBtn;
        this.rightBtn = builder.rightBtn;
        this.leftColor = builder.leftColor;
        this.rightColor = builder.rightColor;
        this.dialogListener = builder.dialogListener;
        initDialog(context);
    }

    public static class Builder {
        Context context;
        String title;
        String msg;
        String leftBtn;
        String rightBtn;
        int leftColor;
        int rightColor;
        DialogListener dialogListener;

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder context(Context context) {
            this.context = context;
            return this;
        }

        public Builder msg(String msg) {
            this.msg = msg;
            return this;
        }

        public Builder leftBtn(String leftBtn) {
            this.leftBtn = leftBtn;
            return this;
        }

        public Builder rightBtn(String rightBtn) {
            this.rightBtn = rightBtn;
            return this;
        }

        public Builder leftColor(int leftColor) {
            this.leftColor = leftColor;
            return this;
        }

        public Builder rightColor(int rightColor) {
            this.rightColor = rightColor;
            return this;
        }

        public Builder setDialogListener(DialogListener dialogListener) {
            this.dialogListener = dialogListener;
            return this;
        }

        public CartEditDialog builder() {
            return new CartEditDialog(this);
        }
    }


    private CartEditDialog(Context context, String title, String msg, DialogListener dialogListener) {
        this.dialogListener = dialogListener;
        dialog = new Dialog(context, R.style.MyDialog);
        LayoutInflater inflater = LayoutInflater.from(context);
        bind = DialogMarketMumberBinding.inflate(inflater);
        dialog.setContentView(bind.getRoot());
        bind.tvTitle.setText(title);
        bind.etMsg.setHint(msg);

        onClick();
    }


    private void initDialog(Context context) {
        dialog = new Dialog(context, R.style.MyDialog);
        LayoutInflater inflater = LayoutInflater.from(context);
        bind = DialogMarketMumberBinding.inflate(inflater);
        dialog.setContentView(bind.getRoot());
        if (!TextUtils.isEmpty(title))
            bind.tvTitle.setText(title);

        if (!TextUtils.isEmpty(msg))
            bind.etMsg.setHint(msg);

        if (!TextUtils.isEmpty(leftBtn))
            bind.rbLeft.setText(leftBtn);

        if (!TextUtils.isEmpty(rightBtn))
            bind.rbRight.setText(rightBtn);

        if (leftColor != 0)
            bind.rbLeft.setTextColor(leftColor);

        if (rightColor != 0)
            bind.rbRight.setTextColor(rightColor);

        onClick();
    }


    private void onClick() {
        bind.rbLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogListener != null)
                    dialogListener.onLeft();
                close();
            }
        });

        bind.rbRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String paw = bind.etMsg.getText().toString().trim();
                if (TextUtils.isEmpty(paw) || !TextUtils.isDigitsOnly(paw)) {
                    ToastUtil.show(context, "请输入正确信息");
                    return;
                }
                if (dialogListener != null)
                    dialogListener.onRight(paw);
                close();
            }
        });
    }


    public void show() {
        if (!dialog.isShowing()) {
            dialog.show();
        }
    }

    public void close() {
        dialog.dismiss();
    }

    /**
     * 左右按钮触发事件
     */
    public interface DialogListener {
        void onLeft();


        /**
         * 传递密码信息
         *
         * @param paw
         */
        void onRight(String paw);
    }
}
