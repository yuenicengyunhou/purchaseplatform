package com.rails.purchaseplatform.common.pop;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.rails.purchaseplatform.common.R;
import com.rails.purchaseplatform.common.databinding.DialogAlterBinding;


/**
 * 两个按钮dialog
 * author :Created by sk.
 * date : 2016/11/2.
 */

public class AlterDialog {

    private Dialog dialog;

    private Context context;
    private String title;
    private String msg;
    private String leftBtn;
    private String rightBtn;
    private int leftColor;
    private int rightColor;
    private DialogListener dialogListener;


    DialogAlterBinding binding;

    private AlterDialog(Builder builder) {
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

        public AlterDialog builder() {
            return new AlterDialog(this);
        }
    }


    @Deprecated
    private AlterDialog(Context context, String title, String msg, DialogListener dialogListener) {
        this.dialogListener = dialogListener;
        dialog = new Dialog(context, R.style.dialog);
        LayoutInflater inflater = LayoutInflater.from(context);
        binding = DialogAlterBinding.inflate(inflater);
        dialog.setContentView(binding.getRoot());
        binding.tvTitle.setText(title);
        binding.tvMsg.setText(msg);

        onClick();
    }


    private void initDialog(Context context) {
        dialog = new Dialog(context, R.style.MyDialog);
        LayoutInflater inflater = LayoutInflater.from(context);
        binding = DialogAlterBinding.inflate(inflater);
        dialog.setContentView(binding.getRoot());
        if (!TextUtils.isEmpty(title))
            binding.tvTitle.setText(title);

        if (!TextUtils.isEmpty(msg))
            binding.tvMsg.setText(msg);

        if (!TextUtils.isEmpty(leftBtn))
            binding.rbLeft.setText(leftBtn);

        if (!TextUtils.isEmpty(rightBtn))
            binding.rbRight.setText(rightBtn);

        if (leftColor != 0)
            binding.rbLeft.setTextColor(leftColor);

        if (rightColor != 0)
            binding.rbRight.setTextColor(rightColor);

        onClick();
    }


    void onClick() {
        binding.rbLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogListener.onLeft();
                close();
            }
        });

        binding.rbRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogListener.onRight();
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

        void onRight();
    }
}
