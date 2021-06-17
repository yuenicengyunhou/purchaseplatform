package com.rails.purchaseplatform.order.activity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloadQueueSet;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.util.FileDownloadUtils;
import com.rails.lib_data.bean.BuyerBean;
import com.rails.lib_data.bean.OrderInfoBean;
import com.rails.lib_data.bean.orderdetails.DeliveredFile;
import com.rails.lib_data.contract.OrderContract;
import com.rails.lib_data.contract.OrderPresenterImpl;
import com.rails.purchaseplatform.common.ConRoute;
import com.rails.purchaseplatform.common.base.ToolbarActivity;
import com.rails.purchaseplatform.common.widget.SpaceDecoration;
import com.rails.purchaseplatform.framwork.utils.ToastUtil;
import com.rails.purchaseplatform.order.R;
import com.rails.purchaseplatform.order.adapter.order_details.DeliveredAdapter;
import com.rails.purchaseplatform.order.databinding.ActivityDeliveredBinding;
import com.tencent.smtt.sdk.QbSdk;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Route(path = ConRoute.ORDER.ORDER_DELIVER)
public class DeliveredActivity extends ToolbarActivity<ActivityDeliveredBinding> implements OrderContract.OrderView {


    private String orderNo;
    private DeliveredAdapter adapter;
    private OrderPresenterImpl presenter;

    //下载
    private int currentIndex = -1;//默认为-1,为-1时没有下载任务，不为-1时等待下载完成
    private final List<BaseDownloadTask> tasks = new ArrayList<>();
    private HashMap<Integer, String> urlMap = new HashMap<>();
    private FileDownloadQueueSet queueSet;

    //刷新下载状态
    private final UIHandler uiHandler = new UIHandler(this);
    private final int COMPLETE = 2;
    private final int DOWNLOADING = 1;

    @SuppressLint("HandlerLeak")
    private class UIHandler extends Handler {
        private final WeakReference<DeliveredActivity> mActivity;

        @SuppressWarnings("deprecation")
        public UIHandler(DeliveredActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            DeliveredActivity activity = mActivity.get();
            if (activity == null || currentIndex == -1) return;
            if (msg.what == COMPLETE) {
                adapter.notifyDownloadState(currentIndex, COMPLETE, 0);
                currentIndex = -1;
            } else if (msg.what == DOWNLOADING) {
                adapter.notifyDownloadState(currentIndex, DOWNLOADING, msg.arg1);
            }
        }
    }

    @Override
    protected void getExtraEvent(Bundle extras) {
        super.getExtraEvent(extras);
        orderNo = extras.getString("orderNo", "");
    }

    @Override
    protected void initialize(Bundle bundle) {
        binding.titleBar
                .setTitleBar(R.string.deliveredFiles)
                .setShowLine(true)
                .setImgLeftRes(R.drawable.svg_back_black);


        adapter = new DeliveredAdapter(this);
        barBinding.recycler.addItemDecoration(new SpaceDecoration(this, 1, R.color.line_gray));
        barBinding.recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        barBinding.empty.setDescEmpty(R.string.order_empty).setImgEmpty(R.drawable.ic_cart_null).setMarginTop(80);
        barBinding.recycler.setAdapter(adapter);
        barBinding.recycler.setEmptyView(barBinding.empty);
        adapter.setDownloadListener((position, url, fileName, downloadState) -> {

            start_single(url, position + 1, position, downloadState);
//            addTasks(position, url,position+1,downloadState);
        });


        barBinding.refresh.setOnRefreshListener(refreshLayout -> presenter.getDelivered(orderNo));

        presenter = new OrderPresenterImpl(this, this);
        presenter.getDelivered(orderNo);
    }

    @Override
    protected int getColor() {
        return android.R.color.white;
    }

    @Override
    protected boolean isSetSystemBar() {
        return true;
    }

    @Override
    protected boolean isBindEventBus() {
        return false;
    }

    @Override
    public void loadDeliveredFileList(ArrayList<DeliveredFile> list) {
        barBinding.refresh.finishRefresh();
        if (null != adapter) {
            adapter.update(list, true);
        }
    }

    @Override
    public void getOrder(ArrayList<OrderInfoBean> orderBeans, boolean firstPage, int totalCount) {

    }

    @Override
    public void loadConditionNameList(ArrayList<BuyerBean> list) {
    }

    BaseDownloadTask singleTask;
    public int singleTaskId = 0;
    public String mSinglePath = FileDownloadUtils.getDefaultSaveRootPath() + File.separator + "GTSC"
            + File.separator;
    public String mSaveFolder = FileDownloadUtils.getDefaultSaveRootPath() + File.separator + "GTSC";

    /**
     * positionPlus=index+1
     */
    public void start_single(String url, int positionPlus, int position, int state) {
        String extension = url.substring(url.lastIndexOf("."));
        String mFilePath = mSinglePath + orderNo + "_" + positionPlus + extension;
        if (state == 2) {
//            ToastUtil.showCenter(this,"查看");
            readFile(mFilePath);
            return;
        }
        if (state == 1) {
            ToastUtil.showCenter(this, "正在下载");
            return;
        }
        if (currentIndex != -1) {
            ToastUtil.showCenter(this, "已有下载任务");
            return;
        }
        adapter.notifyDownloadState(position, 1, 0);
        currentIndex = position;
        singleTask = FileDownloader.getImpl().create(url)
//                .setPath(mSinglePath,false)
                .setPath(mFilePath, false)
                .setCallbackProgressTimes(300)
                .setMinIntervalUpdateSpeed(400)
                .setCallbackProgressMinInterval(10)
                .setCallbackProgressTimes(10)
                //.setTag()
                .setListener(new FileDownloadSampleListener() {
                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        Log.d("feifei", "error taskId:" + task.getId() + ",progress:" + (float)soFarBytes/totalBytes);
                        Message message = new Message();
                        message.arg1 = soFarBytes * 100 / totalBytes;
                        message.what = DOWNLOADING;
                        uiHandler.sendMessage(message);

                    }

                    @Override
                    protected void blockComplete(BaseDownloadTask task) {
                        Message message = new Message();
                        message.what = COMPLETE;
                        uiHandler.sendMessage(message);
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        Log.d("feifei", "error taskId:" + task.getId() + ",e:" + e.getLocalizedMessage());
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                        Log.d("feifei", "warn taskId:" + task.getId());
                    }
                });

        singleTaskId = singleTask.start();

    }

    //    private void OpenFile() {
//
//    }
    private void readFile(String mFilePath) {
//        OpenFiles.getImageFileIntent(mFilePath)
        HashMap<String, String> params = new HashMap<>();
        params.put("style", "0");
        params.put("local", "true");
        QbSdk.openFileReader(this, mFilePath, params, s -> {
        });
    }


//    public void pause_single() {
//        Log.d("feifei", "pause_single task:" + singleTaskId);
//        FileDownloader.getImpl().pause(singleTaskId);
//    }
//
//    public void delete_single() {
//
//        //删除单个任务的database记录
//        boolean deleteData = FileDownloader.getImpl().clear(singleTaskId, mSaveFolder);
//        File targetFile = new File(mSinglePath);
//        boolean delate = false;
//        if (targetFile.exists()) {
//            delate = targetFile.delete();
//        }
//
//        Log.d("feifei", "delete_single file,deleteDataBase:" + deleteData + ",mSinglePath:" + mSinglePath + ",delate:" + delate);
//
//        new File(FileDownloadUtils.getTempPath(mSinglePath)).delete();
//    }

    // 多任务下载
    private FileDownloadListener downloadListener;

    public FileDownloadListener createLis() {
        return new FileDownloadSampleListener() {
            @Override
            protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                if (task.getListener() != downloadListener) {
                    return;
                }
                Log.d("feifei", "pending taskId:" + task.getId() + ",fileName:" + task.getFilename() + ",soFarBytes:" + soFarBytes + ",totalBytes:" + totalBytes + ",percent:" + soFarBytes * 1.0 / totalBytes);

            }

            @Override
            protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                if (task.getListener() != downloadListener) {
                    return;
                }
                Log.d("feifei", "progress taskId:" + task.getId() + ",fileName:" + task.getFilename() + ",soFarBytes:" + soFarBytes + ",totalBytes:" + totalBytes + ",percent:" + soFarBytes * 1.0 / totalBytes + ",speed:" + task.getSpeed());
            }

            @Override
            protected void blockComplete(BaseDownloadTask task) {
                if (task.getListener() != downloadListener) {
                    return;
                }
                Log.d("feifei", "blockComplete taskId:" + task.getId() + ",filePath:" + task.getPath() + ",fileName:" + task.getFilename() + ",speed:" + task.getSpeed() + ",isReuse:" + task.reuse());
            }

            @Override
            protected void completed(BaseDownloadTask task) {
                if (task.getListener() != downloadListener) {
                    return;
                }
                Log.d("feifei", "completed taskId:" + task.getId() + ",isReuse:" + task.reuse());
            }

            @Override
            protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                if (task.getListener() != downloadListener) {
                    return;
                }
                Log.d("feifei", "paused taskId:" + task.getId() + ",soFarBytes:" + soFarBytes + ",totalBytes:" + totalBytes + ",percent:" + soFarBytes * 1.0 / totalBytes);
            }

            @Override
            protected void error(BaseDownloadTask task, Throwable e) {
                if (task.getListener() != downloadListener) {
                    return;
                }
                Log.d("feifei", "error taskId:" + task.getId() + ",e:" + e.getLocalizedMessage());
            }

            @Override
            protected void warn(BaseDownloadTask task) {
                if (task.getListener() != downloadListener) {
                    return;
                }
                Log.d("feifei", "warn taskId:" + task.getId());
            }
        };
    }

    private void addTasks(int position, String url, int positionPlus, int downloadState) {
        adapter.notifyDownloadState(position, 1, 0);
        String extension = url.substring(url.lastIndexOf("."));
        String mFilePath = mSinglePath + orderNo + "_" + positionPlus + extension;
        urlMap.put(position, url);
        BaseDownloadTask task = FileDownloader.getImpl().create(url).setPath(mFilePath, false);
        tasks.add(task);
        start_multi();
    }

    public void start_multi() {

        downloadListener = createLis();
        //(1) 创建 FileDownloadQueueSet
        if (null == queueSet) {
            queueSet = new FileDownloadQueueSet(downloadListener);
            //(2) 创建Task 队列


//        tasks.add(task1);
//        BaseDownloadTask task2 = FileDownloader.getImpl().create(BIG_FILE_URLS[4]).setPath(mSaveFolder,true);
//        tasks.add(task2);

            //(3) 设置参数

            // 每个任务的进度 无回调
            //queueSet.disableCallbackProgressTimes();
            // do not want each task's download progress's callback,we just consider which task will completed.

            queueSet.setCallbackProgressTimes(100);
            queueSet.setCallbackProgressMinInterval(100);
            //失败 重试次数
            queueSet.setAutoRetryTimes(3);

            //避免掉帧
            FileDownloader.enableAvoidDropFrame();

        }
        //(4)串行下载
        queueSet.downloadSequentially(tasks);

        //(5)任务启动
        queueSet.start();
    }

    public void stop_multi() {
        FileDownloader.getImpl().pause(downloadListener);
    }

    public void deleteAllFile() {

        //清除所有的下载任务
        FileDownloader.getImpl().clearAllTaskData();

        //清除所有下载的文件
        int count = 0;
        File file = new File(FileDownloadUtils.getDefaultSaveRootPath());
        do {
            if (!file.exists()) {
                break;
            }

            if (!file.isDirectory()) {
                break;
            }

            File[] files = file.listFiles();

            if (files == null) {
                break;
            }

            for (File file1 : files) {
                count++;
                file1.delete();
            }

        } while (false);

        Toast.makeText(this,
                String.format("Complete delete %d files", count), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBack() {
        finishWithResult();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishWithResult();
    }

    private void finishWithResult() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }
}