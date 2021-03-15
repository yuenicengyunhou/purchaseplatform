package com.rails.purchaseplatform.common.multiplecolumnslist;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MultipleColumnsListViewHolder {

    private SparseArray<View> mViews;

    private View mConvertView;

    private Context mContext;

    private MultipleColumnsListViewHolder(Context context, ViewGroup parent, int layoutId) {
        this.mViews = new SparseArray<>();
        this.mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        this.mConvertView.setTag(this);
        this.mContext = context;
    }

    /**
     * @param context
     * @param convertView
     * @param parent
     * @param layoutId
     * @return
     */
    public static MultipleColumnsListViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId) {
        if (convertView == null) {
            return new MultipleColumnsListViewHolder(context, parent, layoutId);
        }
        return (MultipleColumnsListViewHolder) convertView.getTag();
    }

    /**
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public View getConvertView() {
        return mConvertView;
    }

    public TextView getTextView(int viewId) {
        return (TextView) getView(viewId);
    }

    public Button getButton(int viewId) {
        return (Button) getView(viewId);
    }

    public ImageView getImageView(int viewid) {
        return (ImageView) getView(viewid);
    }

    public ImageButton getImageButton(int viewid) {
        return (ImageButton) getView(viewid);
    }


    public LinearLayout getLinearLayout(int viewid) {
        return (LinearLayout) getView(viewid);
    }


    public MultipleColumnsListViewHolder setText(int viewid, String content) {
        getTextView(viewid).setText(content);
        return this;
    }


    public MultipleColumnsListViewHolder setImageResource(int viewid, int resid) {
        getImageView(viewid).setImageResource(resid);
        return this;
    }


//    public MultipleColumnsListViewHolder setImageGlide(int viewId, int img_path, int default_img, int load_type, int dp) {
//        switch (load_type) {
//            case 0:
//                Glide.with(mContext).load(img_path).placeholder(default_img)
//                        .error(default_img).crossFade().into(getImageView(viewId));
//                break;
//            case 1:
//                if (dp <= 0) {
//                    Glide.with(mContext).load(img_path).placeholder(default_img)
//                            .transform(new GlideRoundTransform(mContext))
//                            .error(default_img).crossFade()
//                            .into(getImageView(viewId));
//                } else {
//                    Glide.with(mContext).load(img_path).placeholder(default_img)
//                            .transform(new GlideRoundTransform(mContext, dp))
//                            .error(default_img).crossFade()
//                            .into(getImageView(viewId));
//                }
//                break;
//            case 2:
//                Glide.with(mContext).load(img_path).placeholder(default_img)
//                        .transform(new GlideCircleTransform(mContext))
//                        .error(default_img).crossFade().into(getImageView(viewId));
//                break;
//            default:
//                break;
//        }
//        return this;
//    }

}
