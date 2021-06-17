package com.rails.purchaseplatform.order.adapter.order_details;

import android.content.Context;
import android.view.View;

import androidx.core.content.res.ResourcesCompat;

import com.rails.lib_data.bean.orderdetails.DeliveredFile;
import com.rails.purchaseplatform.framwork.adapter.BaseRecyclerAdapter;
import com.rails.purchaseplatform.order.R;
import com.rails.purchaseplatform.order.databinding.ItemDeliveredBinding;

import static com.rails.purchaseplatform.common.widget.ProgresButton.MODE_PROGRESS;

public class DeliveredAdapter extends BaseRecyclerAdapter<DeliveredFile, ItemDeliveredBinding> {

    private DownloadListener downloadListener;
    private final int DOWMLOADING = 1;


    public DeliveredAdapter(Context context) {
        super(context);
    }

    public void setDownloadListener(DownloadListener downloadListener) {
        this.downloadListener = downloadListener;
    }

    @Override
    protected int getContentID() {
        return R.layout.item_delivered;
    }

    @Override
    protected void onBindItem(ItemDeliveredBinding binding, DeliveredFile deliveredFile, int position) {
        binding.setFile(deliveredFile);
        String fileName = deliveredFile.getFileName();
        int progress = deliveredFile.getProgress();
        int downloadState = deliveredFile.getDownloadState();
        binding.tvDownload.setButtonMode(MODE_PROGRESS);
        switch (downloadState) {
            case 0:
                binding.tvDownload.setProgress(progress);
                binding.tvDownload.setText("下载");
                binding.tvDownload.setTextColor(mContext.getResources().getColor(R.color.font_blue));
                break;
            case DOWMLOADING:
                binding.tvDownload.setText("下载中");
                binding.tvDownload.setProgress(progress);
                binding.tvDownload.setTextColor(mContext.getResources().getColor(R.color.font_blue));
                break;
            case 2:
                binding.tvDownload.setText("查看");
                binding.tvDownload.setProgress(100);
                binding.tvDownload.setTextColor(mContext.getResources().getColor(R.color.white));
                break;
        }
        binding.tvDownload.setOnClickListener(v -> {
            if (null != downloadListener) {
                downloadListener.onDownload(position, deliveredFile.getUrl(), fileName, downloadState);
            }
        });
    }

    public interface DownloadListener {
        void onDownload(int position, String url, String fileName, int downloadState);
    }

    public void notifyDownloadState(int position, int state, int progress) {
        DeliveredFile deliveredFile = mDataSource.get(position);
        deliveredFile.setDownloadState(state);
        if (state == DOWMLOADING) {
            deliveredFile.setProgress(progress);
        }
        notifyItemChanged(position);
    }


}
