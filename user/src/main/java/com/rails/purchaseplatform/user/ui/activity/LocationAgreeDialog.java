package com.rails.purchaseplatform.user.ui.activity;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.rails.purchaseplatform.common.databinding.DialogLocationAgreeBinding;

public class LocationAgreeDialog {

    private Dialog dialog;

    private Context context;

    private String message;

    private String goButton;

    private int goColor;

    private DialogListener listener;

    private DialogLocationAgreeBinding binding;

    private LocationAgreeDialog(Builder builder) {
        this.context = builder.context;
        this.message = builder.message;
        this.goButton = builder.goButton;
        this.goColor = builder.goColor;
        this.listener = builder.listener;
        initDialog(context);
    }

    private void initDialog(Context context) {
        dialog = new Dialog(context, com.rails.purchaseplatform.framwork.R.style.MyDialog);
        LayoutInflater inflater = LayoutInflater.from(context);
        binding = DialogLocationAgreeBinding.inflate(inflater);
        dialog.setContentView(binding.getRoot());
        if (!TextUtils.isEmpty(message))
            binding.message.setText(message);
        if (!TextUtils.isEmpty(goButton))
            binding.go.setText(goButton);
        if (goColor != 0)
            binding.go.setTextColor(goColor);
        onClick();
    }

    void onClick() {
        binding.go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onGo();
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


    public static class Builder {
        Context context;
        String message;
        String goButton;
        int goColor;
        DialogListener listener;

        public Builder context(Context context) {
            this.context = context;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder goButton(String goButton) {
            this.goButton = goButton;
            return this;
        }

        public Builder goColor(int goColor) {
            this.goColor = goColor;
            return this;
        }

        public Builder setListener(DialogListener listener) {
            this.listener = listener;
            return this;
        }

        public LocationAgreeDialog builder() {
            return new LocationAgreeDialog(this);
        }
    }


    public interface DialogListener {
        void onGo();
    }

}
